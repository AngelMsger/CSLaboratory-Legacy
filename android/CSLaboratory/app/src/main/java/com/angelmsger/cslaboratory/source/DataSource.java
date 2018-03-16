package com.angelmsger.cslaboratory.source;

/**
 * 数据源接口，实现此接口的类都能够作为其他类的数据来源
 */

import com.angelmsger.cslaboratory.course.CourseContent;
import com.angelmsger.cslaboratory.daily.DailyContent;
import com.angelmsger.cslaboratory.forum.ForumContent;
import com.angelmsger.cslaboratory.login.Login;
import com.angelmsger.cslaboratory.quality.QualityContent;
import com.angelmsger.cslaboratory.topology.TopologyContent;

public interface DataSource {
    Login.Response attemptLogin(Login.Request request);
    CourseContent getCourseContent(final int offset, final int limit);
    DailyContent getDailyContent(final int offset, final int limit);
    ForumContent getForumContent(final int offset, final int limit);
    QualityContent getQualityContent(final int offset, final int limit);
    TopologyContent getTopologyContent(final int offset, final int limit);
}
