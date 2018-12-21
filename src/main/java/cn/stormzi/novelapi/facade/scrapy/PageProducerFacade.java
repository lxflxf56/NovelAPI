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


    //void addQueue(String link);

    void addQueue(UrlBean urlBean);

    void addQueue(PageBean pageBean);

    void addQueue(String json);

}
