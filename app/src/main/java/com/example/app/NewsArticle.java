package com.example.app;

import java.util.List;

public class NewsArticle {
    private String title;
    private String link;
    private String imgUrl;
    private List<String> categories;

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    private String publishedDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", categories=" + categories +
                ", publishedDate='" + publishedDate + '\'' +
                '}';
    }
}
