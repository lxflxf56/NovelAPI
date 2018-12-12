package cn.stormzi.novelapi.facade.scrapy;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-07
 **/


public interface PageProducerFacade {

    boolean filter(UrlBean urlBean);

    void addQueue(UrlBean urlBean);

    boolean filter(PageBean pageBean);

    void addQueue(PageBean pageBean);

}
