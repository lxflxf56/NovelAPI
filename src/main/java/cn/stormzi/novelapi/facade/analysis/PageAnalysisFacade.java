package cn.stormzi.novelapi.facade.analysis;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;

public interface PageAnalysisFacade {
    PageBean analysisPage(UrlBean bean);
    boolean savePageIntoCache(PageBean pageBean);
    String getPageFromCache(UrlBean urlBean);
    PageBean getPageFromWeb(UrlBean urlBean);

}
