package cn.stormzi.novelapi.facade.bean.cache;

import java.util.List;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public class SearchBean {
    protected List<ChaptersBean> novelItems;


    public List<ChaptersBean> getNovelItems() {
        return novelItems;
    }

    public void setNovelItems(List<ChaptersBean> novelItems) {
        this.novelItems = novelItems;
    }
}
