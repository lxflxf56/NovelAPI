package cn.stormzi.novelapi.facade.analysis;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-07
 **/


public interface UrlAnalysisFacade {
    UrlBean extractLink(String link);
    UrlBean exchangeLink(UrlBean link);
    boolean checkUrl(String link);
}
