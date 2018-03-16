package com.angelmsger.cslaboratory.course.subclass;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 课程分类组组数据项实例类。
 */
@Entity(nameInDb = "course_categories")
public class CourseCategoryItem {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String title;

    @Transient
    private List<CourseSubclassItem> subclasses;

    public CourseCategoryItem() {
    }

    @Keep
    public CourseCategoryItem(Long id, String title, List<CourseSubclassItem> subclasses) {
        this.id = id;
        this.title = title;
        this.subclasses = subclasses;
    }

    @Generated(hash = 1103060316)
    public CourseCategoryItem(Long id, @NotNull String title) {
        this.id = id;
        this.title = title;
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

    @Keep
    public List<CourseSubclassItem> getSubclasses() {
        return subclasses;
    }

    @Keep
    public void setSubclasses(List<CourseSubclassItem> subclasses) {
        this.subclasses = subclasses;
    }
}
