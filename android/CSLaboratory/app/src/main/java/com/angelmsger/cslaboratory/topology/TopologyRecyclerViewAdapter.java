package com.angelmsger.cslaboratory.topology;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelmsger.cslaboratory.R;
import com.angelmsger.cslaboratory.topology.TopologyFragment.OnListFragmentInteractionListener;
import com.angelmsger.cslaboratory.topology.field.TopologyFieldItem;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} 可以展示一些 {@link TopologyFieldItem}
 * 并且调用指定的 {@link OnListFragmentInteractionListener}.
 * TODO: 替换为自定义代码段。
 */
public class TopologyRecyclerViewAdapter
        extends RecyclerView.Adapter<TopologyRecyclerViewAdapter.TopologyViewHolder> {

    private final List<TopologyFieldItem> mTopologyFieldItems;
    private final LayoutInflater mInflater;
    private final OnListFragmentInteractionListener mListener;

    public TopologyRecyclerViewAdapter(List<TopologyFieldItem> items,
                                       LayoutInflater inflater,
                                       OnListFragmentInteractionListener listener) {
        mTopologyFieldItems = items;
        mInflater = inflater;
        mListener = listener;
    }

    @Override
    public TopologyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_topology_item, parent, false);
        return new TopologyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TopologyViewHolder holder, int position) {
        holder.bind(mTopologyFieldItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mTopologyFieldItems.size();
    }

    // 根据当前持有数据量返回下一次请求开始点 offset
    public int getNextRequestOffset() {
        return mTopologyFieldItems.size();
    }

    public class TopologyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mBackground;
        public final TextView mTitle;
        public final TextView mContent;
        public final CircleImageView mImage;
        public TopologyFieldItem mItem;

        public TopologyViewHolder(View view) {
            super(view);
            mView = view;
            mBackground = (ImageView) view.findViewById(R.id.background);
            mTitle = (TextView) view.findViewById(R.id.title);
            mContent = (TextView) view.findViewById(R.id.content);
            mImage = (CircleImageView) view.findViewById(R.id.image);
        }

        public void bind(final TopologyFieldItem item) {
            Context context = mInflater.getContext();
            mItem = item;
            Glide.with(context).load(item.getBackgroundURI()).into(mBackground);
            mTitle.setText(item.getTitle());
            mContent.setText(item.getContent());
            Glide.with(context).load(item.getImageURI()).into(mImage);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 唤醒活动的回调接口 (activity, 如果 fragment 被绑定) 有一个选项被选择。
                    mListener.onTopologyItemClick(item);
                }
            });
        }
    }
}
