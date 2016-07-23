package com.sina.crowclub.network.Parse;

import java.io.Serializable;

/**
 * Created by wu on 2016/7/17.
 */
public class StoryBean implements Serializable {
    public int id;
    public String name;
    public int create_time;
    public boolean isSingle;
    public int albumId;
    public String albumName;
    public boolean isEditable;

    @Override
    public String toString() {
        return "{id:"+id+";name:"+name+";create_time:"+create_time+";isSingle:"+isSingle
                +";albumId:"+albumId+";albumName:"+albumName+";isEditable:"+isEditable+"}";
    }
}
