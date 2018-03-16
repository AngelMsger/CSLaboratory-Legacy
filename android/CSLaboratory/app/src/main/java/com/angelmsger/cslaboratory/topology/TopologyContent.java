package com.angelmsger.cslaboratory.topology;

import com.angelmsger.cslaboratory.topology.field.TopologyFieldItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 为用户接口提供内容的帮助类。
 * TODO: 根据需要替换为具体实现。
 */
public class TopologyContent {
    /**
     * 数据项实例数组。
     */
    public final List<TopologyFieldItem> ITEMS = new ArrayList<TopologyFieldItem>();

    synchronized int update(TopologyContent topologyContent, boolean pushToBottom) {
        int updateCount = 0;

        if (topologyContent != null) {
            int i;
            if (ITEMS.size() == 0) {
                ITEMS.addAll(topologyContent.ITEMS);
                updateCount += topologyContent.ITEMS.size();
            }
            else if (pushToBottom) {
                // 在尾部插入新数据集，插入依据为新数据比现有数据最后一项 ID 更小
                for (i = topologyContent.ITEMS.size(); i > 0
                        && topologyContent.ITEMS.get(i - 1).getId()
                        < ITEMS.get(ITEMS.size() - 1).getId(); i--);
                ITEMS.addAll(topologyContent.ITEMS
                        .subList(i, topologyContent.ITEMS.size()));
                updateCount += topologyContent.ITEMS.size() - i;
            } else {
                // 在头部插入新数据集，插入依据为新数据比现有数据最前一项 ID 更大
                for (i = 0; i < topologyContent.ITEMS.size()
                        && topologyContent.ITEMS.get(i).getId()
                        > ITEMS.get(0).getId(); i++);
                ITEMS.addAll(0, topologyContent.ITEMS.subList(0, i));
                updateCount += i;
            }
        }

        return updateCount;
    }

}
