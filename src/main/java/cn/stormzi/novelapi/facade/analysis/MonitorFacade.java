package cn.stormzi.novelapi.facade.analysis;

import cn.stormzi.novelapi.facade.bean.analysis.MonitorInfoBean;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-07
 **/


public interface MonitorFacade {
    MonitorInfoBean getMonitorInfoBean() throws Exception;
}
