package cn.stormzi.novelapi.service.analysis;

import cn.stormzi.novelapi.NovelapiApplication;
import cn.stormzi.novelapi.facade.analysis.PatternMap;
import cn.stormzi.novelapi.facade.analysis.SearchAnalysisFacade;
import cn.stormzi.novelapi.facade.bean.analysis.BookSelectInfo;
import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import cn.stormzi.novelapi.facade.bean.cache.SearchBean;
import cn.stormzi.novelapi.facade.cache.ESFacade;
import cn.stormzi.novelapi.util.elasticsearch.QueryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-19
 **/


@Service("searchService")
public class SearchService implements SearchAnalysisFacade, PatternMap {

    private static Map<String, BookSelectInfo> patternMap;

    protected static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    ESFacade esService;


    public SearchBean analysisDocument(Document document,BookSelectInfo bookSelectInfo ){
        SearchBean searchBean = new SearchBean();
        searchBean.setNovelItems(new ArrayList<>());
        List<String> result = new ArrayList();

        //BookName
        itrElement(0,bookSelectInfo.getBookName().split(" "),document.body().children(),result);
        int size=result.size();
        for (String s:result){
            ChaptersBean bean=new ChaptersBean();
            bean.setBookName(s);
            searchBean.getNovelItems().add(bean);
        }
        result.clear();

        //BookAuthor
        itrElement(0,bookSelectInfo.getBookAuthor().split(" "),document.body().children(),result);
        for (int i=0;i<size;i++){
            searchBean.getNovelItems().get(i).setAuthor(result.get(i));
        }
        result.clear();

        //BookUrl
        itrElement(0,bookSelectInfo.getBookUrl().split(" "),document.body().children(),result);
        for (int i=0;i<size;i++){
            searchBean.getNovelItems().get(i).setUrl(result.get(i));
        }
        result.clear();

        return searchBean;
    }

    @Override
    public String searchFromCacheByBookname(String bookname) {
        String json= QueryBuilder.similarQuery("bookName",bookname);
        try {
            String select = esService.select(NovelapiApplication.index, NovelapiApplication.contentType, json);
            return select;
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error(e.toString());
        }
        return "{}";
    }

    @Override
    public String searchFromCacheByAuthor(String author) {
        String json= QueryBuilder.similarQuery("author",author);
        try {
            String select = esService.select(NovelapiApplication.index, NovelapiApplication.contentType, json);
            return select;
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error(e.toString());
        }
        return "{}";
    }

    @Override
    public SearchBean searchFromWebsiteByBookname(String bookname, String website) {
        BookSelectInfo bookSelectInfo = getPatternMap().get(website);
        //String searchUrl = bookSelectInfo.getSearchUrl().replace("%s", bookname);
        SearchBean searchBean=null;
        try {
            String encode = URLEncoder.encode(bookname, bookSelectInfo.getSearchEncoding());
            String searchUrl = String.format(bookSelectInfo.getSearchUrl(), encode);
            Document document = Jsoup.connect(searchUrl).timeout(5000).get();
            //抽出bean
            searchBean = analysisDocument(document,bookSelectInfo);
            return searchBean;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String searchOtherBook(String bookname) {
        String json= QueryBuilder.term("bookname",bookname);
        try {
            String select = esService.select(NovelapiApplication.index, NovelapiApplication.contentType, json);
            return select;
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error(e.toString());
        }
        return "{}";
    }


    @Override
    public Set getAllWebsite() {
        Set<String> strings = getPatternMap().keySet();
        return strings;
    }

    @Override
    public synchronized void generateMap() {
        Map patternMap = NovelapiApplication.getPatternMap();
        Collection<BookSelectInfo> values = patternMap.values();
        this.patternMap =new HashMap<>();
        for (BookSelectInfo value : values) {
            String webname = value.getName();
            this.patternMap.put(webname, value);
        }
        //this.patternMap = patternMap;
    }

    @Override
    public Map<String, BookSelectInfo> getPatternMap() {
        if (patternMap == null) {
            generateMap();
        }
        return patternMap;
    }

    public static void itrElement(int i, String[] s, Elements element, List result) {
        if (element.isEmpty()) {
            return;
        }
        if (i >= s.length) {
            result.add(element.get(0).html());
            return;
        }

        String str = s[i];
        if (isNum(str.charAt(0))) {
            //result.add(element.get(0).html());
            Element element1 = element.get(Integer.parseInt(str.charAt(0)+""));
            if (str.length() > 1) {// 0[attr]
                str = str.substring(1);
                String attr = element1.attr(str);
                result.add(attr);
            }else {
                result.add(element1.html());
            }
            return;
        } else if (str.charAt(0) == '[') {
            String attr = element.attr(str);
            result.add(attr);
        } else if (str.charAt(0) == '@') {
            str = str.substring(1, str.length());
            Elements select = element.select(str);
            for (Element element1 : select) {
                itrElement(i + 1, s, element1.children(), result);
            }
        } else {
            itrElement(i + 1, s, element.select(str), result);
        }

    }


    public static boolean isNum(char c) {
        if (47 < c && c < 57) {
            return true;
        }
        return false;
    }
}
