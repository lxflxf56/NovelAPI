package cn.stormzi.novelapi.service.scrapy;

import cn.stormzi.novelapi.facade.analysis.UrlAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.cache.DisruptorFacade;
import cn.stormzi.novelapi.facade.scrapy.PageProducerFacade;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-07
 **/


public class PageProducerService implements PageProducerFacade {

    DisruptorFacade disruptorFacade;

    UrlAnalysisFacade urlAnalysisFacade;

    @Override
    public boolean filter(UrlBean urlBean) {
        return false;
    }

    @Override
    public void addQueue(UrlBean urlBean) {
        boolean filter = filter(urlBean);
        if (filter) {
            disruptorFacade.send(urlBean);
        }
    }

    @Override
    public boolean filter(PageBean pageBean) {
        return false;
    }

    @Override
    public void addQueue(PageBean pageBean) {
        if (filter(pageBean)) {
            UrlBean urlBean = urlAnalysisFacade.extractLink(pageBean.getUrl());
            disruptorFacade.send(urlBean);
        }
    }


}
