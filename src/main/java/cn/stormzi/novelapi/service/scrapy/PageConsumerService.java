package cn.stormzi.novelapi.service.scrapy;

import cn.stormzi.novelapi.NovelapiApplication;
import cn.stormzi.novelapi.facade.analysis.PageAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.cache.ESFacade;
import cn.stormzi.novelapi.facade.cache.KeyFacade;
import cn.stormzi.novelapi.facade.scrapy.PageConsumerFacade;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: novelapi
 * @description: 异步请求消费者service
 * @author: Xiaofeng
 * @create: 2018-12-18
 **/

@Service("pageConsumerService")
public class PageConsumerService implements PageConsumerFacade {

    protected static final Logger logger = LoggerFactory.getLogger(PageConsumerService.class);

    @Autowired
    private PageAnalysisFacade pageService;

    @Autowired
    private KeyFacade keyService;

    @Autowired
    private ESFacade esService;

    @Override
    public boolean filter(UrlBean urlBean) {
        if (Math.random()*10>7){
            return false;
        }
        long pageKey = keyService.createPageKey(urlBean);
        try {
            String select = esService.select(NovelapiApplication.index, NovelapiApplication.pageType, pageKey);
            PageBean pageBean = JSON.parseObject(select, PageBean.class);
            Date createtime = pageBean.getCreatetime();
            int day = compareDay(createtime, new Date());
            if (day>60){
                return true;
            }
        } catch (IOException e) {
            logger.error("e",e);
        }
        return false;
    }

    public int compareDay(Date date1,Date date2){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int day1=calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date2);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        return day2-day1;
    }

    @Override
    public void comsumer(UrlBean urlBean) {
        if (filter(urlBean)){
            pageService.analysisPage(urlBean);
        }
    }


}
