package cn.stormzi.novelapi;

import cn.stormzi.novelapi.facade.bean.analysis.BookSelectInfo;
import cn.stormzi.novelapi.util.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SpringBootApplication
public class NovelapiApplication {

    public static boolean useCache=true;
    public final static String index = "novelapi";
    public final static String pageType = "page";
    public final static String contentType = "content";
    public static Map patternMap;

    protected static final Logger logger = LoggerFactory.getLogger(NovelapiApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(NovelapiApplication.class, args);
        init();
    }

    public static void init(){
        String s = null;
        try {
            s = HttpUtil.get("https://raw.githubusercontent.com/lxflxf56/NovelAPI/master/src/main/resources/static/booklilst.json");
            List<BookSelectInfo> bookSelectInfos = JSON.parseArray(s, BookSelectInfo.class);
            patternMap=new TreeMap();
            for (BookSelectInfo book:bookSelectInfos){
                patternMap.put(book.getHost(),book);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("初始化失败",e);
            System.exit(1);
        }

    }

    public static Map getPatternMap() {
        return patternMap;
    }


}
