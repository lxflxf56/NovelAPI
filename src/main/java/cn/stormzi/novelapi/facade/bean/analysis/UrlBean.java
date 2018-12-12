package cn.stormzi.novelapi.facade.bean.analysis;

import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-07
 **/


public class UrlBean {
    private String hostname = null;
    private String realurl = null;
    private String link =null;
    private Map<String,String> para = null;

    public UrlBean() {
    }

    public UrlBean(String link) {
        this.link = link;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getRealurl() {
        return realurl;
    }

    public void setRealurl(String realurl) {
        this.realurl = realurl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map<String, String> getPara() {
        return para;
    }

    public void setPara(Map<String, String> para) {
        this.para = para;
    }
}
