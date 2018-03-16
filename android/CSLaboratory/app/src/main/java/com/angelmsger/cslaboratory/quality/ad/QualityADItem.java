package com.angelmsger.cslaboratory.quality.ad;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 数据项实例类。
 */
@Entity(nameInDb = "quality_ads")
public class QualityADItem {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String title;

    @NotNull
    private String imageURI;

    @NotNull
    private String actionURI;

    public QualityADItem() {
    }

    @Keep
    public QualityADItem(Long id, String title, String imageURI, String actionURI) {
        this.id = id;
        this.title = title;
        this.imageURI = imageURI;
        this.actionURI = actionURI;
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
}
