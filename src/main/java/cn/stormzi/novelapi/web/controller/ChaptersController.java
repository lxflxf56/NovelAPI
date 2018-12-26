package cn.stormzi.novelapi.web.controller;

import cn.stormzi.novelapi.NovelapiApplication;
import cn.stormzi.novelapi.facade.analysis.ChaptersAnalysisFacade;
import cn.stormzi.novelapi.facade.analysis.UrlAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.web.ErrorCode;
import cn.stormzi.novelapi.web.ControllerBase;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/

@RestController
@RequestMapping("/Chapters")
public class ChaptersController extends ControllerBase {
    protected static final Logger logger = LoggerFactory.getLogger(ChaptersController.class);

    @Autowired
    UrlAnalysisFacade urlService;

    @Autowired
    ChaptersAnalysisFacade chaptersService;

    @RequestMapping(value="/index")
    @ResponseBody
    public String index(@RequestBody Map<String,String> jsonMap){
        //logger.debug("/page/index json:{}",jsonMap);
        String link = jsonMap.get("link");
        UrlBean urlBean = urlService.extractLink(link);
        if (null == urlBean) {
            return JSON.toJSONString(new ErrorCode("404", "请求链接不合法"));
        }
        if (NovelapiApplication.useCache){
            return chaptersService.getContentBeanFromCache(urlBean);
        }
        return JSON.toJSONString(new ErrorCode("404","No support Now"));
    }



}
