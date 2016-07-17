package com.sina.crowclub.view.adapter;

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

    @Override
    public String toString() {
        return "{id:"+id+";name:"+name+";create_time:"+create_time+";isSingle:"+isSingle
                +";albumId:"+albumId+";albumName:"+albumName+"}";
    }
}
