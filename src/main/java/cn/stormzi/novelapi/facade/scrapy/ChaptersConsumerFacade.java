package cn.stormzi.novelapi.facade.scrapy;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;

public interface ChaptersConsumerFacade {
    boolean filter(UrlBean urlBean);
}
