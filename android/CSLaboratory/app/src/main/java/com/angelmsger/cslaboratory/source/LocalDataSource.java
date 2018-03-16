package com.angelmsger.cslaboratory.source;

import com.angelmsger.cslaboratory.course.CourseContent;
import com.angelmsger.cslaboratory.daily.DailyContent;
import com.angelmsger.cslaboratory.forum.ForumContent;
import com.angelmsger.cslaboratory.login.Login;
import com.angelmsger.cslaboratory.quality.QualityContent;
import com.angelmsger.cslaboratory.topology.TopologyContent;

/**
 * 实现数据源接口，数据源为本地缓存数据库
 */

public class LocalDataSource implements DataSource {
    @Override
    public Login.Response attemptLogin(Login.Request request) {
        return null;
    }

    @Override
    public CourseContent getCourseContent(final int offset, final int limit) {
        return null;
    }

    @Override
    public DailyContent getDailyContent(final int offset, final int limit) {
        return null;
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
