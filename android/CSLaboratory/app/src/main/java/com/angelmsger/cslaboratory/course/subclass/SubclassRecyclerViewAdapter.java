package com.angelmsger.cslaboratory.course.subclass;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angelmsger.cslaboratory.R;
import com.angelmsger.cslaboratory.course.CourseFragment;
import com.bumptech.glide.Glide;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} 可以展示一些 {@link CourseSubclassItem}
 * 并且调用指定的 {@link CourseFragment.OnListFragmentInteractionListener}.
 * TODO: 替换为自定义代码段。
 */
public class SubclassRecyclerViewAdapter
        extends RecyclerView.Adapter<SubclassRecyclerViewAdapter.CourseClassViewHolder> {

    private final List<CourseSubclassItem> mCourseSubclassItems;
    private final LayoutInflater mInflater;
    private final CourseFragment.OnListFragmentInteractionListener mListener;

    public SubclassRecyclerViewAdapter(List<CourseSubclassItem> courseSubclassItems,
                                       LayoutInflater inflater,
                                       CourseFragment.OnListFragmentInteractionListener listener) {
        mCourseSubclassItems = courseSubclassItems;
        mInflater = inflater;
        mListener = listener;
    }

    @Override
    public CourseClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_course_category_item, parent, false);
        return new CourseClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseClassViewHolder holder, int position) {
        holder.bind(mCourseSubclassItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mCourseSubclassItems.size();
    }

    public class CourseClassViewHolder extends ViewHolder {
        public final View mView;
        public final CircleImageView mImage;
        public final TextView mTitle;
        public CourseSubclassItem mItem;

        public CourseClassViewHolder(View view) {
            super(view);
            mView = view;
            mImage = (CircleImageView) view.findViewById(R.id.image);
            mTitle = (TextView) view.findViewById(R.id.title);
        }

        public void bind(final CourseSubclassItem item) {
            mItem = item;
            Glide.with(mInflater.getContext()).load(item.getImageURI()).into(mImage);
            mTitle.setText(item.getTitle());
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 唤醒活动的回调接口 (activity, 如果 fragment 被绑定) 有一个选项被选择。
                    mListener.onCourseCategoryItemClick(item);
                }
            });
        }
    }
}
