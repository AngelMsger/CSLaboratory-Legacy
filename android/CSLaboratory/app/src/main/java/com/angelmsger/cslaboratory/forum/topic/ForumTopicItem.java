package com.angelmsger.cslaboratory.forum.topic;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 数据项实例类。
 */
@Entity(nameInDb = "forum_topics", indexes = {
        @Index(value = "nickname", name = "NicknameIndex")
})
public class ForumTopicItem {
    @Id(autoincrement = true)
    private Long id;

    private String avatarURI;

    @NotNull
    private String nickname;

    @NotNull
    private String title;

    @NotNull
    private String content;

    public ForumTopicItem() {
    }

    @Keep
    public ForumTopicItem(Long id, String avatarURI, String nickname,
                          String title, String content) {
        this.id = id;
        this.avatarURI = avatarURI;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarURI() {
        return avatarURI;
    }

    public void setAvatarURI(String avatarURI) {
        this.avatarURI = avatarURI;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
