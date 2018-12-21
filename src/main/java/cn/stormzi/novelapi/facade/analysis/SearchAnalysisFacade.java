package cn.stormzi.novelapi.facade.analysis;

import cn.stormzi.novelapi.facade.bean.cache.SearchBean;

import java.util.Set;

public interface SearchAnalysisFacade {
    String searchFromCacheByBookname(String bookname);
    String searchFromCacheByAuthor(String author);
    SearchBean searchFromWebsiteByBookname(String bookname,String website);
    String searchOtherBook(String bookname);
    Set getAllWebsite();

}
