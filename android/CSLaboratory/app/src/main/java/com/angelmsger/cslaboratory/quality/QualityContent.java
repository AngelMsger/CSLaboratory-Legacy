package com.angelmsger.cslaboratory.quality;

import com.angelmsger.cslaboratory.quality.ad.QualityADItem;
import com.angelmsger.cslaboratory.quality.article.QualityArticleItem;
import com.angelmsger.cslaboratory.quality.category.QualityCategoryItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 为用户接口提供内容的帮助类。
 * TODO: 根据需要替换为具体实现。
 */
public class QualityContent {

    /**
     * 数据项实例数组。
     */
    public final List<QualityADItem> AD_ITEMS = new LinkedList<>();
    public final List<QualityCategoryItem> CLASS_ITEMS = new ArrayList<>();
    public final List<QualityArticleItem> QUALITY_ITEMS = new ArrayList<>();

    /**
     * 互斥更新 {@link QualityRecyclerViewAdapter} 数据集，
     * 此方法认为得到的数据条目已经根据 ID 由大到小排列，且
     * ID 越大代表数据优先级越高。
     * @return 新增条目数量
     */
    synchronized int update(QualityContent qualityContent, boolean pushToBottom) {
        int updateCount = 0;

        if (qualityContent != null) {

            if (AD_ITEMS.size() == 0) {
                AD_ITEMS.addAll(qualityContent.AD_ITEMS);
            }
            else {
                for (int i = 0; i < qualityContent.AD_ITEMS.size()
                        && qualityContent.AD_ITEMS.get(i).getId() > AD_ITEMS.get(0).getId(); i++) {
                    AD_ITEMS.add(0, qualityContent.AD_ITEMS.get(i));
                    AD_ITEMS.remove(AD_ITEMS.size() - 1);
                }
            }

            CLASS_ITEMS.addAll(qualityContent.CLASS_ITEMS);

            int i;
            if (QUALITY_ITEMS.size() == 0) {
                QUALITY_ITEMS.addAll(qualityContent.QUALITY_ITEMS);
                updateCount += qualityContent.QUALITY_ITEMS.size();
            }
            else if (pushToBottom) {
                // 在尾部插入新数据集，插入依据为新数据比现有数据最后一项 ID 更小
                for (i = qualityContent.QUALITY_ITEMS.size(); i > 0
                        && qualityContent.QUALITY_ITEMS.get(i - 1).getId()
                        < QUALITY_ITEMS.get(QUALITY_ITEMS.size() - 1).getId(); i--);
                QUALITY_ITEMS.addAll(qualityContent.QUALITY_ITEMS
                        .subList(i, qualityContent.QUALITY_ITEMS.size()));
                updateCount += qualityContent.QUALITY_ITEMS.size() - i;
            } else {
                // 在头部插入新数据集，插入依据为新数据比现有数据最前一项 ID 更大
                for (i = 0; i < qualityContent.QUALITY_ITEMS.size()
                        && qualityContent.QUALITY_ITEMS.get(i).getId()
                        > QUALITY_ITEMS.get(0).getId(); i++);
                QUALITY_ITEMS.addAll(0, qualityContent.QUALITY_ITEMS.subList(0, i));
                updateCount += i;
            }
        }

        return updateCount;
    }

}
