package cn.stormzi.novelapi.facade.scrapy;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ContentBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.cache.SearchBean;

public interface RequestFacade {
    void asynReqPage(UrlBean urlBean);
    void asynReqContent(UrlBean urlBean);
    void asyReqSearch(UrlBean urlBean);
    PageBean requestPage(UrlBean urlBean);
    ContentBean requestContent(UrlBean urlBean);
    SearchBean requestSearch(UrlBean urlBean);
}
