package com.sina.crowclub.network.Parse.dao;

import android.content.ContentValues;

import com.sina.crowclub.network.Parse.ChannelItem;

import java.util.List;
import java.util.Map;

/**
 * Created by Walter on 2016/1/7.
 */
public interface ChannelDaoInface {
    public boolean addCache(ChannelItem item, final String tableName);

    public boolean addCache(List<ChannelItem> list, final String tableName);

    public boolean deleteCache(String whereClause, String[] whereArgs, final String tableName);

    public boolean updateCache(ContentValues values, String whereClause,
                               String[] whereArgs, final String tableName);

    public Map<String, String> viewCache(String selection,
                                         String[] selectionArgs, final String tableName);

    public List<Map<String, String>> listCache(String selection,
                                               String[] selectionArgs, final String tableName);

    public void clearFeedTable(final String tableName);
}
