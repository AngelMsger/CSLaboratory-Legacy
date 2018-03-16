# Project Scratch

## 猫爪实验室配套数据获取脚本

### 项目介绍：

Scratch 是与[猫爪实验室网站](Https://AngelMsger.Com)，App(猫爪实验室)配套的数据获取脚本，本脚本通过爬取及分析第三方网页的内容并将获取的数据分类存放至数据库来为猫爪实验室的其他服务提供模拟数据。

### 首要声明:

使用爬虫脚本获取他人数据是不受保护的行为，本脚本限制了爬取速度以避免影响他人网站的正常工作，请不要尝试修改这些内容。本脚本及通过本脚本获得的数据版权归属原作者所有。脚本仅用于学习交流，请勿用于其他用途。

目前脚本爬取的内容来源于以下网站：

1. [异次元软件世界](http://www.iplaysoft.com/)
2. [慕课网](http://www.imooc.com/)
3. [实验楼](https://www.shiyanlou.com/)

### 如何使用：

1. 首先你需要安装 [Python 3](https://www.python.org/) 并激活本脚本附带的虚拟环境，激活虚拟环境的方法为在当前目录下执行 source venv/bin/activate 。
2. 在你的数据库中执行一下代码以创建数据表：

```
CREATE TABLE IF NOT EXISTS shadow_daily_articles(
  uid INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title NVARCHAR(128) UNIQUE NOT NULL,
  image_uri VARCHAR(256) NOT NULL,
  post_time VARCHAR(16) NOT NULL,
  context longtext COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS shadow_daily_tags(
  uid INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title NVARCHAR(32) UNIQUE NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS shadow_daily_tagrels(
  article_id INT(10) UNSIGNED NOT NULL,
  tag_id INTEGER(10) UNSIGNED NOT NULL,
  KEY TagsIndex(article_id, tag_id)
);

CREATE TABLE IF NOT EXISTS shadow_daily_categories(
  uid INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title NVARCHAR(32) UNIQUE NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS shadow_daily_categoryrels(
  article_id INT(10) UNSIGNED NOT NULL,
  category_id INTEGER(10) UNSIGNED NOT NULL,
  KEY CategoriesIndex(article_id, category_id)
);
```

```
CREATE TABLE IF NOT EXISTS shadow_course_courses(
  uid INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title NVARCHAR(128) UNIQUE NOT NULL,
  image_uri VARCHAR(256) NOT NULL,
  learn_num VARCHAR(16) NOT NULL,
  action_uri NVARCHAR(256) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS shadow_course_subclasses(
  uid INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title NVARCHAR(32) UNIQUE NOT NULL,
  image_uri VARCHAR(256) NOT NULL,
  category_uid INT(10) UNSIGNED NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS shadow_course_subclassrels(
  course_id INT(10) UNSIGNED NOT NULL,
  subclass_id INTEGER(10) UNSIGNED NOT NULL,
  KEY SubclassesIndex(course_id, subclass_id)
);

CREATE TABLE IF NOT EXISTS shadow_course_categories(
  uid INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title NVARCHAR(16) UNIQUE NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1;
```

3. 手动配置 startup.py 文件下的数据库连接 URI 以适配您的数据类型及安全信息。
4. 执行 startup.py 脚本，并等待程序执行结束。

