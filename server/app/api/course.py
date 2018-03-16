import json
from . import api, auth, session
from ..models import ShadowCourseCategory, ShadowCourseCourse, ShadowCourseSubclass, t_shadow_course_subclassrels
from flask_restful import abort, reqparse, Resource


class Course(Resource):
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
        for row in ShadowCourseCourse.query.order_by(ShadowCourseCourse.uid.desc()).offset(offset).limit(limit).all():
            result.append({
                'id': row.uid,
                'title': row.title,
                'imageURI': row.image_uri,
                'learnNum': row.learn_num,
                'actionURI': row.action_uri
            })
        return result


class Category(Resource):
    decorators = [auth.login_required]

    def get(self):
        result = []
        for row in ShadowCourseCategory.query.all():
            subclasses = []
            for sub_row in ShadowCourseSubclass.query.filter(ShadowCourseSubclass.category_uid == row.uid).all():
                subclasses.append({
                    'id': sub_row.uid,
                    'title': sub_row.title,
                    'imageURI': sub_row.image_uri
                })
            category = {
                'id': row.uid,
                'title': row.title,
                'subclasses': subclasses
            }
            result.append(category)
        return result

api.add_resource(Course, '/course/courses', endpoint='course_course')
api.add_resource(Category, '/course/categories', endpoint='course_category')
