package com.angelmsger.cslaboratory.login;

public class Login {
    public static class Request {
        private String email;
        private String password;

        public Request() {
        }

        public Request(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Response {
        public static final int SUCCESS = 200;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;

        private int code;
        private User user;

        public Response() {
        }

        public Response(int code, User user) {
            this.code = code;
            this.user = user;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
