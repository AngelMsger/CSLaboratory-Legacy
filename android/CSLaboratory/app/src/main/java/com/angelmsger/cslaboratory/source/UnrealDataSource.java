package com.angelmsger.cslaboratory.source;

/**
 * 实现数据源接口，数据源为用于在开发过程中进行测试的虚假数据
 */

import com.angelmsger.cslaboratory.course.subclass.CourseCategoryItem;
import com.angelmsger.cslaboratory.course.course.CourseCourseItem;
import com.angelmsger.cslaboratory.course.subclass.CourseSubclassItem;
import com.angelmsger.cslaboratory.course.subclass.SubclassContent;
import com.angelmsger.cslaboratory.course.CourseContent;
import com.angelmsger.cslaboratory.daily.DailyContent;
import com.angelmsger.cslaboratory.forum.ForumContent;
import com.angelmsger.cslaboratory.forum.topic.ForumTopicItem;
import com.angelmsger.cslaboratory.login.Login;
import com.angelmsger.cslaboratory.login.User;
import com.angelmsger.cslaboratory.quality.article.QualityArticleItem;
import com.angelmsger.cslaboratory.quality.category.QualityCategoryItem;
import com.angelmsger.cslaboratory.quality.ad.QualityADItem;
import com.angelmsger.cslaboratory.quality.QualityContent;
import com.angelmsger.cslaboratory.topology.TopologyContent;
import com.angelmsger.cslaboratory.topology.field.TopologyFieldItem;

public class UnrealDataSource implements DataSource {
    private int[] mRequestsCount = { 0, 0, 0, 0, 0 };

    @Override
    public Login.Response attemptLogin(Login.Request request) {
        User.Base base = new User.Base();
        base.setId("");
        base.setAvatarURI(
                "https://q1.qlogo.cn/g?b=qq&k=47NdwRStQ1zq8mPiaCicibJ0w&s=140&t=1475895910");
        base.setImageURI(
                "http://e.hiphotos.baidu.com/zhidao/pic/item/dc54564e9258d109679e4c99d058ccbf6c814d98.jpg");
        base.setNickname("敲键盘的猫");
        User.Extra extra = new User.Extra();
        extra.setFavTag(3);
        extra.setFavTopic(1);
        extra.setFavUser(1);
        extra.setFollowerCount(1);
        extra.setGoodTopicCount(3);
        extra.setTopicCount(3);
        extra.setReplyCount(1);
        extra.setSex(0);
        User user = new User(base, extra);
        return new Login.Response(200, user);
    }

    @Override
    public CourseContent getCourseContent(final int offset, final int limit) {
        CourseContent courseContent =  new CourseContent();

        // 分类组信息
        for (int i = 0; i < 2; i++) {
            SubclassContent subclassContent = new SubclassContent();
            for (int j = 0; j < 8; j++) {
                subclassContent.ITEMS.add(new CourseSubclassItem(0L,
                        "http://img.knowledge.csdn.net/upload/base/1466132253931_931.jpg", "Title"));
            }
            CourseCategoryItem courseCategoryItem = new CourseCategoryItem(0L, "Class" + i, subclassContent.ITEMS);
            courseContent.CATEGORY_ITEMS.add(courseCategoryItem);
        }

        if (offset == 0) {
            // 课程信息
            if (mRequestsCount[3]++ == 0) {
                for (int i = 0; i < limit; i++) {
                    courseContent.COURSE_ITEMS.add(new CourseCourseItem(100L,
                            "http://szimg.mukewang.com/576376440001766205400300-360-202.jpg", "Title" + i, "", 0));
                }
            }
            else {
                for (int i = 0; i < limit; i++) {
                    courseContent.COURSE_ITEMS.add(new CourseCourseItem(116L - i,
                            "http://img.mukewang.com/5707655b00016b1d06000338-228-128.jpg", "Title" + i, "", 0));
                }
            }
        }
        else {
            for (int i = 0; i < limit; i++) {
                courseContent.COURSE_ITEMS.add(new CourseCourseItem(16L - i,
                        "http://img.mukewang.com/562052b70001e9c106000338-228-128.jpg", "Title" + i, "", 0));
            }
        }

        return courseContent;
    }

    @Override
    public DailyContent getDailyContent(final int offset, final int limit) {
        return null;
    }

    @Override
    public ForumContent getForumContent(final int offset, final int limit) {
        ForumContent forumContent = new ForumContent();
        if (offset == 0) {
            if (mRequestsCount[1]++ == 0) {
                for (int i = 0; i < limit; i++) {
                    forumContent.ITEMS.add(new ForumTopicItem(100L,
                            "http://cdn.dota2.com/steamcommunity/public/images/avatars/https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/64/640335a196ffcc972c3fd228c7ef593a8dc0e5a7_medium.jpg",
                            "Nickname" + i, "Title" + i, "Content" + i));
                }
            }
            else {
                for (int i = 0; i < limit; i++) {
                    forumContent.ITEMS.add(new ForumTopicItem(116L - i,
                            "http://img.mukewang.com/user/56518a660001b25601000100-40-40.jpg",
                            "Nickname" + i, "Title" + i, "Content" + i));
                }
            }
        }
        else {
            for (int i = 0; i < limit; i++) {
                forumContent.ITEMS.add(new ForumTopicItem(16L - i,
                        "http://img.mukewang.com/user/56518a660001b25601000100-40-40.jpg",
                        "Nickname" + i, "Title" + i, "Content" + i));
            }
        }


        return forumContent;
    }

    @Override
    public QualityContent getQualityContent(final int offset, final int limit) {
        QualityContent qualityContent = new QualityContent();
        if (offset == 0) {
            if (mRequestsCount[2]++ == 0) {
                qualityContent.AD_ITEMS.add(new QualityADItem(104L, "", "https://pic2.zhimg.com/v2-7b34cc40df565cafc4551882c96210a9_b.png", ""));
                qualityContent.AD_ITEMS.add(new QualityADItem(103L, "", "https://pic2.zhimg.com/v2-26a3004619bd8c1eebe187faa6648075_b.png", ""));
                qualityContent.AD_ITEMS.add(new QualityADItem(102L, "", "https://pic1.zhimg.com/v2-c241b0354b1334b5e0ac460b85cd1da4_b.png", ""));
                qualityContent.AD_ITEMS.add(new QualityADItem(101L, "", "https://pic4.zhimg.com/b343e0dd8532fd90a03296af02cedc5b_b.png", ""));
                qualityContent.AD_ITEMS.add(new QualityADItem(100L, "", "https://pic2.zhimg.com/80e53053d4820dae0e5bfc4f607454d5_b.png", ""));
                for (int i = 1; i <= limit; i++) {
                    qualityContent.CLASS_ITEMS.add(new QualityCategoryItem(0L, Integer.toString(i),
                            "http://imgsrc.baidu.com/forum/pic/item/9eae8b58d109b3de7da534f6ccbf6c81810a4c73.jpg"));
                    qualityContent.QUALITY_ITEMS.add(new QualityArticleItem(100L - i, "Title" + i, "来自话题", "Content" + i,
                            "https://pic4.zhimg.com/371c6c8b5bf55b8d12a491ab4f688447_b.png", "", 0, 0));
                }
            }
            else {
                qualityContent.AD_ITEMS.add(new QualityADItem(106L, "", "https://pic4.zhimg.com/v2-97642da495484a13b3caffe4d4c1251b_b.png", ""));
                qualityContent.AD_ITEMS.add(new QualityADItem(105L, "", "https://pic1.zhimg.com/v2-ec7dad7bc04e0690625ef40018dbf5f4_b.png", ""));
                qualityContent.AD_ITEMS.add(new QualityADItem(104L, "", "https://pic1.zhimg.com/v2-c241b0354b1334b5e0ac460b85cd1da4_b.png", ""));
                qualityContent.AD_ITEMS.add(new QualityADItem(103L, "", "https://pic4.zhimg.com/b343e0dd8532fd90a03296af02cedc5b_b.png", ""));
                qualityContent.AD_ITEMS.add(new QualityADItem(102L, "", "https://pic2.zhimg.com/80e53053d4820dae0e5bfc4f607454d5_b.png", ""));
                for (int i = 1; i <= limit; i++) {
                    qualityContent.CLASS_ITEMS.add(new QualityCategoryItem(0L, Integer.toString(i),
                            "http://imgsrc.baidu.com/forum/pic/item/9eae8b58d109b3de7da534f6ccbf6c81810a4c73.jpg"));
                    qualityContent.QUALITY_ITEMS.add(new QualityArticleItem((long) (mRequestsCount[2] * limit - i), "Title" + i, "来自话题", "Content" + i,
                            "https://pic1.zhimg.com/v2-bb51b98557838b66ff3b34845bd48300_b.png", "", 0, 0));
                }
            }
        }
        else {
            qualityContent.AD_ITEMS.add(new QualityADItem(106L, "", "https://pic4.zhimg.com/v2-97642da495484a13b3caffe4d4c1251b_b.png", ""));
            qualityContent.AD_ITEMS.add(new QualityADItem(105L, "", "https://pic1.zhimg.com/v2-ec7dad7bc04e0690625ef40018dbf5f4_b.png", ""));
            qualityContent.AD_ITEMS.add(new QualityADItem(104L, "", "https://pic1.zhimg.com/v2-c241b0354b1334b5e0ac460b85cd1da4_b.png", ""));
            qualityContent.AD_ITEMS.add(new QualityADItem(103L, "", "https://pic4.zhimg.com/b343e0dd8532fd90a03296af02cedc5b_b.png", ""));
            qualityContent.AD_ITEMS.add(new QualityADItem(102L, "", "https://pic2.zhimg.com/80e53053d4820dae0e5bfc4f607454d5_b.png", ""));
            for (int i = 1; i <= limit; i++) {
                qualityContent.CLASS_ITEMS.add(new QualityCategoryItem(0L, Integer.toString(i),
                        "http://imgsrc.baidu.com/forum/pic/item/9eae8b58d109b3de7da534f6ccbf6c81810a4c73.jpg"));
                qualityContent.QUALITY_ITEMS.add(new QualityArticleItem((long) (limit - i), "Title" + i, "来自话题", "Content" + i,
                        "https://pic4.zhimg.com/v2-157d1877b8ab331d01dc92e2145a778f_b.jpg", "", 0, 0));
            }
        }
        return qualityContent;
    }

    @Override
    public TopologyContent getTopologyContent(final int offset, final int limit) {
        TopologyContent topologyContent = new TopologyContent();
        if (mRequestsCount[4]++ == 0) {
            TopologyFieldItem topologyFieldItem = new TopologyFieldItem(100L,
                    "http://img.knowledge.csdn.net/upload/base/1466674256106_106.jpg",
                    "C++",
                    "C++不仅拥有计算机高效运行的实用性特征，同时还致力于提高大规模程序的编程质量与程序设计语言的问题描述能力。",
                    "http://img.knowledge.csdn.net/upload/base/1466674255874_874.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(99L,
                    "http://img.knowledge.csdn.net/upload/base/1473663968868_868.jpg",
                    "React Native",
                    "React Native结合了Web应用和Native应用的优势，可以使用JavaScript来开发iOS和Android原生应用。",
                    "http://img.knowledge.csdn.net/upload/base/1473751990490_490.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(98L,
                    "http://img.knowledge.csdn.net/upload/base/1478676230624_624.jpg",
                    "MongoDB",
                    "MongoDB由C++语言编写。旨在为WEB应用提供可扩展的高性能数据存储解决方案。",
                    "http://img.knowledge.csdn.net/upload/base/1478676229520_520.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(97L,
                    "http://img.knowledge.csdn.net/upload/base/1479972981646_646.jpg",
                    "人工智能",
                    "人工智能是研究、开发用于模拟、延伸和扩展人的智能的理论、方法、技术及应用系统的一门新的技术科学。",
                    "http://img.knowledge.csdn.net/upload/base/1479972981201_201.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(96L,
                    "http://img.knowledge.csdn.net/upload/base/1476175342919_919.jpg",
                    "区块链",
                    "区块链是分布式数据存储、点对点传输、共识机制、加密算法等计算机技术的 新型应用模式。",
                    "http://img.knowledge.csdn.net/upload/base/1476175342721_721.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(95L,
                    "http://img.knowledge.csdn.net/upload/base/1474966029754_754.jpg",
                    "深度学习",
                    "深度学习的概念源于人工神经网络的研究。含多隐层的多层感知器就是一种深度学习结构。深度学习通过组合低层特征形成更加抽象的高层表示属性类别或特征，以发现数据的分布式特征表示。",
                    "http://img.knowledge.csdn.net/upload/base/1474966029376_376.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(94L,
                    "http://img.knowledge.csdn.net/upload/base/1463125919684_684.jpg",
                    "Bluemix",
                    "Bluemix，2015年年中，IBM推出了名为Bluemix的云计算平台。这一“平台即服务”的PaaS云将帮助开发者更快的进行应用开发和部署。",
                    "http://img.knowledge.csdn.net/upload/base/1463125918859_859.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
        }
        else {
            TopologyFieldItem topologyFieldItem = new TopologyFieldItem(102L,
                    "http://img.knowledge.csdn.net/upload/base/1471395509081_81.jpg",
                    "Go",
                    "Go语言专门针对多处理器系统应用程序的编程进行了优化，使用Go编译的程序可以媲美C或C++代码的速度，而且更加安全、支持并行进程。",
                    "http://img.knowledge.csdn.net/upload/base/1471395508721_721.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(101L,
                    "http://img.knowledge.csdn.net/upload/base/1467269388411_411.jpg",
                    "OpenCV",
                    "OpenCV是一个基于BSD许可（开源）发行的跨平台计算机视觉库，可以运行在Linux、Windows、Android和Mac OS操作系统上。",
                    "http://img.knowledge.csdn.net/upload/base/1467269388326_326.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(93L,
                    "http://img.knowledge.csdn.net/upload/base/1469086540456_456.jpg",
                    "Scala",
                    "Scala是一门多范式的编程语言，一种类似java的编程语言，设计初衷是实现可伸缩的语言 、并集成面向对象编程和函数式编程的各种特性。",
                    "http://img.knowledge.csdn.net/upload/base/1469086540236_236.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(92L,
                    "http://img.knowledge.csdn.net/upload/base/1452498182501_501.jpg",
                    "Rust",
                    "Rust是Mozilla开发的注重安全、性能和并发性的编程语言。",
                    "http://img.knowledge.csdn.net/upload/base/1452498182335_335.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(91L,
                    "http://img.knowledge.csdn.net/upload/base/1461035533624_624.jpg",
                    "算法与数据结构",
                    "数据结构是计算机存储、组织数据的方式。",
                    "http://img.knowledge.csdn.net/upload/base/1461035533512_512.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(90L,
                    "http://img.knowledge.csdn.net/upload/base/1461807588401_401.jpg",
                    "虚拟现实（VR）",
                    "虚拟现实（VR，Virtual Reality）是一种可创建和体验虚拟世界的计算机系统。它综合利用计算机图形系统和各种现实及控制等接口设备，在计算机上生成的、可交互的三维环境中提供沉浸感觉的技术。",
                    "http://img.knowledge.csdn.net/upload/base/1461807587796_796.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
            topologyFieldItem = new TopologyFieldItem(89L,
                    "http://img.knowledge.csdn.net/upload/base/1452500466656_656.jpg",
                    "Docker",
                    "Docker是一个开源、可以将任何应用包装在\"LXC容器”中运行的工具。如果说VMware、KVM包装的虚拟机，那该工具包装的则是应用。它是一个实至名归的PaaS。",
                    "http://img.knowledge.csdn.net/upload/base/1452500466484_484.jpg");
            topologyContent.ITEMS.add(topologyFieldItem);
        }
        return topologyContent;
    }
}
