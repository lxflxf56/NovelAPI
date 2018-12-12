package cn.stormzi.novelapi.service.scrapy;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ContentBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.cache.SearchBean;
import cn.stormzi.novelapi.facade.scrapy.RequestFacade;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-11
 **/


public class RequestService implements RequestFacade {


    @Override
    public void asynReqPage(UrlBean urlBean) {

    }

    @Override
    public void asynReqContent(UrlBean urlBean) {

    }

    @Override
    public void asyReqSearch(UrlBean urlBean) {

    }

    @Override
    public PageBean requestPage(UrlBean urlBean) {
        return null;
    }

    @Override
    public ContentBean requestContent(UrlBean urlBean) {
        return null;
    }

    @Override
    public SearchBean requestSearch(UrlBean urlBean) {
        return null;
    }
}
