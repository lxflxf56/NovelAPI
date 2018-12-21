package cn.stormzi.novelapi.facade.cache;

import cn.stormzi.novelapi.facade.bean.cache.disruptor.IntElement;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.ValueElement;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/


public abstract class DisruptorFacade<T> implements MQOperator<T>{

    protected EventHandler<ValueElement<T>> handler;

    protected Disruptor<ValueElement> disruptor = generateDisruptor();
    //buffer
    protected RingBuffer<ValueElement> ringBuffer;

    @Override
    public void send(T data,int type){
        long next = ringBuffer.next();
        try {
            ValueElement valueElement = ringBuffer.get(next);
            valueElement.setValue(data);
            valueElement.setType(type);
        }finally {
            ringBuffer.publish(next);
        }
    }

    public void start() {
        if (disruptor!=null)
            disruptor.start();
    }

    public void close() {
        if (disruptor!=null){
            disruptor.shutdown();
        }
    }

    private final static int buffersize = 128 * 128;

    protected abstract Disruptor<ValueElement> generateDisruptor();


    protected EventHandler<ValueElement> generateHandler() {
        return new EventHandler<ValueElement>() {
            @Override
            public void onEvent(ValueElement urlBeanValueElement, long l, boolean b) throws Exception {
                receiver(urlBeanValueElement);
            }
        };
    }

    public static Disruptor<ValueElement> multiValueElement() {
        EventFactory<ValueElement> eventFactory =new EventFactory<ValueElement>() {
            @Override
            public ValueElement newInstance() {
                return new ValueElement();
            }
        };
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread("disruptor-ValueElement:" + r.toString());
            }
        };
        WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();

        return new Disruptor(eventFactory, buffersize, threadFactory, ProducerType.MULTI, YIELDING_WAIT);
    }


    @Deprecated
    public static Disruptor<ValueElement> singleCachedThreadPool(){
        EventFactory<ValueElement> eventFactory =new EventFactory<ValueElement>() {
            @Override
            public ValueElement newInstance() {
                return new ValueElement();
            }
        };
        WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
        return new Disruptor(eventFactory, buffersize, Executors.newCachedThreadPool(), ProducerType.MULTI, YIELDING_WAIT);
    }

}
