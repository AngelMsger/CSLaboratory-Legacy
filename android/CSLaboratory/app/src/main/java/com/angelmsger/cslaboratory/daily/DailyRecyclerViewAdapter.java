package com.angelmsger.cslaboratory.daily;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelmsger.cslaboratory.daily.DailyFragment.OnListFragmentInteractionListener;
import com.angelmsger.cslaboratory.R;
import com.angelmsger.cslaboratory.daily.article.DailyArticleItem;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.regex.Pattern;

/**
 * {@link RecyclerView.Adapter} 可以展示一些 {@link DailyArticleItem}
 * 并且调用指定的 {@link OnListFragmentInteractionListener}.
 * TODO: 替换为自定义代码段。
 */
public class DailyRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<DailyArticleItem> mDailyArticleItems;
    private final LayoutInflater mInflater;
    private final OnListFragmentInteractionListener mListener;

    private final Pattern mHtmlPattern = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);

    public DailyRecyclerViewAdapter(List<DailyArticleItem> dailyArticleItems,
                                    LayoutInflater inflater,
                                    OnListFragmentInteractionListener listener) {
        mDailyArticleItems = dailyArticleItems;
        mInflater = inflater;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_daily_item, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ((DailyViewHolder)holder).bind(mDailyArticleItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mDailyArticleItems.size();
    }

    // 根据当前持有数据量返回下一次请求开始点 offset
    public int getNextRequestOffset() {
        return mDailyArticleItems.size();
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImage;
        public final TextView mTitle;
        public final TextView mContent;
        public DailyArticleItem mItem;

        public DailyViewHolder(View view) {
            super(view);
            mView = view;
            mImage = (ImageView) view.findViewById(R.id.image);
            mTitle = (TextView) view.findViewById(R.id.title);
            mContent = (TextView) view.findViewById(R.id.content);
        }

        public void bind(final DailyArticleItem item) {
            mItem = item;
            Glide.with(mInflater.getContext()).load(item.getBannerURI()).into(mImage);
            mTitle.setText(item.getTitle());
            mContent.setText(mHtmlPattern.matcher(item.getContent()).replaceAll(""));
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // 唤醒活动的回调接口 (activity, 如果 fragment 被绑定) 有一个选项被选择。
                        mListener.onDailyItemClick(item);
                    }
                }
            });
        }
    }
}
