package cn.stormzi.novelapi.service.cache;

import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.ValueElement;
import cn.stormzi.novelapi.facade.cache.DisruptorFacade;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/


public class ESMQManage extends DisruptorFacade {

    public ESMQManage(){
        ringBuffer=disruptor.getRingBuffer();
        this.start();
    }


    @Override
    public void send(Object data) {

    }

    @Override
    protected EventHandler<ValueElement> generateHandler() {
        return null;
    }

    @Override
    protected Disruptor<ValueElement> generateDisruptor() {
        return DisruptorFacade.multiValueElement();
    }

}
