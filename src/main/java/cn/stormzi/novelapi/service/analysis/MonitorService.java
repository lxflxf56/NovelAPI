package cn.stormzi.novelapi.service.analysis;

import cn.stormzi.novelapi.facade.analysis.MonitorFacade;
import cn.stormzi.novelapi.facade.bean.analysis.MonitorInfoBean;
import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.*;


/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-07
 **/


public class MonitorService implements MonitorFacade {
    private static final int CPUTIME = 5000;

    private static final int PERCENT = 100;

    private static final int FAULTLENGTH = 10;

    protected static final Logger logger = getLogger();
    protected static Logger getLogger(){
        return LoggerFactory.getLogger(MonitorFacade.class);
    }


    @Override
    public MonitorInfoBean getMonitorInfoBean() throws Exception {
        int kb = 1024;

        // 可使用内存
        long totalMemory = Runtime.getRuntime().totalMemory() / kb;
        // 剩余内存
        long freeMemory = Runtime.getRuntime().freeMemory() / kb;
        // 最大可使用内存
        long maxMemory = Runtime.getRuntime().maxMemory() / kb;

        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();

        // 操作系统
        String osName = System.getProperty("os.name");
        // 总的物理内存
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;
        // 已使用的物理内存
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
                .getFreePhysicalMemorySize())
                / kb;

        // 获得线程总数
        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                .getParent() != null; parentThread = parentThread.getParent())
            ;
        int totalThread = parentThread.activeCount();

        // 构造返回对象
        MonitorInfoBean infoBean = new MonitorInfoBean();
        infoBean.setFreeMemory(freeMemory);
        infoBean.setFreePhysicalMemorySize(freePhysicalMemorySize);
        infoBean.setMaxMemory(maxMemory);
        infoBean.setOsName(osName);
        infoBean.setTotalMemory(totalMemory);
        infoBean.setTotalMemorySize(totalMemorySize);
        infoBean.setTotalThread(totalThread);
        infoBean.setUsedMemory(usedMemory);
        return infoBean;
    }



    public static Map<?, ?> cpuinfo() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                logger.debug(e2.toString());
            }
        }
        return map;
    }

    public static double getMemUsage(MonitorInfoBean monitorInfoBean) {
        String osName = monitorInfoBean.getOsName();
        if (osName.toLowerCase().contains("windows")
                || osName.toLowerCase().contains("win")) {

            try {
                OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                        .getOperatingSystemMXBean();
// 总的物理内存+虚拟内存
                long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
// 剩余的物理内存
                long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
                Double usage = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
                BigDecimal b1 = new BigDecimal(usage);
                double memoryUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return memoryUsage;
            } catch (Exception e) {
                logger.debug(e.toString());
            }
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            InputStreamReader inputs = null;
            BufferedReader buffer = null;
            try {
                inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
                buffer = new BufferedReader(inputs);
                String line = "";
                while (true) {
                    line = buffer.readLine();
                    if (line == null)
                        break;
                    int beginIndex = 0;
                    int endIndex = line.indexOf(":");
                    if (endIndex != -1) {
                        String key = line.substring(beginIndex, endIndex);
                        beginIndex = endIndex + 1;
                        endIndex = line.length();
                        String memory = line.substring(beginIndex, endIndex);
                        String value = memory.replace("kB", "").trim();
                        map.put(key, value);
                    }
                }

                long memTotal = Long.parseLong(map.get("MemTotal").toString());
                long memFree = Long.parseLong(map.get("MemFree").toString());
                long memused = memTotal - memFree;
                long buffers = Long.parseLong(map.get("Buffers").toString());
                long cached = Long.parseLong(map.get("Cached").toString());

                double usage = (double) (memused - buffers - cached) / memTotal * 100;
                BigDecimal b1 = new BigDecimal(usage);
                double memoryUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return memoryUsage;
            } catch (Exception e) {
                logger.debug(e.toString());
            } finally {
                try {
                    buffer.close();
                    inputs.close();
                } catch (Exception e2) {
                    logger.debug(e2.toString());
                }
            }

        }
        return 0.0;
    }


}
