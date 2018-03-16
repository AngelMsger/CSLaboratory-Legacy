# coding: utf-8

import requests
import time
from bs4 import BeautifulSoup
from models import ShadowCourseCourse, ShadowCourseSubclass, t_shadow_course_subclassrels
from startup import conn, DBSession
from sqlalchemy.exc import IntegrityError

base_uri = 'https://www.shiyanlou.com'
parser = 'html.parser'
headers = {
    'accept': 'text/html,application/xhtml+xml,'
              'application/xml;q=0.9,image/webp,*/*;q=0.8',
    'accept-encoding': 'gzip, deflate, sdch, br',
    'accept-language': 'zh-CN,zh;q=0.8',
    'Connection': 'keep-alive',
    'cache-control': 'max-age=0',
    'Host': 'www.shiyanlou.com',
    'Referer': 'https://www.shiyanlou.com/',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
                  ' (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
}


def get_subclasses(session):
    print('获取标签信息')
    subclasses = dict()
    soup = BeautifulSoup(requests.get(base_uri + '/courses/', headers=headers).text, parser)
    for sibling in soup.find_all('div', class_='col-md-11 course-cates-content')[1].find('a').find_next_siblings():
        subclass = ShadowCourseSubclass()
        subclass.title = sibling.get_text()
        subclass.category_uid = 0
        subclass.image_uri = ''
        subclasses[sibling['href']] = subclass
        try:
            session.add(subclass)
            session.commit()
        except IntegrityError:
            session.rollback()
    return subclasses


def parse_detail(uri, subclass_id, session):
    while uri is not None and len(uri) > 7:
        print('获取页面' + uri)
        soup = BeautifulSoup(requests.get(base_uri + uri, headers=headers).text, parser)
        for item in soup.find_all('div', class_='col-md-4 col-sm-6 course'):
            course = ShadowCourseCourse()
            course.image_uri = item.find('img')['src']
            course.title = item.find('span', class_='course-title').get_text()
            course.action_uri = base_uri + item.find('a')['href']
            course.learn_num = int(item.find('span', class_='course-per-num pull-left').get_text())
            try:
                session.add(course)
                session.commit()
                conn.execute(t_shadow_course_subclassrels.insert(
                    values={'course_id': course.uid, 'subclass_id': subclass_id}))
            except IntegrityError:
                session.rollback()
        next_page = soup.find('a', attrs={'aria-label': 'Next'})
        uri = None if next_page is None else next_page['href']


def start():
    session = DBSession()
    subclasses = get_subclasses(session)
    for subclass_uri, subclass in subclasses.items():
        parse_detail(subclass_uri, subclass.uid, session)
        time.sleep(3)
    session.close()
