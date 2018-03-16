package com.angelmsger.cslaboratory.daily;

import com.angelmsger.cslaboratory.daily.article.DailyArticleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 为用户接口提供内容的帮助类。
 * TODO: 根据需要替换为具体实现。
 */
public class DailyContent {

    /**
     * 数据项实例数组。
     */
    public final List<DailyArticleItem> ITEMS = new ArrayList<DailyArticleItem>();

    synchronized int update(DailyContent dailyContent, boolean pushToBottom) {
        int updateCount = 0;

        if (dailyContent != null) {
            int i;
            if (ITEMS.size() == 0) {
                ITEMS.addAll(dailyContent.ITEMS);
                updateCount += dailyContent.ITEMS.size();
            }
            else if (pushToBottom) {
                // 在尾部插入新数据集，插入依据为新数据比现有数据最后一项 ID 更小
                for (i = dailyContent.ITEMS.size(); i > 0
                        && dailyContent.ITEMS.get(i - 1).getId()
                        < ITEMS.get(ITEMS.size() - 1).getId(); i--);
                ITEMS.addAll(dailyContent.ITEMS
                        .subList(i, dailyContent.ITEMS.size()));
                updateCount += dailyContent.ITEMS.size() - i;
            } else {
                // 在头部插入新数据集，插入依据为新数据比现有数据最前一项 ID 更大
                for (i = 0; i < dailyContent.ITEMS.size()
                        && dailyContent.ITEMS.get(i).getId()
                        > ITEMS.get(0).getId(); i++);
                ITEMS.addAll(0, dailyContent.ITEMS.subList(0, i));
                updateCount += i;
            }
        }

        return updateCount;
    }

}

