package cn.stormzi.novelapi.web.controller;

import cn.stormzi.novelapi.facade.analysis.SearchAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.cache.SearchBean;
import cn.stormzi.novelapi.facade.bean.web.ErrorCode;
import cn.stormzi.novelapi.facade.scrapy.ChaptersProducerFacade;
import cn.stormzi.novelapi.web.ControllerBase;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-18
 **/

@RestController
@RequestMapping("/search")
public class SearchController extends ControllerBase {

    @Autowired
    SearchAnalysisFacade searchService;

    @Autowired
    ChaptersProducerFacade chaptersProducerService;

    @RequestMapping(value = "index")
    @ResponseBody
    @Override
    public String index(@RequestBody Map<String, String> map) {
        String searchname = map.get("searchname");
        if (null == searchname) {
            return JSON.toJSONString(new ErrorCode("404", "请求为空"));
        }
        String websitename = map.get("websitename");
        String author = map.get("author");

        if (null == websitename) {
            return searchService.searchFromCacheByBookname(searchname);
        } else if (null != author) {
            return searchService.searchFromCacheByAuthor(author);
        } else {
            SearchBean searchBean = searchService.searchFromWebsiteByBookname(searchname, websitename);
            if (searchBean!=null){
                chaptersProducerService.addQueue(searchBean.getNovelItems());
                return JSON.toJSONString(searchBean);
            }
        }
        return JSON.toJSONString(new ErrorCode("404", "请求错误"));
    }

    @RequestMapping(value = "/website.json")
    @ResponseBody
    public String listWebsite() {
        Set allWebsite = searchService.getAllWebsite();
        String json = JSON.toJSONString(allWebsite);
        return json;
    }

}
