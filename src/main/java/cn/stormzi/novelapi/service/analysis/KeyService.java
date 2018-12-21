package cn.stormzi.novelapi.service.analysis;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.cache.KeyFacade;
import org.springframework.stereotype.Service;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-21
 **/

@Service("keyService")
public class KeyService implements KeyFacade {
    @Override
    public long createPageKey(PageBean pageBean) {
        return 0;
    }

    @Override
    public long createContentKey(ChaptersBean chaptersBean) {
        return 0;
    }

    @Override
    public long createPageKey(UrlBean pageBean) {
        return 0;
    }

    @Override
    public long createContentKey(UrlBean contentBean) {
        return 0;
    }
}
