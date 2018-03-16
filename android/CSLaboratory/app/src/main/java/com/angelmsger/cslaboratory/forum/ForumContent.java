package com.angelmsger.cslaboratory.forum;

import com.angelmsger.cslaboratory.forum.topic.ForumTopicItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 为用户接口提供内容的帮助类。
 * TODO: 根据需要替换为具体实现。
 */
public class ForumContent {

    /**
     * 数据项实例数组。
     */
    public final List<ForumTopicItem> ITEMS = new ArrayList<ForumTopicItem>();

    synchronized int update(ForumContent forumContent, boolean pushToBottom) {
        int updateCount = 0;

        if (forumContent != null) {
            int i;
            if (ITEMS.size() == 0) {
                ITEMS.addAll(forumContent.ITEMS);
                updateCount += forumContent.ITEMS.size();
            }
            else if (pushToBottom) {
                // 在尾部插入新数据集，插入依据为新数据比现有数据最后一项 ID 更小
                for (i = forumContent.ITEMS.size(); i > 0
                        && forumContent.ITEMS.get(i - 1).getId()
                        < ITEMS.get(ITEMS.size() - 1).getId(); i--);
                ITEMS.addAll(forumContent.ITEMS
                        .subList(i, forumContent.ITEMS.size()));
                updateCount += forumContent.ITEMS.size() - i;
            } else {
                // 在头部插入新数据集，插入依据为新数据比现有数据最前一项 ID 更大
                for (i = 0; i < forumContent.ITEMS.size()
                        && forumContent.ITEMS.get(i).getId()
                        > ITEMS.get(0).getId(); i++);
                ITEMS.addAll(0, forumContent.ITEMS.subList(0, i));
                updateCount += i;
            }
        }

        return updateCount;
    }

}
