package cn.stormzi.novelapi.facade.analysis;

import cn.stormzi.novelapi.facade.bean.cache.PageBean;

public interface PageAnalysisFacade {
    PageBean analysisPage(String link);
    boolean savePageIntoCache(PageBean pageBean);
    PageBean getPageFromCache(String link);

}
