package cn.stormzi.novelapi.facade.analysis;

import cn.stormzi.novelapi.facade.bean.cache.SearchBean;

public interface SearchAnalysisFacade {
    SearchBean searchFromCacheByBookname();
    SearchBean searchFromCacheByAuthor();
    SearchBean searchFromWebsiteByBookname(String bookname,String website);
    SearchBean searchFromWebsiteByAuthor(String bookname,String website);


}
