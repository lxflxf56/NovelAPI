package cn.stormzi.novelapi.service.scrapy;

import cn.stormzi.novelapi.facade.analysis.UrlAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.enums.ESTypeEnum;
import cn.stormzi.novelapi.facade.scrapy.PageProducerFacade;
import cn.stormzi.novelapi.service.mq.PageMQManage;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

/**
 * @program: novelapi
 * @description: 异步请求Page生产者
 * @author: Xiaofeng
 * @create: 2018-12-07
 **/

@Service("pageProducerService")
public class PageProducerService implements PageProducerFacade {

    PageMQManage pageMQManage;

    UrlAnalysisFacade urlAnalysisFacade;

    /**
    * @Description: 已在缓存内的更新
    * @Author: Xiaofeng
    * @Date: 2018/12/18 
    */ 
    @Override
    public void addQueue(UrlBean urlBean) {
        pageMQManage.send(urlBean,ESTypeEnum.Update_Page.getIndex());
    }

    @Override
    public void addQueue(PageBean pageBean) {
        UrlBean urlBean = urlAnalysisFacade.extractLink(pageBean.getUrl());
        addQueue(urlBean);
    }

    @Override
    public void addQueue(String json) {
        PageBean pageBean = JSON.parseObject(json, PageBean.class);
        addQueue(pageBean);
    }


}
