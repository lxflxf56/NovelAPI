package cn.stormzi.novelapi.util.http;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.service.analysis.UrlService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public class HttpUtilTest {
    @Test
    public void connectTest(){
        String url="http://123.207.121.111:8080";
        String json="{\"name\":\"你好\"}";
        String s;
        try {
//            s = HttpUtil.get(url);
//            System.out.println(s);
              s=HttpUtil.post(url,json);
              System.out.println(s);
//            s=HttpUtil.put(url,json);
//            System.out.println(s);
//            s=HttpUtil.delete(url,json);
//            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void paraTest(){
        UrlService urlService = new UrlService();
        UrlBean urlBean = urlService.extractLink("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F%20%3F&oq=%25E4%25BD%25A0%25E5%25A5%25BD&rsv_pq=8d74278b00005959&rsv_t=7e2di%2Bq4WZj%2BIGd9nSGmTZcTuswXASqU4tH78qGx5vbK2y1uyalM%2FRpX1D0&rqlang=cn&rsv_enter=1&rsv_sug3=19&rsv_sug1=17&rsv_sug7=100&bs=%E4%BD%A0%E5%A5%BD");
        String hostname = urlBean.getHostname();
        String realurl = urlBean.getRealurl();
        Map<String, String> para = urlBean.getPara();
        System.out.println(JSON.toJSONString(urlBean));
    }



}
