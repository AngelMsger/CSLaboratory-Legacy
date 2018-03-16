package com.angelmsger.cslaboratory.topology.field;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 数据项实例类。
 */
@Entity(nameInDb = "topology_fields", indexes = {
        @Index(value = "title", unique = true, name = "TitleIndex")
})
public class TopologyFieldItem {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String backgroundURI;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String imageURI;

    public TopologyFieldItem() {
    }

    @Keep
    public TopologyFieldItem(Long id, String backgroundURI, String title,
                             String content, String imageURI) {
        this.id = id;
        this.backgroundURI = backgroundURI;
        this.title = title;
        this.content = content;
        this.imageURI = imageURI;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackgroundURI() {
        return backgroundURI;
    }

    public void setBackgroundURI(String backgroundURI) {
        this.backgroundURI = backgroundURI;
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

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
