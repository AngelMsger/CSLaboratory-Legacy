package com.angelmsger.cslaboratory.forum;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angelmsger.cslaboratory.forum.topic.ForumTopicItem;
import com.angelmsger.cslaboratory.source.DataSource;
import com.angelmsger.cslaboratory.source.UnrealDataSource;
import com.angelmsger.cslaboratory.R;
import com.yalantis.phoenix.PullToRefreshView;

/**
 * 一个表现 Items 列表的 fragment。
 * 包含这个 fragment 的 Activities 必须实现 {@link OnListFragmentInteractionListener}
 * 接口。
 */
public class ForumFragment extends Fragment implements PullToRefreshView.OnRefreshListener {

    // TODO: 自定义参数名称。
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: 自定义参数。
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private PullToRefreshView mPullToRefreshView;
    private RecyclerView mRecyclerView;
    private ForumRecyclerViewAdapter mRecyclerViewAdapter;

    private final DataSource mDataSource;
    private ForumContent mForumContent;

    private Handler mHandler;
    private AsynLoadData mLoadDataTask;

    private final int mRequestLimit = 16;

    /**
     * 为 fragment manager 预留的强制的空构造器以实例化
     * fragment (e.g. upon screen orientation changes)。
     */
    public ForumFragment() {
        mHandler = new Handler();
        mDataSource = new UnrealDataSource();
        mForumContent = mDataSource.getForumContent(0, mRequestLimit);
    }

    // TODO: 自定义参数初始化。
    @SuppressWarnings("unused")
    public static ForumFragment newInstance(int columnCount) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPullToRefreshView = (PullToRefreshView) inflater.
                inflate(R.layout.fragment_forum, container, false);

        // 设置下拉刷新监听器。
        mPullToRefreshView.setOnRefreshListener(this);

        Runnable createView = new Runnable() {
            @Override
            public void run() {
                // 在这里将数据内容加载到Fragment上
                mRecyclerView = (RecyclerView) mPullToRefreshView.findViewById(R.id.list);

                // 设置适配器。
                mRecyclerViewAdapter
                        = new ForumRecyclerViewAdapter(mForumContent.ITEMS, inflater, mListener);
                mRecyclerView.setAdapter(mRecyclerViewAdapter);

                Context context = mPullToRefreshView.getContext();
                if (mColumnCount <= 1) {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }

                // 为 RecyclerView 增加滚动事件监听器以实现滚动至底部时自动加载更多
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        mListener.onForumListScroll();
                        if (mLoadDataTask == null) {
                            if (recyclerView.computeVerticalScrollExtent()
                                    + recyclerView.computeVerticalScrollOffset()
                                    >= recyclerView.computeVerticalScrollRange()){
                                mLoadDataTask =
                                        new AsynLoadData(mRecyclerViewAdapter.getNextRequestOffset());
                                mLoadDataTask.execute();
                            }
                        }
                        else {
                            mLoadDataTask.cancel(true);
                        }
                    }
                });
            }
        };

        mHandler.postDelayed(createView, 300);
        return mPullToRefreshView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        mLoadDataTask = new AsynLoadData(0);
        mLoadDataTask.execute();
    }

    /**
     * 包含此 fragment 的 activity 必须实现此接口来允许一此 fragment 和此 activity 及此 activity 中
     * 包含的其他 fragment 进行通信。
     * 查看 Android 训练课程 <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> 以获取更多信息。
     */
    public interface OnListFragmentInteractionListener {
        // TODO: 更新参数类型及名称。
        void onForumListScroll();
        void onForumListUpdate(final int updateCount);
        void onForumItemClick(ForumTopicItem forumTopicItem);
    }

    /**
     * 异步加载数据到列表，需要提供加载分页页号，加载完成后更新界面。
     */
    private class AsynLoadData extends AsyncTask<Void, Void, Integer> {
        private final int mRequestOffset;

        public AsynLoadData(int requestOffset) {
            this.mRequestOffset = requestOffset;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            // 根据 offset 和 limit 请求数据，根据 offset 是否为 0 决定是否在头部插入数据
            return mForumContent.update(mDataSource
                    .getForumContent(mRequestOffset, mRequestLimit), mRequestOffset != 0);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mLoadDataTask = null;

            // 唤醒 Adapter 提示数据集发生变动
            if (integer != 0) {
                mListener.onForumListUpdate(integer);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }

            mPullToRefreshView.setRefreshing(false);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mLoadDataTask = null;

            mPullToRefreshView.setRefreshing(false);
        }
    }
}
