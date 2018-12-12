package cn.stormzi.novelapi.web.controller;

import cn.stormzi.novelapi.facade.bean.web.ErrorCode;
import cn.stormzi.novelapi.web.ControllerBase;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/

@Controller
@RequestMapping("/content")
public class ContentController extends ControllerBase {
    protected static final Logger logger = LoggerFactory.getLogger(ContentController.class);


    @RequestMapping(value="/index")
    @ResponseBody
    public String index(@RequestBody Map<String,String> jsonMap){
        logger.debug("/page/index json:{}",jsonMap);
        return JSON.toJSONString(new ErrorCode("404","No support Now"));
    }

    @Override
    public Map getPatternMap(String website) {
        return null;
    }


}
