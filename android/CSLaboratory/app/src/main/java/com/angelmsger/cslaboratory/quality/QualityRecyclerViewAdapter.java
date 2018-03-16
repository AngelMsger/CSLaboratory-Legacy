package com.angelmsger.cslaboratory.quality;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelmsger.cslaboratory.quality.QualityFragment.OnListFragmentInteractionListener;
import com.angelmsger.cslaboratory.R;
import com.angelmsger.cslaboratory.quality.ad.QualityADItem;
import com.angelmsger.cslaboratory.quality.article.QualityArticleItem;
import com.angelmsger.cslaboratory.quality.category.QualityCategoryItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * {@link RecyclerView.Adapter} 可以展示一些 {@link QualityArticleItem}
 * 并且调用指定的 {@link OnListFragmentInteractionListener}.
 * TODO: 替换为自定义代码段。
 */
public class QualityRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<QualityADItem> mQualityADItems;
    private final List<QualityCategoryItem> mQualityCategoryItems;
    private final List<QualityArticleItem> mQualityArticleItems;
    private final int mColCount;
    private final LayoutInflater mInflater;
    private final OnListFragmentInteractionListener mListener;

    private static final int mCategoryItemsRowCount = 2;
    private static final int mQualityItemsRowCount = 8;

    private final int mCategoryItemsCount;
    private final int mPageCapacity;

    public QualityRecyclerViewAdapter(List<QualityADItem> QualityADItems,
                                      List<QualityCategoryItem> qualityCategoryItems,
                                      List<QualityArticleItem> qualityArticleItems,
                                      final int colCount,
                                      LayoutInflater inflater,
                                      OnListFragmentInteractionListener listener) {
        mQualityADItems = QualityADItems;
        mQualityCategoryItems = qualityCategoryItems;
        mQualityArticleItems = qualityArticleItems;
        mColCount = colCount;
        mInflater = inflater;
        mListener = listener;
        mCategoryItemsCount =  colCount * mCategoryItemsRowCount;
        mPageCapacity = mCategoryItemsCount + mQualityItemsRowCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Integer.MAX_VALUE;
        }
        else {
            return (position - 1) % mPageCapacity;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Integer.MAX_VALUE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_quality_ad, parent, false);
            return new ADViewHolder(view);
        }
        else if (viewType < mCategoryItemsCount) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_quality_category, parent, false);
            return new CategoryViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_quality_quality, parent, false);
            return new QualityViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        if (viewType == Integer.MAX_VALUE) {
            ((ADViewHolder)holder).bind(mQualityADItems);
        }
        else if (viewType < mCategoryItemsCount) {
            final int redirect = ((position - 1) / mPageCapacity)
                    * (mColCount * mCategoryItemsRowCount) + viewType;
            ((CategoryViewHolder)holder).bind(mQualityCategoryItems.get(redirect));
        }
        else {
            final int redirect = ((position - 1) / mPageCapacity)
                    * mQualityItemsRowCount + viewType - mCategoryItemsCount;
            ((QualityViewHolder)holder).bind(mQualityArticleItems.get(redirect));
        }
    }

    @Override
    public int getItemCount() {
        return 1 + mQualityArticleItems.size() / mQualityItemsRowCount
                * mCategoryItemsCount + mQualityArticleItems.size();
    }

    // 根据当前持有数据量返回下一次请求开始点 offset
    public int getNextRequestOffset() {
        return mQualityArticleItems.size();
    }

    public class ADViewHolder extends ViewHolder {
        public final View mView;
        public final ViewPager mContainer;
        public final CircleIndicator mIndicator;
        public List<QualityADItem> mItems;

        public ADViewHolder(View view) {
            super(view);
            mView = view;
            mContainer = (ViewPager) view.findViewById(R.id.container);
            mIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
        }

        public void bind(List<QualityADItem> items) {
            mItems = items;
            mContainer.setAdapter(new ADPagerAdapter());
            int heightPixels = (int)(mInflater.getContext().getResources().
                    getDisplayMetrics().widthPixels * 0.5625);
            ViewGroup.LayoutParams params = mContainer.getLayoutParams();
            params.height = heightPixels;
            mContainer.setLayoutParams(params);
            mIndicator.setViewPager(mContainer);
        }
    }

    public class CategoryViewHolder extends ViewHolder {
        public final View mView;
        public final ImageView mImage;
        public final TextView mTitle;
        public QualityCategoryItem mItem;

        public CategoryViewHolder(View view) {
            super(view);
            mView = view;
            mImage = (ImageView) view.findViewById(R.id.image);
            mTitle = (TextView) view.findViewById(R.id.title);
        }

        public void bind(final QualityCategoryItem item) {
            mItem = item;
            Glide.with(mInflater.getContext()).load(item.getImageURI()).into(mImage);
            mTitle.setText(item.getTitle());
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // 唤醒活动的回调接口 (activity, 如果 fragment 被绑定) 有一个选项被选择。
                        mListener.onCategoryItemClick(item);
                    }
                }
            });
        }
    }

    public class QualityViewHolder extends ViewHolder {
        public final View mView;
        public final TextView mClass;
        public final ImageView mImage;
        public final TextView mTitle;
        public final TextView mContent;
        public final TextView mAttitude;
        public QualityArticleItem mItem;

        public QualityViewHolder(View view) {
            super(view);
            mView = view;
            mClass = (TextView) view.findViewById(R.id.group);
            mImage = (ImageView) view.findViewById(R.id.image);
            mTitle = (TextView) view.findViewById(R.id.title);
            mContent = (TextView) view.findViewById(R.id.content);
            mAttitude = (TextView) view.findViewById(R.id.attitude);
        }

        public void bind(final QualityArticleItem item) {
            mItem = item;
            mClass.setText(item.getGroup());
            Glide.with(mInflater.getContext()).load(item.getImageURI()).into(mImage);
            mTitle.setText(item.getTitle());
            mContent.setText(item.getContent());
            mAttitude.setText(item.getBangCount() + "赞" + item.getCommentCount() + "评论");
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // 唤醒活动的回调接口 (activity, 如果 fragment 被绑定) 有一个选项被选择。
                        mListener.onQualityItemClick(item);
                    }
                }
            });
        }
    }

    public class ADPagerAdapter extends PagerAdapter {
        private final List<ImageView> mADViews;

        public ADPagerAdapter() {
            mADViews = new ArrayList<>();
            View.OnClickListener onImageClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view instanceof ImageView) {
                        mListener.onADItemClick(mQualityADItems.get(mADViews.indexOf(view)));
                    }
                }
            };
            for (QualityADItem item : mQualityADItems) {
                ImageView imageView =  (ImageView)mInflater
                        .inflate(R.layout.fragment_quality_ad_item, null)
                        .findViewById(R.id.image);
                imageView.setOnClickListener(onImageClickListener);
                Glide.with(mInflater.getContext())
                        .load(item.getImageURI())
                        .into(imageView);
                mADViews.add(imageView);
            }
        }

        @Override
        public int getCount() {
            return mADViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mADViews.get(position);
            ViewParent parent = imageView.getParent();
            if (parent != null) {
                ((ViewGroup)parent).removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mADViews.get(position));
        }
    }
}
