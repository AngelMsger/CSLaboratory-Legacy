package com.angelmsger.cslaboratory.quality.category;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 数据项实例类。
 */
@Entity(nameInDb = "quality_categories")
public class QualityCategoryItem {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String title;

    @NotNull
    private String imageURI;

    public QualityCategoryItem() {
    }

    @Keep
    public QualityCategoryItem(Long id, String title, String imageURI) {
        this.id = id;
        this.title = title;
        this.imageURI = imageURI;
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
}
