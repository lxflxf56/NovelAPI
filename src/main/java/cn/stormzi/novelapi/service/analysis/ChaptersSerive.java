package cn.stormzi.novelapi.service.analysis;

import cn.stormzi.novelapi.facade.analysis.ChaptersAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import org.springframework.stereotype.Service;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-21
 **/

@Service("chaptersSerive")
public class ChaptersSerive implements ChaptersAnalysisFacade {
    @Override
    public ChaptersBean analysisContent(UrlBean bean) {
        return null;
    }

    @Override
    public boolean saveContentIntoCache(ChaptersBean chaptersBean) {
        return false;
    }

    @Override
    public ChaptersBean getContentBeanFromCache(UrlBean urlBean) {
        return null;
    }
}
