package com.angelmsger.cslaboratory.daily.article;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 数据项实例类。
 */
@Entity(nameInDb = "daily_articles",indexes = {
        @Index(value = "title, postTime DESC", unique = true, name = "TitlePostTimeIndex")
})
public class DailyArticleItem {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String bannerURI;

    @NotNull
    private String imageURI;

    @NotNull
    private String title;

    private String content;
    private String postTime;

    public DailyArticleItem() {
    }

    @Keep
    public DailyArticleItem(Long id, String bannerURI, String imageURI, String title, String content, String postTime) {
        this.id = id;
        this.bannerURI = bannerURI;
        this.imageURI = imageURI;
        this.title = title;
        this.content = content;
        this.postTime = postTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBannerURI() {
        return bannerURI;
    }

    public void setBannerURI(String bannerURI) {
        this.bannerURI = bannerURI;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}
