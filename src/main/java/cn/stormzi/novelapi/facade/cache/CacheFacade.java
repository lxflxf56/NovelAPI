package cn.stormzi.novelapi.facade.cache;

import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.cache.SearchBean;

public interface CacheFacade {
    boolean set(String key,String value);
    String get(String key);
    boolean setPage(PageBean pageBean);
    PageBean getPage(String key);
    boolean setContent(ChaptersBean chaptersBean);
    ChaptersBean getContent(String key);
    boolean setSearch(SearchBean searchBean);
    SearchBean getSearchBean(String key);

}
