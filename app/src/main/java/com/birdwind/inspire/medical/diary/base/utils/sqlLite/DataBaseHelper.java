package com.diary.init.base.utils.sqlLite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @ClassName: DataBaseOpenHelper
 * @Description: 資料庫工具類
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static Map<String, DataBaseHelper> dbMaps = new HashMap<String, DataBaseHelper>();

    private OnSqliteUpdateListener onSqliteUpdateListener;

    /**
     * 建表語句列表
     */
    private List<String> createTableList;

    private String nowDbName;

    private DataBaseHelper(Context context, String dbName, int dbVersion, List<String> tableSqls) {
        super(context, dbName, null, dbVersion);
        nowDbName = dbName;
        createTableList = new ArrayList<String>();
        createTableList.addAll(tableSqls);
    }

    /**
     *
     * @Title: getInstance
     * @Description: 獲取資料庫例項
     * @param @param context
     * @param @param userId
     * @param @return
     * @return DataBaseOpenHelper
     * @author lihy
     */
    public static DataBaseHelper getInstance(Context context, String dbName, int dbVersion,
                                             List<String> tableSqls) {
        DataBaseHelper dataBaseHelper = dbMaps.get(dbName);
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context, dbName, dbVersion, tableSqls);
        }
        dbMaps.put(dbName, dataBaseHelper);
        return dataBaseHelper;
    };

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sqlString : createTableList) {
            db.execSQL(sqlString);
        }
    }

    /**
     *
     * @Title: execSQL
     * @Description: Sql寫入
     * @param @param sql
     * @param @param bindArgs
     * @return void
     * @author lihy
     */
    public void execSQL(String sql, Object[] bindArgs) {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseHelper) {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            database.execSQL(sql, bindArgs);
        }
    }

    /**
     *
     * @Title: rawQuery
     * @Description:
     * @param @param sql查詢
     * @param @param bindArgs
     * @param @return
     * @return Cursor
     * @author lihy
     */
    public Cursor rawQuery(String sql, String[] bindArgs) {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseHelper) {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, bindArgs);
            return cursor;
        }
    }

    /**
     *
     * @Title: insert @Description: 插入資料 @param @param table @param @param contentValues 設定檔案 @return void 返回型別 @author
     *         lihy @throws
     */
    public void insert(String table, ContentValues contentValues) {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseHelper) {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            database.insert(table, null, contentValues);
        }
    }

    /**
     *
     * @Title: update @Description: 更新 @param @param table @param @param values @param @param whereClause @param @param
     *         whereArgs 設定檔案 @return void 返回型別 @throws
     */
    public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseHelper) {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            database.update(table, values, whereClause, whereArgs);
        }
    }

    /**
     *
     * @Title: delete
     * @Description:刪除
     * @param @param table
     * @param @param whereClause
     * @param @param whereArgs
     * @return void
     * @author lihy
     */
    public void delete(String table, String whereClause, String[] whereArgs) {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseHelper) {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            database.delete(table, whereClause, whereArgs);
        }
    }

    /**
     *
     * @Title: query
     * @Description: 查
     * @param @param table
     * @param @param columns
     * @param @param selection
     * @param @param selectionArgs
     * @param @param groupBy
     * @param @param having
     * @param @param orderBy
     * @return void
     * @author lihy
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
        String having, String orderBy) {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseHelper) {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            return cursor;
        }
    }

    /**
     *
     * @Description:查
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return Cursor
     * @exception:
     * @author: lihy
     * @time:2015-4-3 上午9:37:29
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
        String having, String orderBy, String limit) {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseHelper) {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            return cursor;
        }
    }

    /**
     *
     * @Description 查詢，方法過載,table表名，sqlString條件
     * @param @return
     * @return Cursor
     * @author lihy
     */
    public Cursor query(String tableName, String sqlString) {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseHelper) {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from " + tableName + " " + sqlString, null);

            return cursor;
        }
    }

    /**
     * @see android.database.sqlite.SQLiteOpenHelper#close()
     */
    public void clear() {
        DataBaseHelper dataBaseHelper = dbMaps.get(nowDbName);
        dataBaseHelper.close();
        dbMaps.remove(dataBaseHelper);
    }

    /**
     * onUpgrade()方法在資料庫版本每次發生變化時都會把使用者手機上的資料庫表刪除，然後再重新建立。<br/>
     * 一般在實際專案中是不能這樣做的，正確的做法是在更新資料庫表結構時，還要考慮使用者存放於資料庫中的資料不會丟失,從版本幾更新到版本幾。(非 Javadoc)
     *
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        if (onSqliteUpdateListener != null) {
            onSqliteUpdateListener.onSqliteUpdateListener(db, arg1, arg2);
        }
    }

    public void setOnSqliteUpdateListener(OnSqliteUpdateListener onSqliteUpdateListener) {
        this.onSqliteUpdateListener = onSqliteUpdateListener;
    }

    // 資料庫更新介面程式碼

    public interface OnSqliteUpdateListener {
        void onSqliteUpdateListener(SQLiteDatabase db, int oldVersion, int newVersion);
    }

}
