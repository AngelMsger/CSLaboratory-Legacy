package com.angelmsger.cslaboratory.course.subclass;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 课程分类数据项实例类。
 */
@Entity(nameInDb = "course_subclasses")
public class CourseSubclassItem {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String imageURI;

    @NotNull
    @Unique
    private String title;

    public CourseSubclassItem() {
    }

    @Keep
    public CourseSubclassItem(Long id, String imageURI, String title) {
        this.id = id;
        this.imageURI = imageURI;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
