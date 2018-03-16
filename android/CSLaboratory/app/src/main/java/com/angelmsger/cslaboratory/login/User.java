package com.angelmsger.cslaboratory.login;

public class User {
    private Base base;
    private Extra extra;

    public User() {
    }

    public User(Base base, Extra extra) {
        this.base = base;
        this.extra = extra;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public static class Base {
        private String id;
        private String nickname;
        private String imageURI;
        private String avatarURI;

        public Base() {
        }

        public Base(String id, String nickname, String imageURI, String avatarURI) {
            this.id = id;
            this.nickname = nickname;
            this.imageURI = imageURI;
            this.avatarURI = avatarURI;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getImageURI() {
            return imageURI;
        }

        public void setImageURI(String imageURI) {
            this.imageURI = imageURI;
        }

        public String getAvatarURI() {
            return avatarURI;
        }

        public void setAvatarURI(String avatarURI) {
            this.avatarURI = avatarURI;
        }
    }

    public static class Extra {
        private int sex;
        private int topicCount;
        private int replyCount;
        private int followerCount;
        private int goodTopicCount;
        private int favUser;
        private int favTag;
        private int favTopic;

        public Extra() {
        }

        public Extra(int sex, int topicCount, int replyCount, int followerCount,
                     int goodTopicCount, int favUser, int favTag, int favTopic) {
            this.sex = sex;
            this.topicCount = topicCount;
            this.replyCount = replyCount;
            this.followerCount = followerCount;
            this.goodTopicCount = goodTopicCount;
            this.favUser = favUser;
            this.favTag = favTag;
            this.favTopic = favTopic;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getTopicCount() {
            return topicCount;
        }

        public void setTopicCount(int topicCount) {
            this.topicCount = topicCount;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getFollowerCount() {
            return followerCount;
        }

        public void setFollowerCount(int followerCount) {
            this.followerCount = followerCount;
        }

        public int getGoodTopicCount() {
            return goodTopicCount;
        }

        public void setGoodTopicCount(int goodTopicCount) {
            this.goodTopicCount = goodTopicCount;
        }

        public int getFavUser() {
            return favUser;
        }

        public void setFavUser(int favUser) {
            this.favUser = favUser;
        }

        public int getFavTag() {
            return favTag;
        }

        public void setFavTag(int favTag) {
            this.favTag = favTag;
        }

        public int getFavTopic() {
            return favTopic;
        }

        public void setFavTopic(int favTopic) {
            this.favTopic = favTopic;
        }
    }
}
