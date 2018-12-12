package cn.stormzi.novelapi.facade.analysis;

import cn.stormzi.novelapi.facade.bean.cache.ContentBean;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public interface ContentAnalysisFacade {
    ContentBean analysisContent(String link);
    boolean saveContentIntoCache(ContentBean contentBean);
    ContentBean getContentBeanFromCache(String link);
}
