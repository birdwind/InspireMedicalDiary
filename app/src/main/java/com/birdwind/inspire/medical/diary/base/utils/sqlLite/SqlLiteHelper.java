package com.birdwind.inspire.medical.diary.base.utils.sqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SqlLiteHelper extends SQLiteOpenHelper {

    private String tableName;

    public SqlLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String tableName) {
        super(context, name, factory, version);
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS " + tableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "url TEXT " +
                ");";
        db.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + tableName;
        db.execSQL(SQL);
    }
}
