package cn.stormzi.novelapi.facade.bean.cache.disruptor;

import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.analysis.UserBean;

/**
 * @program: novelapi
 * @description: 队列中的元素
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/


public class UrlBeanElement {
    private UrlBean value;

    public UrlBean getValue() {
        return value;
    }

    public void setValue(UrlBean value) {
        this.value = value;
    }
}
