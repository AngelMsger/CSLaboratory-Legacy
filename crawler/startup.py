# coding: utf-8

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

"""
    ------------- 猫爪实验室模拟数据爬取 -------------
    本脚本为爬虫，启动后会依次爬取异次元，慕课网和实验楼三个
    网站的页面数据，将数据格式化后存储至数据库。脚本默认数据
    表已经手动创建(数据库创建脚本请查看 README 文件)。
    请注意，通过爬虫爬取他人数据是不受保护的行为，本脚本仅用
    于学习与交流，请勿用于任何其他用途。
"""

engine = create_engine('mysql+pymysql://username:password@localhost:3306/dbname?charset=utf8')
conn = engine.connect()

DBSession = sessionmaker(bind=engine)

from main import imooc, shiyanlou, iplaysoft

if __name__ == '__main__':
    iplaysoft.start()
    imooc.start()
    shiyanlou.start()
    conn.close()
    print('全部完成')
