# coding: utf-8

import re
import requests
import time
from bs4 import BeautifulSoup
from models import ShadowDailyArticle, ShadowDailyTag, ShadowDailyCategory, t_shadow_daily_tagrels, \
    t_shadow_daily_categoryrels
from startup import conn, DBSession
from sqlalchemy.exc import IntegrityError

base_uri = 'http://www.iplaysoft.com'
parser = 'html.parser'
headers = {
    'accept': 'text/html,application/xhtml+xml,'
              'application/xml;q=0.9,image/webp,*/*;q=0.8',
    'accept-encoding': 'gzip, deflate, sdch, br',
    'accept-language': 'zh-CN,zh;q=0.8',
    'cache-control': 'max-age=0',
    'Host': 'www.iplaysoft.com',
    'Referer': 'http://www.iplaysoft.com/',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
                  ' (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
}

filter_except = re.compile(r'http://www\.iplaysoft\.com/[^/]+/')


def get_page_count():
    print('获取分页信息')
    # soup = BeautifulSoup(requests.get(base_uri, headers=headers).text, parser)
    # page_count = soup.find('a', href='http://www.iplaysoft.com/page/2')
    # return int(page_count.get_text()[7:-3])
    # 由于实际网站在 64 页后的早期网页格式不规整或几乎没有实用价值因此此处不予采集
    return 2


def parse_page(uri):
    print('获取页面: ' + uri)
    uris = set()
    soup = BeautifulSoup(requests.get(uri, headers=headers).text, parser)
    head = soup.find('div', id='show_post_entry')
    if head is not None:
        uris.add(head.find('a')['href'])
    body = soup.find_all('div', class_='entry')
    for item in body:
        item_uri = item.find('a')['href']
        if not filter_except.match(item_uri):
            uris.add(item.find('a')['href'])
    return uris


def parse_detail(uri, session):
    print('获取详情: ' + uri)
    article = ShadowDailyArticle()
    soup = BeautifulSoup(requests.get(uri, headers=headers).text, parser)
    title = soup.find('h1')
    if title is not None:
        article.title = title.get_text()
        article.banner_uri = soup.find('img')['src']
        article.image_uri = soup.find('div', class_='entry-banner').img['src']
        article.post_time = soup.find('div', class_='entry-cat').get_text().split()[-1]
        while soup.h3 is not None and soup.h3.get_text() != '相关文件下载地址：':
            soup.h3.decompose()
        content = re.sub(r'\{[^}]+\}', '', soup.find('div', class_='entry-content').get_text(strip=True)
                         .replace('{}', '')).replace('if(ggadCount<3)', '')
        filter_offset = content.find('相关文件下载地址')
        article.content = content if filter_offset == -1 else content[0: filter_offset]
        try:
            session.add(article)
            session.commit()
        except IntegrityError:
            session.rollback()
        article_categories = set([tag.get_text() for tag in soup.find_all('a', rel='category tag')])
        article_tags = set([tag.get_text() for tag in soup.find_all('a', rel=re.compile(r'^tag$'))])
        for article_category in article_categories:
            category = ShadowDailyCategory()
            category.title = article_category
            try:
                session.add(category)
                session.commit()
            except IntegrityError:
                session.rollback()
            if article.uid is not None:
                category_id = category.uid or session.query(ShadowDailyCategory)\
                    .filter(ShadowDailyCategory.title == category.title).one().uid
                conn.execute(t_shadow_daily_categoryrels.insert(
                    values={'article_id': article.uid, 'category_id': category_id}))
        for article_tag in article_tags:
            tag = ShadowDailyTag()
            tag.title = article_tag
            try:
                session.add(tag)
                session.commit()
                if article.uid is not None:
                    tag_id = tag.uid or session.query(ShadowDailyTag).filter(ShadowDailyTag.title == tag.title).one()\
                        .uid
                    conn.execute(t_shadow_daily_tagrels.insert(values={'article_id': article.uid, 'tag_id': tag_id}))
            except IntegrityError:
                session.rollback()


def start():
    print('获取异次元网站数据')
    page_count = get_page_count()
    session = DBSession()
    while page_count > 0:
        uris = parse_page(base_uri + '/page/' + str(page_count))
        for uri in uris:
            parse_detail(uri, session)
            # time.sleep(3)
        page_count -= 1

    session.close()
