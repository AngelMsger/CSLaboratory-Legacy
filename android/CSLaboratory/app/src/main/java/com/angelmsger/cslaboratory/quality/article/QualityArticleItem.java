package com.angelmsger.cslaboratory.quality.article;

import android.support.annotation.IntDef;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 数据项实例类。
 */
@Entity(nameInDb = "quality_articles", indexes = {
        @Index(value = "title, bangCount DESC", name = "BangCountIndex"),
        @Index(value = "title, commentCount DESC", name = "CommentCountIndex")
})
public class QualityArticleItem {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String title;

    private String group;

    @NotNull
    private String content;

    private String imageURI;
    private String actionURI;

    @NotNull
    private int bangCount;

    @NotNull
    private int commentCount;

    public QualityArticleItem() {
    }

    @Keep
    public QualityArticleItem(Long id, String title, String group, String content,
                              String imageURI, String actionURI, int bangCount, int commentCount) {
        this.id = id;
        this.title = title;
        this.group = group;
        this.content = content;
        this.imageURI = imageURI;
        this.actionURI = actionURI;
        this.bangCount = bangCount;
        this.commentCount = commentCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getActionURI() {
        return actionURI;
    }

    public void setActionURI(String actionURI) {
        this.actionURI = actionURI;
    }

    public int getBangCount() {
        return bangCount;
    }

    public void setBangCount(int bangCount) {
        this.bangCount = bangCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
