package cn.stormzi.novelapi.service.scrapy;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import cn.stormzi.novelapi.facade.scrapy.ChaptersProducerFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-21
 **/

@Service("chaptersProducerService")
public class ChaptersProducerService implements ChaptersProducerFacade {
    @Override
    public void addQueue(UrlBean urlBean) {

    }

    @Override
    public void addQueue(ChaptersBean chaptersBean) {

    }

    @Override
    public void addQueue(String json) {

    }

    @Override
    public void addQueue(List list) {

    }
}
