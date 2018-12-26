package cn.stormzi.novelapi.service.analysis;

import cn.stormzi.novelapi.NovelapiApplication;
import cn.stormzi.novelapi.facade.analysis.ChaptersAnalysisFacade;
import cn.stormzi.novelapi.facade.analysis.PatternMap;
import cn.stormzi.novelapi.facade.bean.analysis.BookSelectInfo;
import cn.stormzi.novelapi.facade.bean.analysis.UrlBean;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;

import cn.stormzi.novelapi.util.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-21
 **/

@Service("chaptersSerive")
public class ChaptersSerive implements ChaptersAnalysisFacade, PatternMap {

    private static Map<String, BookSelectInfo> patternMap;

    ExecutorService threadPool = Executors.newCachedThreadPool();

    protected static final Logger logger = LoggerFactory.getLogger(ChaptersSerive.class);

    @Override
    public ChaptersBean getContentFromWebsite(UrlBean bean) {
        BookSelectInfo bookSelectInfo = getPatternMap().get(bean.getHostname());
        ChaptersBean ChaptersBean = null;
        try {
            Document document = Jsoup.connect(bean.getRealurl()).timeout(5000).get();
            //抽出bean
            ChaptersBean = analysisDocument(document, bookSelectInfo);
            return ChaptersBean;
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (Exception e) {

        }
        return null;
    }

    public ChaptersBean analysisDocument(Document document, BookSelectInfo bookSelectInfo) {
        ChaptersBean chaptersBean = new ChaptersBean();
        chaptersBean.setChaptersTitle(new ArrayList<>());
        chaptersBean.setChaptersUrl(new ArrayList<>());
        String detailChaptersUrl = bookSelectInfo.getDetailChaptersUrl();
        Future<ChaptersBean> submit = null;
        if (detailChaptersUrl != null) {
            String detailLink = HttpUtil.itrElement(0, detailChaptersUrl.split(" "), document.body().children());
            submit = threadPool.submit(new OtherRequest(detailLink));
            chaptersBean.setBookName(HttpUtil.itrElement(bookSelectInfo.getDetailBookName(), document));
            chaptersBean.setAuthor(HttpUtil.itrElement(bookSelectInfo.getDetailBookAuthor(), document));
            chaptersBean.setDescription(HttpUtil.itrElement(bookSelectInfo.getBookDesc(), document));
            //...

        } else {
            //...
        }


        return chaptersBean;
    }

    @Override
    public boolean saveContentIntoCache(ChaptersBean chaptersBean) {
        return false;
    }

    @Override
    public String getContentBeanFromCache(UrlBean urlBean) {
        return null;
    }

    @Override
    public synchronized void generateMap() {
        patternMap= NovelapiApplication.getPatternMap();
    }

    @Override
    public Map<String, BookSelectInfo> getPatternMap() {
        if (patternMap==null){
            generateMap();
        }
        return patternMap;
    }

    class OtherRequest implements Callable<ChaptersBean> {

        private String link;

        public OtherRequest(String link) {
            this.link = link;
        }

        @Override
        public ChaptersBean call() throws Exception {
            //...异步处理
            return null;
        }
    }
}
