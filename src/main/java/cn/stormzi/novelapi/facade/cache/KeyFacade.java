package cn.stormzi.novelapi.facade.cache;

import cn.stormzi.novelapi.facade.bean.cache.ContentBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.cache.SearchBean;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public interface KeyFacade {
    String createPageKey(PageBean pageBean);
    String createContentKey(ContentBean contentBean);
}
