package cn.stormzi.novelapi.facade.bean.cache;

import java.util.List;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public class SearchBean {
    protected List<NovelItem> novelItems;

    public static class NovelItem{
        private String contentUrl,imgUrl,author,title,description,lastestSection,website;
    }

}
