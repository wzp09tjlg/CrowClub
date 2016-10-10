package com.sina.crowclub.database;

import com.sina.crowclub.network.Parse.ChannelItem;

import java.util.List;

/**
 * Created by wu on 2016/10/9.
 * dao中执行的动作都统一在这里定义,便于统一管理。这里是根据表名来划分逻辑处理
 */
public interface DaoInterface {
    /** channel_news */
    //查询
    ChannelItem getNewsById(int id);
    //插入
    boolean insertNewsItem(ChannelItem item);
    boolean insertNewsItem(List<ChannelItem> items);
    //删除
    boolean deleteNewsItemById(int id);
    //更新
    boolean updateNewsItem(int id,int selected);
    boolean updateNewsItem(ChannelItem item);
    //排序
    List<ChannelItem> getSortNewsItemList();

    /** channel_video*/
    //查询
    ChannelItem getVideoById(int id);
    //插入
    boolean insertVideoItem(ChannelItem item);
    boolean insertVideoItem(List<ChannelItem> items);
    //删除
    boolean deleteVideoItemById(int id);
    //更新
    boolean updateVideoItem(int id,int selected);
    boolean updateVideoItem(ChannelItem item);
    //排序
    List<ChannelItem> getSortVideoItemList();
}
