package cn.stormzi.novelapi.facade.cache;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.IntElement;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.UrlBeanElement;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.ValueElement;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/


public abstract class DisruptorFacade<T> {

    protected EventHandler<ValueElement<T>> handler = generateHandler();

    protected Disruptor<ValueElement> disruptor = generateDisruptor();
    //buffer
    protected RingBuffer<ValueElement> ringBuffer;

    public abstract void send(T data);

    public void start() {
        disruptor.start();
    }

    protected abstract EventHandler<ValueElement<T>> generateHandler();

    protected abstract Disruptor<ValueElement> generateDisruptor();

    private static int buffersize = 1024 * 1024;

    public static Disruptor<IntElement> singleIntElement() {
        EventFactory<IntElement> eventFactory = new EventFactory<IntElement>() {
            @Override
            public IntElement newInstance() {
                return new IntElement();
            }
        };
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread("disruptor IntElement" + r.toString());
            }
        };
        WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();

        return new Disruptor(eventFactory, buffersize, threadFactory, ProducerType.SINGLE, YIELDING_WAIT);
    }

    public static Disruptor<ValueElement> multiValueElement() {
        EventFactory<UrlBeanElement> eventFactory = new EventFactory() {
            @Override
            public UrlBeanElement newInstance() {
                return new UrlBeanElement();
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

}
