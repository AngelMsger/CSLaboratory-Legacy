# coding: utf-8

import re
import requests
import time
from bs4 import BeautifulSoup
from models import ShadowCourseCourse, ShadowCourseSubclass, ShadowCourseCategory, t_shadow_course_subclassrels
from startup import conn, DBSession
from sqlalchemy.exc import IntegrityError

base_uri = 'http://www.imooc.com'
parser = 'html.parser'
headers = {
    'accept': 'text/html,application/xhtml+xml,'
              'application/xml;q=0.9,image/webp,*/*;q=0.8',
    'accept-encoding': 'gzip, deflate, sdch, br',
    'accept-language': 'zh-CN,zh;q=0.8',
    'Connection': 'keep-alive',
    'cache-control': 'max-age=0',
    'Host': 'www.imooc.com',
    'Referer': 'http://www.imooc.com/',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
                  ' (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
}


def get_categories(session):
    print('获取分类信息')
    categories = dict()
    soup = BeautifulSoup(requests.get(base_uri + '/course/list', headers=headers).text, parser)
    for sibling in soup.find('li', class_='course-nav-item').find_next_siblings():
        category = ShadowCourseCategory()
        category.title = sibling.find('a').get_text()
        categories[sibling.find('a')['href']] = category
        try:
            session.add(category)
            session.commit()
        except IntegrityError:
            session.rollback()
    return categories


def get_subclasses(uri, category_id, session):
    print('获取标签信息')
    subclasses = dict()
    soup = BeautifulSoup(requests.get(base_uri + uri, headers=headers).text, parser)
    for sibling in soup.find('div', class_='course-nav-row clearfix')\
            .find_next_sibling().find('li', class_='course-nav-item').find_next_siblings():
        subclass = ShadowCourseSubclass()
        subclass.title = sibling.find('a').get_text()
        subclass.category_uid = category_id
        subclass.image_uri = ''
        subclasses[sibling.find('a')['href']] = subclass
        try:
            session.add(subclass)
            session.commit()
        except IntegrityError:
            session.rollback()
    return subclasses


def get_page_count(uri):
    soup = BeautifulSoup(requests.get(base_uri + uri, headers=headers).text, parser)
    index = soup.find('div', class_='page')
    return 1 if index is None else int(index.find_all('a')[-1]['href'].split('&page=')[-1])


def parse_detail(uri, subclass_id, session):
    print('获取页面' + uri)
    soup = BeautifulSoup(requests.get(base_uri + uri, headers=headers).text, parser)
    for item in soup.find('div', class_='moco-course-list').find_all('div', 'moco-course-wrap'):
        try:
            error = item.find('div', class_='moco-course-box')['style']
            print('跳过意外项因为发现: ' + error)
        except KeyError:
            course = ShadowCourseCourse()
            image = item.find('img')
            if image is None:
                continue
            course.image_uri = image['src']
            course.title = item.find('h3').get_text()[2:].strip()
            course.action_uri = base_uri + item.find('a')['href']
            course.learn_num = int(item.find('div', class_='moco-course-bottom').find('span').get_text()[:-3])
            try:
                session.add(course)
                session.commit()
                conn.execute(t_shadow_course_subclassrels.insert(
                    values={'course_id': course.uid, 'subclass_id': subclass_id}))
            except IntegrityError:
                session.rollback()


def start():
    session = DBSession()
    categories = get_categories(session)
    for uri, category in categories.items():
        subclasses = get_subclasses(uri, category.uid, session)
        for subclass_uri, subclass in subclasses.items():
            page_count = get_page_count(subclass_uri)
            while page_count > 0:
                parse_detail(subclass_uri + '&page=' + str(page_count), subclass.uid, session)
                time.sleep(3)
                page_count -= 1
    session.close()
