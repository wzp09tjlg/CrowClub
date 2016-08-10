package com.sina.crowclub.network.Parse.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Walter on 2016/1/7.
 */
public class SQLHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "database.db";// 数据库名称
    public static final int VERSION = 1;

    public static final String TABLE_CHANNEL = "channel_news";//数据表
    public static final String TABLE_VIDEO = "channel_video";//数据表

    public static final String ID = "id";//
    public static final String NAME = "name";
    public static final String ORDERID = "orderId";
    public static final String SELECTED = "selected";
    private Context context;

    private final String sqlChannel = "create table if not exists "+TABLE_CHANNEL +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ID + " INTEGER , " +
            NAME + " TEXT , " +
            ORDERID + " INTEGER , " +
            SELECTED + " SELECTED)";

    private final String sqlVideo = "create table if not exists "+TABLE_VIDEO +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ID + " INTEGER , " +
            NAME + " TEXT , " +
            ORDERID + " INTEGER , " +
            SELECTED + " SELECTED)";

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作

        db.execSQL(sqlChannel);
        db.execSQL(sqlVideo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        onCreate(db);
    }

    public static enum TABLES{
        TABLE_CHANNEL ,
        TABLE_NEW
    }
}
