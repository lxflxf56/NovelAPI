package cn.stormzi.novelapi.service.scrapy;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.ValueElement;
import cn.stormzi.novelapi.facade.cache.DisruptorFacade;
import cn.stormzi.novelapi.facade.scrapy.PageConsumerFacade;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-11
 **/




public class PageMQManage extends DisruptorFacade<UrlBean> {

    private PageConsumerFacade pageConsumerFacade;

    public PageMQManage() {
        ringBuffer=disruptor.getRingBuffer();
        this.start();
    }


    @Override
    public void send(UrlBean data) {
        long next = ringBuffer.next();
        try {
            ValueElement valueElement = ringBuffer.get(next);
            valueElement.setValue(data);
        }finally {
            ringBuffer.publish(next);
        }
    }

    @Override
    protected EventHandler<ValueElement<UrlBean>> generateHandler() {
        return new EventHandler<ValueElement<UrlBean>>() {
            @Override
            public void onEvent(ValueElement<UrlBean> urlBeanValueElement, long l, boolean b) throws Exception {
                UrlBean value = urlBeanValueElement.getValue();
                pageConsumerFacade.comsumer(value);
            }
        };
    }

    @Override
    protected Disruptor<ValueElement> generateDisruptor() {
        return DisruptorFacade.multiValueElement();
    }


}


