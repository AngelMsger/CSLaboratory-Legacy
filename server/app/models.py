# coding: utf-8
from . import db

Base = db.Model
metadata = db.metadata


class CarbonApp(Base):
    __tablename__ = 'carbon_app'

    ID = db.Column(db.Integer, primary_key=True)
    AppKey = db.Column(db.String(20), nullable=False, index=True)
    AppName = db.Column(db.String(32), nullable=False)
    AppSecret = db.Column(db.String(40), nullable=False)
    Time = db.Column(db.Integer, nullable=False)


class CarbonAppUser(Base):
    __tablename__ = 'carbon_app_users'
    __table_args__ = (
        db.Index('db.Index', 'AppID', 'OpenID'),
    )

    ID = db.Column(db.Integer, primary_key=True)
    AppID = db.Column(db.Integer, nullable=False)
    OpenID = db.Column(db.String(64), nullable=False)
    AppUserName = db.Column(db.String(50))
    UserID = db.Column(db.Integer, nullable=False, index=True)
    Time = db.Column(db.Integer, nullable=False)


class CarbonBlog(Base):
    __tablename__ = 'carbon_blogs'
    __table_args__ = (
        db.Index('Blogs', 'UserName', 'ParentID', 'DateCreated'),
    )

    ID = db.Column(db.Integer, primary_key=True)
    ParentID = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    Content = db.Column(db.String, nullable=False)
    UserName = db.Column(db.String(50), nullable=False)
    Category = db.Column(db.String(255))
    Subject = db.Column(db.String(255))
    BlogDate = db.Column(db.String(50))
    TotalReplies = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    DateCreated = db.Column(db.Integer, nullable=False)
    db.DateNew = db.Column(db.Integer)


class CarbonBlogsetting(Base):
    __tablename__ = 'carbon_blogsettings'

    ID = db.Column(db.Integer, primary_key=True)
    BlogTitle = db.Column(db.String(255))
    BlogTagline = db.Column(db.String(255))
    UserName = db.Column(db.String(50), nullable=False, index=True)
    BlogBgcolor = db.Column(db.String(255))
    BlogPermissions = db.Column(db.String(50))
    BlogBackground = db.Column(db.String(255))
    BlogAudio = db.Column(db.String(255))
    BlogComments = db.Column(db.Integer, nullable=False, server_default=db.text("'1'"))
    NumBlogs = db.Column(db.Integer, nullable=False, server_default=db.text("'5'"))
    UserSkinID = db.Column(db.Integer, server_default=db.text("'1'"))
    BlogCount = db.Column(db.Integer, server_default=db.text("'0'"))
    BlogReplies = db.Column(db.Integer, server_default=db.text("'0'"))
    BlogViews = db.Column(db.Integer, server_default=db.text("'0'"))
    BlogScore = db.Column(db.Integer, server_default=db.text("'0'"))
    MusicUrl = db.Column(db.String)
    MusicName = db.Column(db.String)


class CarbonConfig(Base):
    __tablename__ = 'carbon_config'

    ConfigName = db.Column(db.String(50), primary_key=True, server_default=db.text("''"))
    ConfigValue = db.Column(db.Text, nullable=False)


class CarbonDict(Base):
    __tablename__ = 'carbon_dict'

    ID = db.Column(db.Integer, primary_key=True)
    Title = db.Column(db.String(512), nullable=False, index=True)
    Abstract = db.Column(db.String, nullable=False)


class CarbonFavorite(Base):
    __tablename__ = 'carbon_favorites'
    __table_args__ = (
        db.Index('IsFavorite', 'UserID', 'Type', 'FavoriteID', unique=True),
        db.Index('UsersFavorites', 'UserID', 'Type', 'DateCreated')
    )

    ID = db.Column(db.Integer, primary_key=True)
    UserID = db.Column(db.Integer, nullable=False)
    Category = db.Column(db.String(255))
    Title = db.Column(db.String(255))
    Type = db.Column(db.Integer)
    FavoriteID = db.Column(db.Integer, nullable=False)
    DateCreated = db.Column(db.Integer, nullable=False)
    Description = db.Column(db.String(255))


class CarbonLink(Base):
    __tablename__ = 'carbon_link'

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(255))
    URL = db.Column(db.String(255))
    Logo = db.Column(db.String(255))
    Intro = db.Column(db.String(255))
    Review = db.Column(db.Integer, server_default=db.text("'0'"))
    TopLink = db.Column(db.Integer, server_default=db.text("'0'"))


class CarbonLog(Base):
    __tablename__ = 'carbon_log'

    ID = db.Column(db.Integer, primary_key=True)
    UserName = db.Column(db.String)
    IPAddress = db.Column(db.String)
    UserAgent = db.Column(db.String)
    DateCreated = db.Column(db.Integer, nullable=False)
    HttpVerb = db.Column(db.String(50))
    PathAndQuery = db.Column(db.String)
    Referrer = db.Column(db.String(255))
    ErrDescription = db.Column(db.String(255))
    POSTData = db.Column(db.String)
    Notes = db.Column(db.String)


class CarbonMessage(Base):
    __tablename__ = 'carbon_messages'

    ID = db.Column(db.Integer, primary_key=True)
    UserID = db.Column(db.Integer, nullable=False)
    UserName = db.Column(db.String(50), nullable=False, index=True)
    Incept = db.Column(db.String(50), nullable=False)
    Content = db.Column(db.String, nullable=False)
    DateCreated = db.Column(db.Integer, nullable=False)
    ParentID = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    IsPublish = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))


class CarbonNotification(Base):
    __tablename__ = 'carbon_notifications'
    __table_args__ = (
        db.Index('UserID', 'UserID', 'Time'),
    )

    ID = db.Column(db.Integer, primary_key=True)
    UserID = db.Column(db.Integer, nullable=False)
    UserName = db.Column(db.String(50), nullable=False)
    Type = db.Column(db.Integer, nullable=False)
    TopicID = db.Column(db.Integer, nullable=False, index=True)
    PostID = db.Column(db.Integer, nullable=False, index=True)
    Time = db.Column(db.Integer, nullable=False)
    IsRead = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))


class CarbonPicture(Base):
    __tablename__ = 'carbon_pictures'

    ID = db.Column(db.Integer, primary_key=True)
    PicUrl = db.Column(db.String(255))
    UserName = db.Column(db.String(255))
    PicReadme = db.Column(db.String(255))
    TopicID = db.Column(db.Integer, server_default=db.text("'0'"))
    AddTime = db.Column(db.Integer, nullable=False)


t_carbon_postrating = db.Table(
    'carbon_postrating', metadata,
    db.Column('UserName', db.String(50), nullable=False),
    db.Column('TopicID', db.Integer, nullable=False, index=True, server_default=db.text("'0'")),
    db.Column('Rating', db.Integer, nullable=False, server_default=db.text("'0'")),
    db.Column('DateCreated', db.Integer, nullable=False)
)


class CarbonPost(Base):
    __tablename__ = 'carbon_posts'
    __table_args__ = (
        db.Index('TopicID', 'TopicID', 'PostTime', 'IsDel'),
        db.Index('UserPosts', 'UserName', 'IsDel', 'PostTime')
    )

    ID = db.Column(db.Integer, primary_key=True)
    TopicID = db.Column(db.Integer, server_default=db.text("'0'"))
    IsTopic = db.Column(db.Integer, server_default=db.text("'0'"))
    UserID = db.Column(db.Integer, nullable=False)
    UserName = db.Column(db.String(50), nullable=False)
    Subject = db.Column(db.String(255))
    Content = db.Column(db.String)
    PostIP = db.Column(db.String(50))
    PostTime = db.Column(db.Integer, nullable=False)
    IsDel = db.Column(db.Integer, server_default=db.text("'0'"))


t_carbon_posttags = db.Table(
    'carbon_posttags', metadata,
    db.Column('TagID', db.Integer, server_default=db.text("'0'")),
    db.Column('TopicID', db.Integer, index=True, server_default=db.text("'0'")),
    db.Column('PostID', db.Integer, server_default=db.text("'0'")),
    db.Index('Tagsdb.Index', 'TagID', 'TopicID')
)


class CarbonRole(Base):
    __tablename__ = 'carbon_roles'

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(255), nullable=False)
    Description = db.Column(db.String(255))


class CarbonStatistic(Base):
    __tablename__ = 'carbon_statistics'

    DaysUsers = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    DaysPosts = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    DaysTopics = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    TotalUsers = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    TotalPosts = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    TotalTopics = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    DaysDate = db.Column(db.Date, primary_key=True, server_default=db.text("'2014-11-01'"))
    DateCreated = db.Column(db.Integer, nullable=False)


class CarbonTag(Base):
    __tablename__ = 'carbon_tags'
    __table_args__ = (
        db.Index('TotalPosts', 'IsEnabled', 'TotalPosts'),
    )

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(255), nullable=False, index=True)
    Followers = db.Column(db.Integer, server_default=db.text("'0'"))
    Icon = db.Column(db.Integer, server_default=db.text("'0'"))
    Description = db.Column(db.String)
    IsEnabled = db.Column(db.Integer, server_default=db.text("'1'"))
    TotalPosts = db.Column(db.Integer, server_default=db.text("'0'"))
    MostRecentPostTime = db.Column(db.Integer, nullable=False)
    DateCreated = db.Column(db.Integer, nullable=False)


class CarbonTopic(Base):
    __tablename__ = 'carbon_topics'
    __table_args__ = (
        db.Index('LastTime', 'LastTime', 'IsDel'),
        db.Index('UserTopics', 'UserName', 'IsDel', 'LastTime')
    )

    ID = db.Column(db.Integer, primary_key=True)
    Topic = db.Column(db.String(255), nullable=False)
    Tags = db.Column(db.Text)
    UserID = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    UserName = db.Column(db.String(50), nullable=False)
    LastName = db.Column(db.String(50))
    PostTime = db.Column(db.Integer, nullable=False)
    LastTime = db.Column(db.Integer, nullable=False)
    IsGood = db.Column(db.Integer, server_default=db.text("'0'"))
    IsTop = db.Column(db.Integer, server_default=db.text("'0'"))
    IsLocked = db.Column(db.Integer, server_default=db.text("'0'"))
    IsDel = db.Column(db.Integer, server_default=db.text("'0'"))
    IsVote = db.Column(db.Integer, server_default=db.text("'0'"))
    Views = db.Column(db.Integer, server_default=db.text("'0'"))
    Replies = db.Column(db.Integer, server_default=db.text("'0'"))
    Favorites = db.Column(db.Integer, server_default=db.text("'0'"))
    RatingSum = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    TotalRatings = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    LastViewedTime = db.Column(db.Integer, nullable=False)
    PostsTableName = db.Column(db.Integer)
    ThreadStyle = db.Column(db.String(255))
    Lists = db.Column(db.String)
    ListsTime = db.Column(db.Integer, nullable=False)
    Log = db.Column(db.String)


class CarbonUpload(Base):
    __tablename__ = 'carbon_upload'
    __table_args__ = (
        db.Index('Hash', 'FileSize', 'SHA1', 'MD5'),
        db.Index('PostID', 'PostID', 'UserName'),
        db.Index('UsersName', 'UserName', 'Created')
    )

    ID = db.Column(db.Integer, primary_key=True)
    UserName = db.Column(db.String(50), nullable=False)
    FileName = db.Column(db.String(255), nullable=False)
    FileSize = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    FileType = db.Column(db.String(255), nullable=False)
    SHA1 = db.Column(db.String(40))
    MD5 = db.Column(db.String(32))
    FilePath = db.Column(db.String(255), nullable=False)
    Description = db.Column(db.String(255))
    Category = db.Column(db.String(255))
    Class = db.Column(db.String(255))
    PostID = db.Column(db.Integer)
    Created = db.Column(db.Integer, nullable=False)


class CarbonUser(Base):
    __tablename__ = 'carbon_users'

    ID = db.Column(db.Integer, primary_key=True)
    UserName = db.Column(db.String(50), unique=True)
    Salt = db.Column(db.Integer)
    Password = db.Column(db.String(32))
    UserMail = db.Column(db.String(255))
    UserHomepage = db.Column(db.String(255))
    PasswordQuestion = db.Column(db.String(255))
    PasswordAnswer = db.Column(db.String(32))
    UserSex = db.Column(db.Integer)
    NumFavUsers = db.Column(db.Integer, server_default=db.text("'0'"))
    NumFavTags = db.Column(db.Integer, server_default=db.text("'0'"))
    NumFavTopics = db.Column(db.Integer, server_default=db.text("'0'"))
    NewMessage = db.Column(db.Integer, server_default=db.text("'0'"))
    Topics = db.Column(db.Integer, server_default=db.text("'0'"))
    Replies = db.Column(db.Integer, server_default=db.text("'0'"))
    Followers = db.Column(db.Integer, server_default=db.text("'0'"))
    DelTopic = db.Column(db.Integer, server_default=db.text("'0'"))
    GoodTopic = db.Column(db.Integer, server_default=db.text("'0'"))
    UserPhoto = db.Column(db.String(255))
    UserMobile = db.Column(db.String(255))
    UserLastIP = db.Column(db.String(50))
    UserRegTime = db.Column(db.Integer, nullable=False)
    LastLoginTime = db.Column(db.Integer, nullable=False)
    LastPostTime = db.Column(db.Integer)
    BlackLists = db.Column(db.String)
    UserFriend = db.Column(db.String)
    UserInfo = db.Column(db.String)
    UserIntro = db.Column(db.String)
    UserIM = db.Column(db.String)
    UserRoleID = db.Column(db.Integer, nullable=False, server_default=db.text("'3'"))
    UserAccountStatus = db.Column(db.Integer, nullable=False, server_default=db.text("'1'"))
    Birthday = db.Column(db.Date)


class CarbonVote(Base):
    __tablename__ = 'carbon_vote'

    TopicID = db.Column(db.Integer, primary_key=True, server_default=db.text("'0'"))
    Type = db.Column(db.Integer, server_default=db.text("'0'"))
    Expiry = db.Column(db.Integer, nullable=False)
    Items = db.Column(db.String)
    Result = db.Column(db.String)
    BallotUserList = db.Column(db.String)
    BallotIPList = db.Column(db.String)
    BallotItemsList = db.Column(db.String)


class ShadowCourseCategory(Base):
    __tablename__ = 'shadow_course_categories'

    uid = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(16), nullable=False, unique=True)


class ShadowCourseCourse(Base):
    __tablename__ = 'shadow_course_courses'

    uid = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(128), nullable=False, unique=True)
    image_uri = db.Column(db.String(256), nullable=False)
    learn_num = db.Column(db.String(16), nullable=False)
    action_uri = db.Column(db.String(256), nullable=False)


class ShadowCourseSubclass(Base):
    __tablename__ = 'shadow_course_subclasses'

    uid = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(32), nullable=False, unique=True)
    image_uri = db.Column(db.String(256), nullable=False)
    category_uid = db.Column(db.Integer, nullable=False)


t_shadow_course_subclassrels = db.Table(
    'shadow_course_subclassrels', metadata,
    db.Column('course_id', db.Integer, nullable=False),
    db.Column('subclass_id', db.Integer, nullable=False),
    db.Index('Subclassesdb.Index', 'course_id', 'subclass_id')
)


class ShadowDailyArticle(Base):
    __tablename__ = 'shadow_daily_articles'

    uid = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(128), nullable=False, unique=True)
    banner_uri = db.Column(db.String(256, 'utf8mb4_unicode_ci'), nullable=False)
    image_uri = db.Column(db.String(256, 'utf8mb4_unicode_ci'), nullable=False)
    post_time = db.Column(db.String(16, 'utf8mb4_unicode_ci'), nullable=False)
    content = db.Column(db.String(collation='utf8mb4_unicode_ci'), nullable=False)


class ShadowDailyCategory(Base):
    __tablename__ = 'shadow_daily_categories'

    uid = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(32), nullable=False, unique=True)


t_shadow_daily_categoryrels = db.Table(
    'shadow_daily_categoryrels', metadata,
    db.Column('article_id', db.Integer, nullable=False),
    db.Column('category_id', db.Integer, nullable=False),
    db.Index('Categoriesdb.Index', 'article_id', 'category_id')
)


t_shadow_daily_tagrels = db.Table(
    'shadow_daily_tagrels', metadata,
    db.Column('article_id', db.Integer, nullable=False),
    db.Column('tag_id', db.Integer, nullable=False),
    db.Index('Tagsdb.Index', 'article_id', 'tag_id')
)


class ShadowDailyTag(Base):
    __tablename__ = 'shadow_daily_tags'

    uid = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(32), nullable=False, unique=True)


class TypechoComment(Base):
    __tablename__ = 'typecho_comments'

    coid = db.Column(db.Integer, primary_key=True)
    cid = db.Column(db.Integer, index=True, server_default=db.text("'0'"))
    created = db.Column(db.Integer, index=True, server_default=db.text("'0'"))
    author = db.Column(db.String(200))
    authorId = db.Column(db.Integer, server_default=db.text("'0'"))
    ownerId = db.Column(db.Integer, server_default=db.text("'0'"))
    mail = db.Column(db.String(200))
    url = db.Column(db.String(200))
    ip = db.Column(db.String(64))
    agent = db.Column(db.String(200))
    text = db.Column(db.Text)
    type = db.Column(db.String(16), server_default=db.text("'comment'"))
    status = db.Column(db.String(16), server_default=db.text("'approved'"))
    parent = db.Column(db.Integer, server_default=db.text("'0'"))


class TypechoContent(Base):
    __tablename__ = 'typecho_contents'

    cid = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(200))
    slug = db.Column(db.String(200), unique=True)
    created = db.Column(db.Integer, index=True, server_default=db.text("'0'"))
    modified = db.Column(db.Integer, server_default=db.text("'0'"))
    text = db.Column(db.Text)
    order = db.Column(db.Integer, server_default=db.text("'0'"))
    authorId = db.Column(db.Integer, server_default=db.text("'0'"))
    template = db.Column(db.String(32))
    type = db.Column(db.String(16), server_default=db.text("'post'"))
    status = db.Column(db.String(16), server_default=db.text("'publish'"))
    password = db.Column(db.String(32))
    commentsNum = db.Column(db.Integer, server_default=db.text("'0'"))
    allowComment = db.Column(db.String(1), server_default=db.text("'0'"))
    allowPing = db.Column(db.String(1), server_default=db.text("'0'"))
    allowFeed = db.Column(db.String(1), server_default=db.text("'0'"))
    parent = db.Column(db.Integer, server_default=db.text("'0'"))


class TypechoField(Base):
    __tablename__ = 'typecho_fields'

    cid = db.Column(db.Integer, primary_key=True, nullable=False)
    name = db.Column(db.String(200), primary_key=True, nullable=False)
    type = db.Column(db.String(8), server_default=db.text("'str'"))
    str_value = db.Column(db.Text)
    int_value = db.Column(db.Integer, index=True, server_default=db.text("'0'"))
    float_value = db.Column(db.Float, index=True, server_default=db.text("'0'"))


class TypechoMeta(Base):
    __tablename__ = 'typecho_metas'

    mid = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(200))
    slug = db.Column(db.String(200), index=True)
    type = db.Column(db.String(32), nullable=False)
    description = db.Column(db.String(200))
    count = db.Column(db.Integer, server_default=db.text("'0'"))
    order = db.Column(db.Integer, server_default=db.text("'0'"))
    parent = db.Column(db.Integer, server_default=db.text("'0'"))


class TypechoOption(Base):
    __tablename__ = 'typecho_options'

    name = db.Column(db.String(32), primary_key=True, nullable=False)
    user = db.Column(db.Integer, primary_key=True, nullable=False, server_default=db.text("'0'"))
    value = db.Column(db.Text)


class TypechoRelationship(Base):
    __tablename__ = 'typecho_relationships'

    cid = db.Column(db.Integer, primary_key=True, nullable=False)
    mid = db.Column(db.Integer, primary_key=True, nullable=False)


class TypechoUser(Base):
    __tablename__ = 'typecho_users'

    uid = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(32), unique=True)
    password = db.Column(db.String(64))
    mail = db.Column(db.String(200), unique=True)
    url = db.Column(db.String(200))
    screenName = db.Column(db.String(32))
    created = db.Column(db.Integer, server_default=db.text("'0'"))
    activated = db.Column(db.Integer, server_default=db.text("'0'"))
    logged = db.Column(db.Integer, server_default=db.text("'0'"))
    group = db.Column(db.String(16), server_default=db.text("'visitor'"))
    authCode = db.Column(db.String(64))


class WpCommentmeta(Base):
    __tablename__ = 'wp_commentmeta'

    meta_id = db.Column(db.BigInteger, primary_key=True)
    comment_id = db.Column(db.BigInteger, nullable=False, index=True, server_default=db.text("'0'"))
    meta_key = db.Column(db.String(255, 'utf8mb4_unicode_ci'), index=True)
    meta_value = db.Column(db.String(collation='utf8mb4_unicode_ci'))


class WpComment(Base):
    __tablename__ = 'wp_comments'
    __table_args__ = (
        db.Index('comment_approved_date_gmt', 'comment_approved', 'comment_date_gmt'),
    )

    comment_ID = db.Column(db.BigInteger, primary_key=True)
    comment_post_ID = db.Column(db.BigInteger, nullable=False, index=True, server_default=db.text("'0'"))
    comment_author = db.Column(db.String(collation='utf8mb4_unicode_ci'), nullable=False)
    comment_author_email = db.Column(db.String(100, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("''"))
    comment_author_url = db.Column(db.String(200, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    comment_author_IP = db.Column(db.String(100, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    comment_date = db.Column(db.DateTime, nullable=False, server_default=db.text("'0000-00-00 00:00:00'"))
    comment_date_gmt = db.Column(db.DateTime, nullable=False, index=True, server_default=db.text("'0000-00-00 00:00:00'"))
    comment_content = db.Column(db.Text(collation='utf8mb4_unicode_ci'), nullable=False)
    comment_karma = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    comment_approved = db.Column(db.String(20, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("'1'"))
    comment_agent = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    comment_type = db.Column(db.String(20, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    comment_parent = db.Column(db.BigInteger, nullable=False, index=True, server_default=db.text("'0'"))
    user_id = db.Column(db.BigInteger, nullable=False, server_default=db.text("'0'"))


class WpLink(Base):
    __tablename__ = 'wp_links'

    link_id = db.Column(db.BigInteger, primary_key=True)
    link_url = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    link_name = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    link_image = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    link_target = db.Column(db.String(25, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    link_description = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    link_visible = db.Column(db.String(20, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("'Y'"))
    link_owner = db.Column(db.BigInteger, nullable=False, server_default=db.text("'1'"))
    link_rating = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    link_updated = db.Column(db.DateTime, nullable=False, server_default=db.text("'0000-00-00 00:00:00'"))
    link_rel = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    link_notes = db.Column(db.String(collation='utf8mb4_unicode_ci'), nullable=False)
    link_rss = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))


class WpOption(Base):
    __tablename__ = 'wp_options'

    option_id = db.Column(db.BigInteger, primary_key=True)
    option_name = db.Column(db.String(191, 'utf8mb4_unicode_ci'), nullable=False, unique=True, server_default=db.text("''"))
    option_value = db.Column(db.String(collation='utf8mb4_unicode_ci'), nullable=False)
    autoload = db.Column(db.String(20, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("'yes'"))


class WpPostmeta(Base):
    __tablename__ = 'wp_postmeta'

    meta_id = db.Column(db.BigInteger, primary_key=True)
    post_id = db.Column(db.BigInteger, nullable=False, index=True, server_default=db.text("'0'"))
    meta_key = db.Column(db.String(255, 'utf8mb4_unicode_ci'), index=True)
    meta_value = db.Column(db.String(collation='utf8mb4_unicode_ci'))


class WpPost(Base):
    __tablename__ = 'wp_posts'
    __table_args__ = (
        db.Index('type_status_date', 'post_type', 'post_status', 'post_date', 'ID'),
    )

    ID = db.Column(db.BigInteger, primary_key=True)
    post_author = db.Column(db.BigInteger, nullable=False, index=True, server_default=db.text("'0'"))
    post_date = db.Column(db.DateTime, nullable=False, server_default=db.text("'0000-00-00 00:00:00'"))
    post_date_gmt = db.Column(db.DateTime, nullable=False, server_default=db.text("'0000-00-00 00:00:00'"))
    post_content = db.Column(db.String(collation='utf8mb4_unicode_ci'), nullable=False)
    post_title = db.Column(db.Text(collation='utf8mb4_unicode_ci'), nullable=False)
    post_excerpt = db.Column(db.Text(collation='utf8mb4_unicode_ci'), nullable=False)
    post_status = db.Column(db.String(20, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("'publish'"))
    comment_status = db.Column(db.String(20, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("'open'"))
    ping_status = db.Column(db.String(20, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("'open'"))
    post_password = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    post_name = db.Column(db.String(200, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("''"))
    to_ping = db.Column(db.Text(collation='utf8mb4_unicode_ci'), nullable=False)
    pinged = db.Column(db.Text(collation='utf8mb4_unicode_ci'), nullable=False)
    post_modified = db.Column(db.DateTime, nullable=False, server_default=db.text("'0000-00-00 00:00:00'"))
    post_modified_gmt = db.Column(db.DateTime, nullable=False, server_default=db.text("'0000-00-00 00:00:00'"))
    post_content_filtered = db.Column(db.String(collation='utf8mb4_unicode_ci'), nullable=False)
    post_parent = db.Column(db.BigInteger, nullable=False, index=True, server_default=db.text("'0'"))
    guid = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    menu_order = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    post_type = db.Column(db.String(20, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("'post'"))
    post_mime_type = db.Column(db.String(100, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    comment_count = db.Column(db.BigInteger, nullable=False, server_default=db.text("'0'"))


class WpTermRelationship(Base):
    __tablename__ = 'wp_term_relationships'

    object_id = db.Column(db.BigInteger, primary_key=True, nullable=False, server_default=db.text("'0'"))
    term_taxonomy_id = db.Column(db.BigInteger, primary_key=True, nullable=False, index=True, server_default=db.text("'0'"))
    term_order = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))


class WpTermTaxonomy(Base):
    __tablename__ = 'wp_term_taxonomy'
    __table_args__ = (
        db.Index('term_id_taxonomy', 'term_id', 'taxonomy', unique=True),
    )

    term_taxonomy_id = db.Column(db.BigInteger, primary_key=True)
    term_id = db.Column(db.BigInteger, nullable=False, server_default=db.text("'0'"))
    taxonomy = db.Column(db.String(32, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("''"))
    description = db.Column(db.String(collation='utf8mb4_unicode_ci'), nullable=False)
    parent = db.Column(db.BigInteger, nullable=False, server_default=db.text("'0'"))
    count = db.Column(db.BigInteger, nullable=False, server_default=db.text("'0'"))


class WpTermmeta(Base):
    __tablename__ = 'wp_termmeta'

    meta_id = db.Column(db.BigInteger, primary_key=True)
    term_id = db.Column(db.BigInteger, nullable=False, index=True, server_default=db.text("'0'"))
    meta_key = db.Column(db.String(255, 'utf8mb4_unicode_ci'), index=True)
    meta_value = db.Column(db.String(collation='utf8mb4_unicode_ci'))


class WpTerm(Base):
    __tablename__ = 'wp_terms'

    term_id = db.Column(db.BigInteger, primary_key=True)
    name = db.Column(db.String(200, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("''"))
    slug = db.Column(db.String(200, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("''"))
    term_group = db.Column(db.BigInteger, nullable=False, server_default=db.text("'0'"))


class WpUsermeta(Base):
    __tablename__ = 'wp_usermeta'

    umeta_id = db.Column(db.BigInteger, primary_key=True)
    user_id = db.Column(db.BigInteger, nullable=False, index=True, server_default=db.text("'0'"))
    meta_key = db.Column(db.String(255, 'utf8mb4_unicode_ci'), index=True)
    meta_value = db.Column(db.String(collation='utf8mb4_unicode_ci'))


class WpUser(Base):
    __tablename__ = 'wp_users'

    ID = db.Column(db.BigInteger, primary_key=True)
    user_login = db.Column(db.String(60, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("''"))
    user_pass = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    user_nicename = db.Column(db.String(50, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("''"))
    user_email = db.Column(db.String(100, 'utf8mb4_unicode_ci'), nullable=False, index=True, server_default=db.text("''"))
    user_url = db.Column(db.String(100, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    user_registered = db.Column(db.DateTime, nullable=False, server_default=db.text("'0000-00-00 00:00:00'"))
    user_activation_key = db.Column(db.String(255, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
    user_status = db.Column(db.Integer, nullable=False, server_default=db.text("'0'"))
    display_name = db.Column(db.String(250, 'utf8mb4_unicode_ci'), nullable=False, server_default=db.text("''"))
