package cn.stormzi.novelapi.facade.scrapy;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;

public interface PageConsumerFacade {
    boolean filter(UrlBean urlBean);
    void comsumer(UrlBean urlBean);
}
