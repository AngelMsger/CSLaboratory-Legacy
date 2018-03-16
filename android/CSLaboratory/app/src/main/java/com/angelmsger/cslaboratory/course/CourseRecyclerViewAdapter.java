package com.angelmsger.cslaboratory.course;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelmsger.cslaboratory.course.CourseFragment.OnListFragmentInteractionListener;
import com.angelmsger.cslaboratory.R;
import com.angelmsger.cslaboratory.course.subclass.CourseCategoryItem;
import com.angelmsger.cslaboratory.course.course.CourseCourseItem;
import com.angelmsger.cslaboratory.course.subclass.SubclassRecyclerViewAdapter;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} 可以展示一些 {@link CourseCourseItem}
 * 并且调用指定的 {@link OnListFragmentInteractionListener}.
 * TODO: 替换为自定义代码段。
 */
public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<CourseCategoryItem> mCourseCategoryItems;
    private final List<CourseCourseItem> mCourseCourseItems;
    private final LayoutInflater mInflater;
    private final OnListFragmentInteractionListener mListener;

    public CourseRecyclerViewAdapter(List<CourseCategoryItem> courseCourseCategoryItems,
                                     List<CourseCourseItem> courseCourseItems,
                                     LayoutInflater inflater,
                                     OnListFragmentInteractionListener listener) {
        mCourseCategoryItems = courseCourseCategoryItems;
        mCourseCourseItems = courseCourseItems;
        mInflater = inflater;
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mCourseCategoryItems.size()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_course_category, parent, false);
            return new ClassViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_course_item, parent, false);
            return new CourseViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        if (viewType == 0) {
            ((ClassViewHolder)holder).bind(mCourseCategoryItems.get(position));
        }
        else {
            ((CourseViewHolder)holder).bind(mCourseCourseItems.get(position - mCourseCategoryItems.size()));
        }
    }

    @Override
    public int getItemCount() {
        return mCourseCategoryItems.size() + mCourseCourseItems.size();
    }

    // 根据当前持有数据量返回下一次请求开始点 offset
    public int getNextRequestOffset() {
        return mCourseCourseItems.size();
    }

    public class ClassViewHolder extends ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final RecyclerView mList;
        public CourseCategoryItem mItem;

        public ClassViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.title);
            mList = (RecyclerView) view.findViewById(R.id.list);
        }

        public void bind(CourseCategoryItem courseCourseCategoryItem) {
            mItem = courseCourseCategoryItem;
            mTitle.setText(courseCourseCategoryItem.getTitle());
            mList.setLayoutManager(new GridLayoutManager(mInflater.getContext(), 3));
            mList.setAdapter(
                    new SubclassRecyclerViewAdapter(
                            courseCourseCategoryItem.getSubclasses(), mInflater, mListener));
        }
    }

    public class CourseViewHolder extends ViewHolder {
        public final View mView;
        public final ImageView mImage;
        public final TextView mTitle;
        public final TextView mLearnerCount;
        public CourseCourseItem mItem;

        public CourseViewHolder(View view) {
            super(view);
            mView = view;
            mImage = (ImageView) view.findViewById(R.id.image);
            mTitle = (TextView) view.findViewById(R.id.title);
            mLearnerCount = (TextView) view.findViewById(R.id.learner_count);
        }

        public void bind(final CourseCourseItem courseCourseItem) {
            final Context context = mInflater.getContext();
            mItem = courseCourseItem;
            Glide.with(context).load(courseCourseItem.getImageURI()).into(mImage);
            mTitle.setText(courseCourseItem.getTitle());
            mLearnerCount.setText(
                    String.format(context.getString(R.string.course_learner_count),
                            courseCourseItem.getLearnNum()));
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 唤醒活动的回调接口 (activity, 如果 fragment 被绑定) 有一个选项被选择。
                    mListener.onCourseItemClick(courseCourseItem);
                }
            });
        }
    }
}
