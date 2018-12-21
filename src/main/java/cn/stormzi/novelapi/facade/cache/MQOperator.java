package cn.stormzi.novelapi.facade.cache;

import cn.stormzi.novelapi.facade.bean.cache.disruptor.ValueElement;

public interface MQOperator<T> {
    void send(T data,int type);
    void receiver(ValueElement<T> valueElement);

}
