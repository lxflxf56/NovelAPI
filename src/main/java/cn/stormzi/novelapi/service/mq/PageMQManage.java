package cn.stormzi.novelapi.service.mq;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.ValueElement;
import cn.stormzi.novelapi.facade.cache.DisruptorFacade;
import cn.stormzi.novelapi.facade.scrapy.PageConsumerFacade;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: novelapi
 * @description: 异步请求Page MQ队列
 * @author: Xiaofeng
 * @create: 2018-12-11
 **/




public class PageMQManage extends DisruptorFacade<UrlBean> {

    @Autowired
    private PageConsumerFacade pageConsumerService;

    public PageMQManage() {}

    @Override
    protected Disruptor<ValueElement> generateDisruptor() {
        Disruptor<ValueElement> valueElementDisruptor = DisruptorFacade.multiValueElement();
        ringBuffer= this.disruptor.getRingBuffer();
        valueElementDisruptor.handleEventsWith(generateHandler());
        valueElementDisruptor.start();
        return valueElementDisruptor;
    }

    @Override
    public void receiver(ValueElement<UrlBean> valueElement) {
        UrlBean value = valueElement.getValue();
        pageConsumerService.comsumer(value);
    }

}


