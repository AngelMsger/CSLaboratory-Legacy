package com.angelmsger.cslaboratory.source;

import android.content.Context;

import com.angelmsger.cslaboratory.CSLaboratory;
import com.angelmsger.cslaboratory.course.subclass.CourseCategoryItem;
import com.angelmsger.cslaboratory.course.CourseContent;
import com.angelmsger.cslaboratory.course.course.CourseCourseItem;
import com.angelmsger.cslaboratory.daily.article.DailyArticleItem;
import com.angelmsger.cslaboratory.daily.DailyContent;
import com.angelmsger.cslaboratory.forum.ForumContent;
import com.angelmsger.cslaboratory.login.Login;
import com.angelmsger.cslaboratory.quality.QualityContent;
import com.angelmsger.cslaboratory.topology.TopologyContent;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 实现数据源接口，数据源为从远程服务器加载
 */

public class ServerDataSource implements DataSource {
    private final String mBaseRequestURI = "http://10.0.2.2:9090";

    private Context mContext;
    private OkHttpClient mApiClient;

    private Gson mGson = new Gson();

    public ServerDataSource(Context context) {
        mContext = context;
        mApiClient = ((CSLaboratory)mContext.getApplicationContext()).mApiClient;
    }

    @Override
    public Login.Response attemptLogin(Login.Request request) {
        return null;
    }

    @Override
    public CourseContent getCourseContent(final int offset, final int limit) {
        CourseContent courseContent = null;
        String categoriesRequestURI = mBaseRequestURI + "/course/categories";
        String coursesRequestURI = mBaseRequestURI + "/course/courses?offset=" + offset
                + "&limit=" + limit;
        Request categoriesRequest = new Request.Builder().url(categoriesRequestURI).build();
        Request coursesRequest = new Request.Builder().url(coursesRequestURI).build();
        try {
            Response categoriesResponse = mApiClient.newCall(categoriesRequest).execute();
            Response coursesResponse = mApiClient.newCall(coursesRequest).execute();
            if (categoriesResponse.code() == 200 && coursesResponse.code() == 200) {
                courseContent = new CourseContent();
                courseContent.CATEGORY_ITEMS.addAll(Arrays.asList(
                   mGson.fromJson(categoriesResponse.body().string(),
                           CourseCategoryItem[].class)
                ));
                courseContent.COURSE_ITEMS.addAll(Arrays.asList(
                   mGson.fromJson(coursesResponse.body().string(), CourseCourseItem[].class)
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseContent;
    }

    @Override
    public DailyContent getDailyContent(final int offset, final int limit) {
        DailyContent dailyContent = null;
        String requestURI = mBaseRequestURI + "/daily/articles?offset=" + offset
                + "&limit=" + limit;
        Request request = new Request.Builder().url(requestURI).build();
        try {
            Response response = mApiClient.newCall(request).execute();
            if (response.code() == 200) {
                dailyContent = new DailyContent();
                dailyContent.ITEMS.addAll(Arrays.asList(
                        mGson.fromJson(response.body().string(), DailyArticleItem[].class)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dailyContent;
    }

    @Override
    public ForumContent getForumContent(final int offset, final int limit) {
        return null;
    }
    @Override
    public QualityContent getQualityContent(final int offset, final int limit) {
        return null;
    }

    @Override
    public TopologyContent getTopologyContent(final int offset, final int limit) {
        return null;
    }
}
