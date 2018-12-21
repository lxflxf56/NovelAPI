package cn.stormzi.novelapi.facade.analysis;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public interface ChaptersAnalysisFacade {
    ChaptersBean analysisContent(UrlBean bean);
    boolean saveContentIntoCache(ChaptersBean chaptersBean);
    ChaptersBean getContentBeanFromCache(UrlBean urlBean);
}
