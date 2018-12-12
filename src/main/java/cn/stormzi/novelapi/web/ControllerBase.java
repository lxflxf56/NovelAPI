package cn.stormzi.novelapi.web;

import cn.stormzi.novelapi.facade.analysis.WebAnalysisFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public abstract class ControllerBase  {
    protected static final Logger logger = getLogger();
    protected static Logger getLogger(){
        return LoggerFactory.getLogger(ControllerBase.class);
    }
    public abstract String index(Map<String,String> map);
    public abstract Map getPatternMap(String website);
}
