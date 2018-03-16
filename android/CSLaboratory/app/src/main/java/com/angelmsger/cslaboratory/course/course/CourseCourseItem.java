package com.angelmsger.cslaboratory.course.course;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 课程项数据项实例类。
 */
@Entity(nameInDb = "course_courses", indexes = {
        @Index(value = "title, learnNum DESC", name = "TitleLearnNumIndex")
})
public class CourseCourseItem {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String imageURI;

    @NotNull
    private String title;

    @NotNull
    private String actionURI;

    @NotNull
    private int learnNum;

    public CourseCourseItem() {
    }

    @Keep
    public CourseCourseItem(Long id, String imageURI, String title, String actionURI, int learnNum) {
        this.id = id;
        this.imageURI = imageURI;
        this.title = title;
        this.actionURI = actionURI;
        this.learnNum = learnNum;
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

    public String getActionURI() {
        return actionURI;
    }

    public void setActionURI(String actionURI) {
        this.actionURI = actionURI;
    }

    public int getLearnNum() {
        return learnNum;
    }

    public void setLearnNum(int learnNum) {
        this.learnNum = learnNum;
    }
}
