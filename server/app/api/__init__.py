from .. import app, db
from flask_restful import Api
from flask_httpauth import HTTPBasicAuth

api = Api(app)
auth = HTTPBasicAuth()

session = db.session

developers = {
    'AngelMsger': 'password'
}


@auth.get_password
def get_password(username):
    return None if username not in developers else developers[username]

from . import course, daily, forum, quality, user
