package cn.stormzi.novelapi.facade.cache;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public interface KeyFacade {
    long createPageKey(PageBean pageBean);
    long createContentKey(ChaptersBean chaptersBean);
    long createPageKey(UrlBean pageBean);
    long createContentKey(UrlBean contentBean);
}
