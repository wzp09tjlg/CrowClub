package com.sina.crowclub.network.Parse.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sina.crowclub.network.Parse.ChannelItem;
import com.sina.crowclub.network.Parse.db.SQLHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Walter on 2016/1/7.
 */
public class ChannelDao implements ChannelDaoInface {
    //data
    private SQLHelper helper = null;
    public ChannelDao(Context context) {
        helper = new SQLHelper(context);
    }

    @Override
    public boolean addCache(ChannelItem item, final String tableName) {
        // TODO Auto-generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        long id = -1;
        try {
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", item.getName());
            values.put("id", item.getId());
            values.put("orderId", item.getOrderId());
            values.put("selected", item.getSelected());
            id = database.insert(tableName, null, values);
            flag = (id != -1 ? true : false);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean addCache(List<ChannelItem> list,final String tableName) {
        //TODO Auto=generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        long id = -1;
        try{
          database = helper.getWritableDatabase();
          ChannelItem item = null;
            for(int i=0;i<list.size();i++){
                item = list.get(i);
                ContentValues values = new ContentValues();
                values.put("name",item.getName());
                values.put("id",item.getId());
                values.put("orderId",item.getOrderId());
                values.put("selected",item.getSelected());
                id = database.insert(tableName, null, values);
                flag = id == 1 ? true : false;
            }
        }catch (Exception e){
                // TODO hanlde exception
        }finally {
            if(database != null)
                database.close();
        }
        return flag;
    }

    @Override
    public boolean deleteCache(String whereClause, String[] whereArgs,final String tableName) {
        // TODO Auto-generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            count = database.delete(tableName, whereClause, whereArgs);
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean updateCache(ContentValues values, String whereClause,
                               String[] whereArgs,final String tableName) {
        // TODO Auto-generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            count = database.update(tableName, values, whereClause, whereArgs);
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public Map<String, String> viewCache(String selection,
                                         String[] selectionArgs,final String tableName) {
        // TODO Auto-generated method stub
        SQLiteDatabase database = null;
        Cursor cursor = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(true, tableName, null, selection,
                            selectionArgs, null, null, null, null);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor
                            .getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return map;
    }

    @Override
    public List<Map<String, String>> listCache(String selection,String[] selectionArgs,final String tableName) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(false, tableName, null, selection,selectionArgs, null, null, null, null);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cols_len; i++) {

                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor
                            .getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
                list.add(map);
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return list;
    }

    public void clearFeedTable(final String tableName) {
        String sql = "DELETE FROM " + tableName + ";";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
        revertSeq(tableName);
    }

    private void revertSeq(final String tableName) {
        String sql = "update sqlite_sequence set seq=0 where name='"
                + tableName + "'";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
    }
}
