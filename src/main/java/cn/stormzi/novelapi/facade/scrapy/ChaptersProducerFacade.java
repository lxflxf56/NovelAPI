package cn.stormzi.novelapi.facade.scrapy;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;

import java.util.List;

public interface ChaptersProducerFacade {

    void addQueue(UrlBean urlBean);

    void addQueue(ChaptersBean chaptersBean);

    void addQueue(String json);

    void addQueue(List list);

}
