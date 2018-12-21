package cn.stormzi.novelapi.service.mq;

import cn.stormzi.novelapi.facade.analysis.ChaptersAnalysisFacade;
import cn.stormzi.novelapi.facade.analysis.UrlAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import cn.stormzi.novelapi.facade.bean.cache.disruptor.ValueElement;
import cn.stormzi.novelapi.facade.bean.enums.ESTypeEnum;
import cn.stormzi.novelapi.facade.cache.DisruptorFacade;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-19
 **/


public class ChaptersMQManage extends DisruptorFacade {

    @Autowired
    UrlAnalysisFacade urlService;

    @Autowired
    ChaptersAnalysisFacade chaptersSerive;


    @Override
    protected Disruptor<ValueElement> generateDisruptor() {
        Disruptor<ValueElement> valueElementDisruptor = DisruptorFacade.multiValueElement();
        ringBuffer = valueElementDisruptor.getRingBuffer();
        valueElementDisruptor.handleEventsWith(generateHandler());
        valueElementDisruptor.start();
        return valueElementDisruptor;
    }

    @Override
    public void receiver(ValueElement valueElement) {
        int type = valueElement.getType();
        ESTypeEnum anEnum = ESTypeEnum.getEnum(type);
        switch (anEnum) {
            case Insert_Chapters_List:
                List<ChaptersBean> values = (List) valueElement.getValue();
                for (ChaptersBean contentBean :values){
                    UrlBean urlBean = urlService.extractLink(contentBean.getUrl());
                    //send(v,ESTypeEnum.Insert_Content.getIndex());
                    send(urlBean,ESTypeEnum.Insert_Chapters.getIndex());
                }
                break;
            case Insert_Chapters:
                UrlBean urlBean= (UrlBean) valueElement.getValue();
                chaptersSerive.analysisContent(urlBean);
                break;
            default:
                break;
        }
    }
}
