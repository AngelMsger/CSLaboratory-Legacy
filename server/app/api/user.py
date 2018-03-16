from . import api, auth
from ..models import WpUser
from flask_restful import abort, reqparse, Resource


class Login(Resource):
    decorators = [auth.login_required]

    def __init__(self):
        super().__init__()
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('username', type=str, required=True, help='Username Required.', location='json')
        self.parser.add_argument('password', type=str, required=True, help='Password Required.', location='json')

    def post(self):
        args = self.parser.parse_args()
        user_login = args.get('username')
        user_pass = args.get('password')
        user = WpUser.query.filter_by(user_login=user_login, user_pass=user_pass).one()
        if user is None:
            abort(404)
        else:
            return {
                'nickname': user.display_name
            }


class Signin(Resource):
    pass


class Signup(Resource):
    pass


api.add_resource(Login, '/user/login')
api.add_resource(Signin, '/user/signin')
api.add_resource(Signup, '/user/signup')
