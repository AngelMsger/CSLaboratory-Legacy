from . import api, auth, session
from ..models import ShadowDailyArticle, ShadowDailyCategory, ShadowDailyTag, t_shadow_daily_categoryrels,\
    t_shadow_daily_tagrels
from flask_restful import abort, reqparse, Resource


class Article(Resource):
    decorators = [auth.login_required]

    def __init__(self):
        super().__init__()
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('limit', type=int, default=128, location='values')
        self.parser.add_argument('offset', type=int, default=0, location='values')

    def get(self):
        args = self.parser.parse_args()
        result = []
        offset = args['offset']
        limit = args['limit']
        for row in ShadowDailyArticle.query.order_by(ShadowDailyArticle.uid.desc()).offset(offset).limit(limit).all():
            result.append({
                'id': row.uid,
                'title': row.title,
                'bannerURI': row.banner_uri,
                'imageURI': row.image_uri,
                'postTime': row.post_time,
                'content': row.content
            })
        return result


class Category(Resource):
    pass


class Tag(Resource):
    pass

api.add_resource(Article, '/daily/articles', endpoint='daily_article')
api.add_resource(Category, '/daily/categories', endpoint='daily_category')
api.add_resource(Tag, '/daily/tags', endpoint='daily_tag')
