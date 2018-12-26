package cn.stormzi.novelapi.web.controller;

import cn.stormzi.novelapi.NovelapiApplication;
import cn.stormzi.novelapi.facade.analysis.PageAnalysisFacade;
import cn.stormzi.novelapi.facade.analysis.UrlAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.web.ErrorCode;
import cn.stormzi.novelapi.web.ControllerBase;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/page")
public class PageController extends ControllerBase {


    @Autowired
    UrlAnalysisFacade urlService;


    PageAnalysisFacade pageService;

    @RequestMapping(value = "/index")
    @ResponseBody
    public String index(@RequestBody Map jsonMap) {
        //logger.debug("/page/index json:{}", jsonMap);
        String link = (String) jsonMap.get("link");
        UrlBean urlBean = urlService.extractLink(link);
        if (null == urlBean) {
            return JSON.toJSONString(new ErrorCode("404", "请求链接不合法"));
        }
        PageBean pageBean = null;
        if (NovelapiApplication.useCache) {
            return pageService.getPageFromCache(urlBean);
        } else {
            pageBean = pageService.getPageFromWeb(urlBean);
            pageService.savePageIntoCache(pageBean);
        }
        if (pageBean == null) {
            return JSON.toJSONString(new ErrorCode("404", "No support Now"));
        }
        return JSON.toJSONString(pageBean);
    }

}
