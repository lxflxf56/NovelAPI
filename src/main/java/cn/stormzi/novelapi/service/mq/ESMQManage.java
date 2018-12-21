package cn.stormzi.novelapi.service.mq;

import cn.stormzi.novelapi.NovelapiApplication;
import cn.stormzi.novelapi.facade.bean.enums.ESTypeEnum;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.ValueElement;
import cn.stormzi.novelapi.facade.cache.DisruptorFacade;
import cn.stormzi.novelapi.facade.cache.ESFacade;
import cn.stormzi.novelapi.facade.cache.KeyFacade;
import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/

@Service("esMQManage")
public class ESMQManage extends DisruptorFacade {

    @Autowired
    ESFacade esService;
    @Autowired
    KeyFacade keyService;

    @Override
    public void receiver(ValueElement valueElement) {
        int type = valueElement.getType();
        ESTypeEnum anEnum = ESTypeEnum.getEnum(type);
        long id;
        String json;
        switch (anEnum) {
            case Insert_Page:
                PageBean pageBean = (PageBean) valueElement.getValue();
                id = keyService.createPageKey(pageBean);
                //id = pageBean.getWebsite().hashCode() ^ 16 + pageBean.getUrl().hashCode();
                json = JSON.toJSONString(pageBean);
                esService.insert(NovelapiApplication.index, NovelapiApplication.pageType, id, json);
                break;
            case Insert_Chapters:
                ChaptersBean contentBean = (ChaptersBean) valueElement.getValue();
                //id = contentBean.getWebsite().hashCode() ^ 16 + contentBean.getUrl().hashCode();
                id = keyService.createContentKey(contentBean);
                json = JSON.toJSONString(contentBean);
                esService.insert(NovelapiApplication.index, NovelapiApplication.contentType, id, json);
                break;
        }
    }

    @Override
    protected Disruptor<ValueElement> generateDisruptor() {
        Disruptor<ValueElement> valueElementDisruptor = DisruptorFacade.multiValueElement();
        valueElementDisruptor.handleEventsWith(generateHandler());
        ringBuffer = valueElementDisruptor.getRingBuffer();
        return valueElementDisruptor;
    }

}
