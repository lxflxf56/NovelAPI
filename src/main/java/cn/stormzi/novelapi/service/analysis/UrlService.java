package cn.stormzi.novelapi.service.analysis;

import cn.stormzi.novelapi.facade.analysis.UrlAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-06
 **/


@Service("urlService")
public class UrlService implements UrlAnalysisFacade {
    private static Map reallywebsite;

    public UrlService() {
    }


    @Override
    public UrlBean extractLink(String link) {
        try {
            URL url = new URL(link);
            UrlBean urlBean = new UrlBean();
            urlBean.setHostname(url.getHost());
            HashMap map = new HashMap();
            String[] split = link.split("\\?");
            urlBean.setLink(split[0]);
            if (split.length > 1) {
                String[] paras = split[1].split("&");
                for (String para : paras) {
                    String[] strings = para.split("=");
                    map.put(strings[0], strings[1]);
                }
            }
            urlBean.setPara(map);
            urlBean = exchangeLink(urlBean);
            return urlBean;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description: 变换相同网站url
     * @Author: Xiaofeng
     * @Date: 2018/12/12
     */
    public UrlBean exchangeLink(UrlBean urlBean) {

        return urlBean;
    }

    @Override
    public boolean checkUrl(String link) {
        return false;
    }
}
