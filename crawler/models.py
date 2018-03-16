# coding: utf-8
from sqlalchemy import Column, Index, Integer, String, Table
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()
metadata = Base.metadata


class ShadowCourseCategory(Base):
    __tablename__ = 'shadow_course_categories'

    uid = Column(Integer, primary_key=True)
    title = Column(String(16), nullable=False, unique=True)


class ShadowCourseCourse(Base):
    __tablename__ = 'shadow_course_courses'

    uid = Column(Integer, primary_key=True)
    title = Column(String(128), nullable=False, unique=True)
    image_uri = Column(String(256), nullable=False)
    learn_num = Column(String(16), nullable=False)
    action_uri = Column(String(256), nullable=False)


class ShadowCourseSubclass(Base):
    __tablename__ = 'shadow_course_subclasses'

    uid = Column(Integer, primary_key=True)
    title = Column(String(32), nullable=False, unique=True)
    image_uri = Column(String(256), nullable=False)
    category_uid = Column(Integer, nullable=False)


t_shadow_course_subclassrels = Table(
    'shadow_course_subclassrels', metadata,
    Column('course_id', Integer, nullable=False),
    Column('subclass_id', Integer, nullable=False),
    Index('SubclassesIndex', 'course_id', 'subclass_id')
)


class ShadowDailyArticle(Base):
    __tablename__ = 'shadow_daily_articles'

    uid = Column(Integer, primary_key=True)
    title = Column(String(128), nullable=False, unique=True)
    banner_uri = Column(String(256, 'utf8mb4_unicode_ci'), nullable=False)
    image_uri = Column(String(256, 'utf8mb4_unicode_ci'), nullable=False)
    post_time = Column(String(16, 'utf8mb4_unicode_ci'), nullable=False)
    content = Column(String(collation='utf8mb4_unicode_ci'), nullable=False)


class ShadowDailyCategory(Base):
    __tablename__ = 'shadow_daily_categories'

    uid = Column(Integer, primary_key=True)
    title = Column(String(32), nullable=False, unique=True)


t_shadow_daily_categoryrels = Table(
    'shadow_daily_categoryrels', metadata,
    Column('article_id', Integer, nullable=False),
    Column('category_id', Integer, nullable=False),
    Index('CategoriesIndex', 'article_id', 'category_id')
)


t_shadow_daily_tagrels = Table(
    'shadow_daily_tagrels', metadata,
    Column('article_id', Integer, nullable=False),
    Column('tag_id', Integer, nullable=False),
    Index('TagsIndex', 'article_id', 'tag_id')
)


class ShadowDailyTag(Base):
    __tablename__ = 'shadow_daily_tags'

    uid = Column(Integer, primary_key=True)
    title = Column(String(32), nullable=False, unique=True)
