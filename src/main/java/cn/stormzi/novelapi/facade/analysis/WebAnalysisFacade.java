package cn.stormzi.novelapi.facade.analysis;

import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-06
 **/


public interface WebAnalysisFacade {
    Map getPagePatternMap(String website);
    Map getContentPatternMap(String website);


}
