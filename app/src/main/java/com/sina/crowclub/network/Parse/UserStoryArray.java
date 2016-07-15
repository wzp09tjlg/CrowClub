package com.sina.crowclub.network.Parse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wu on 2016/7/15.
 */
public class UserStoryArray extends Data implements Serializable {
    public String count;
    public List<Rows> rows;

    public class Rows implements Serializable{
        public int story_id;
        public String title;
        public String imgae;
        public String summary;
        public String create_time;
        public boolean show_icon;
        public String type;
        public String status;
        public User suer;
        public String fav_count;
        public String like_count;
        public String comment_count;
        public Album album;

        public class User implements Serializable{
            public String uid;
            public String nickname;
            public String small_avatar;
            public String large_avatar;
            public String weibo_verified_type;
        }

        public class Album implements Serializable{
            public String album_id;
            public String title;
            public String intro;
            public String coverl;
        }
    }
}
