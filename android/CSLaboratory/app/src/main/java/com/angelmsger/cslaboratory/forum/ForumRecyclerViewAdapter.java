package com.angelmsger.cslaboratory.forum;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angelmsger.cslaboratory.forum.ForumFragment.OnListFragmentInteractionListener;
import com.angelmsger.cslaboratory.R;
import com.angelmsger.cslaboratory.forum.topic.ForumTopicItem;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} 可以展示一些 {@link ForumTopicItem}
 * 并且调用指定的 {@link OnListFragmentInteractionListener}.
 * TODO: 替换为自定义代码段。
 */
public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<ForumTopicItem> mForumTopicItems;
    private final LayoutInflater mInflater;
    private final OnListFragmentInteractionListener mListener;

    public ForumRecyclerViewAdapter(List<ForumTopicItem> forumTopicItems,
                                    LayoutInflater inflater,
                                    OnListFragmentInteractionListener listener) {
        mForumTopicItems = forumTopicItems;
        mInflater = inflater;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_forum_item, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ((ForumViewHolder)holder).bind(mForumTopicItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mForumTopicItems.size();
    }

    // 根据当前持有数据量返回下一次请求开始点 offset
    public int getNextRequestOffset() {
        return mForumTopicItems.size();
    }


    public class ForumViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CircleImageView mAvatar;
        public final TextView mNickname;
        public final TextView mTitle;
        public final TextView mContent;
        public ForumTopicItem mItem;

        public ForumViewHolder(View view) {
            super(view);
            mView = view;
            mAvatar = (CircleImageView) view.findViewById(R.id.avatar);
            mNickname = (TextView) view.findViewById(R.id.nickname);
            mTitle = (TextView) view.findViewById(R.id.title);
            mContent = (TextView) view.findViewById(R.id.content);
        }

        public void bind(final ForumTopicItem item) {
            mItem = item;
            Glide.with(mInflater.getContext()).load(item.getAvatarURI()).into(mAvatar);
            mNickname.setText(item.getNickname());
            mTitle.setText(item.getTitle());
            mContent.setText(item.getContent());
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // 唤醒活动的回调接口 (activity, 如果 fragment 被绑定) 有一个选项被选择。
                        mListener.onForumItemClick(item);
                    }
                }
            });
        }
    }
}
