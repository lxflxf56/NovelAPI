package cn.stormzi.novelapi.facade.bean.cache;

import java.io.Serializable;
import java.util.List;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public class ChaptersBean implements Serializable {
    protected String bookName,author,createTime,updateTime,description,url,website,icoUrl;
    protected List<String> chaptersTitle;
    protected List<String> chaptersUrl;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<String> getChaptersTitle() {
        return chaptersTitle;
    }

    public void setChaptersTitle(List<String> chaptersTitle) {
        this.chaptersTitle = chaptersTitle;
    }

    public List<String> getChaptersUrl() {
        return chaptersUrl;
    }

    public void setChaptersUrl(List<String> chaptersUrl) {
        this.chaptersUrl = chaptersUrl;
    }

    public String getIcoUrl() {
        return icoUrl;
    }

    public void setIcoUrl(String icoUrl) {
        this.icoUrl = icoUrl;
    }
}
