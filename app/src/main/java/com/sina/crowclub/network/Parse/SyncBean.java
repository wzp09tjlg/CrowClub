package com.sina.crowclub.network.Parse;

import java.io.Serializable;

/**
 * Created by wu on 2016/8/25.
 */
public class SyncBean implements Serializable {
    public String story_id;
    public int version;

    @Override
    public String toString() {
        return "{story_id:"+ story_id +",version:"+version+"}";
    }
}
