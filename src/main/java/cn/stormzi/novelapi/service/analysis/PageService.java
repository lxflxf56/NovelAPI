package cn.stormzi.novelapi.service.analysis;

import cn.stormzi.novelapi.NovelapiApplication;
import cn.stormzi.novelapi.facade.analysis.PageAnalysisFacade;
import cn.stormzi.novelapi.facade.analysis.PatternMap;
import cn.stormzi.novelapi.facade.bean.analysis.BookSelectInfo;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.enums.ESTypeEnum;
import cn.stormzi.novelapi.facade.cache.DisruptorFacade;
import cn.stormzi.novelapi.facade.cache.ESFacade;
import cn.stormzi.novelapi.facade.cache.KeyFacade;
import cn.stormzi.novelapi.facade.scrapy.PageProducerFacade;
import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-18
 **/

@Service("pageService")
public class PageService implements PageAnalysisFacade, PatternMap {


    @Autowired
    DisruptorFacade esMQManage;
    @Autowired
    ESFacade esService;
    @Autowired
    KeyFacade keyService;
    @Autowired
    PageProducerFacade pageProducerService;

    protected static final Logger logger = LoggerFactory.getLogger(PageService.class);

    protected Map patternMap;


    @Override
    public PageBean analysisPage(UrlBean urlBean) {
        Map<String, BookSelectInfo> patternMap = getPatternMap();
        BookSelectInfo bookSelectInfo = patternMap.get(urlBean.getHostname());
        PageBean pageBean = null;
        if (null == bookSelectInfo) {
            return pageBean;
        }
        try {
            //https://jsoup.org/cookbook/extracting-data/dom-navigation
            Document doc = Jsoup.connect(urlBean.getRealurl()).get();
            pageBean = new PageBean();


        } catch (IOException e) {
            logger.error("请求出错： e ", e);
        }
        return pageBean;
    }


    @Override
    public boolean savePageIntoCache(PageBean pageBean) {
        if (NovelapiApplication.useCache)
            esMQManage.send(pageBean, ESTypeEnum.Insert_Page.getIndex());
        return true;
    }

    @Override
    public String getPageFromCache(UrlBean urlBean) {
        long key = keyService.createPageKey(urlBean);
        String json = null;
        try {
            json = esService.select(NovelapiApplication.index, NovelapiApplication.pageType, key);
            if (json==null){
                PageBean pageBean = analysisPage(urlBean);
                json= JSON.toJSONString(pageBean);
                savePageIntoCache(pageBean);
            }else {
                pageProducerService.addQueue(json);
            }
        } catch (IOException e) {
            logger.error("请求出错： e ", e);
        }
        return json;
    }

    @Override
    public PageBean getPageFromWeb(UrlBean urlBean) {
        return analysisPage(urlBean);
    }

    @Override
    public synchronized void generateMap() {
        patternMap=NovelapiApplication.getPatternMap();
    }

    @Override
    public Map getPatternMap() {
        if (patternMap == null) {
            generateMap();
        }
        return patternMap;
    }
}
