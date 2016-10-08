package com.sina.crowclub.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wu on 2016/10/8.
 * 通用的数据库工具类
 * 1.SQL的关键字使用大些字母来实现,表属性使用小写字母来实现
 * 2.在创建表时,一定记得在创建表字段时注明 含义及创建的时间和版本,方便以后对数据库的维护
 */
public class CommonDB extends SQLiteOpenHelper {
    /** Data */
    private Context mContext;

    /** FinalData */
    private static final String  DB_NAME = "CommonDB.db";//创建数据库的名字
    private static final int  VERSION = 1;


    /**************************************/
    public CommonDB(Context context){
        super(context, DB_NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //查看表是否在数据库中存在
    protected boolean isTableExist(String tableName){
        String sql = "SELECT count(*) FROM sqlite_master " +
                "WHERE type='table' AND name='" + tableName + "'";
        Cursor cur = getReadableDatabase().rawQuery(sql, null);
        int count = -1;
        while (cur.moveToNext()) {
            count = cur.getInt(0);
        }
        if (count <= 0) {
            // 表不存在
        } else {

        }
        return false;
    }
}
