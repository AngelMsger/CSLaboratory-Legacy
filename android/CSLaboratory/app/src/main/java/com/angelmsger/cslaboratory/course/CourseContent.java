package com.angelmsger.cslaboratory.course;

import com.angelmsger.cslaboratory.course.course.CourseCourseItem;
import com.angelmsger.cslaboratory.course.subclass.CourseCategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 为用户接口提供内容的帮助类。
 * TODO: 根据需要替换为具体实现。
 */
public class CourseContent {

    /**
     * 数据项实例数组。
     */
    public final List<CourseCategoryItem> CATEGORY_ITEMS = new ArrayList<>();
    public final List<CourseCourseItem> COURSE_ITEMS = new ArrayList<CourseCourseItem>();

    synchronized int update(CourseContent courseContent, boolean pushToBottom) {
        int updateCount = 0;

        if (courseContent != null) {
            CATEGORY_ITEMS.clear();
            CATEGORY_ITEMS.addAll(courseContent.CATEGORY_ITEMS);

            int i;
            if (COURSE_ITEMS.size() == 0) {
                COURSE_ITEMS.addAll(courseContent.COURSE_ITEMS);
                updateCount += courseContent.COURSE_ITEMS.size();
            }
            else if (pushToBottom) {
                // 在尾部插入新数据集，插入依据为新数据比现有数据最后一项 ID 更小
                for (i = courseContent.COURSE_ITEMS.size(); i > 0
                        && courseContent.COURSE_ITEMS.get(i - 1).getId()
                        < COURSE_ITEMS.get(COURSE_ITEMS.size() - 1).getId(); i--);
                COURSE_ITEMS.addAll(courseContent.COURSE_ITEMS
                        .subList(i, courseContent.COURSE_ITEMS.size()));
                updateCount += courseContent.COURSE_ITEMS.size() - i;
            } else {
                // 在头部插入新数据集，插入依据为新数据比现有数据最前一项 ID 更大
                for (i = 0; i < courseContent.COURSE_ITEMS.size()
                        && courseContent.COURSE_ITEMS.get(i).getId()
                        > COURSE_ITEMS.get(0).getId(); i++);
                    COURSE_ITEMS.addAll(0, courseContent.COURSE_ITEMS.subList(0, i));
                updateCount += i;
            }
        }

        return updateCount;
    }

}
