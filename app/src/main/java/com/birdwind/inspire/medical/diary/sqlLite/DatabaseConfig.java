package com.birdwind.inspire.medical.diary.sqlLite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.utils.sqlLite.DataConverter;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.birdwind.inspire.medical.diary.model.response.PatientResponse;
import com.birdwind.inspire.medical.diary.sqlLite.dao.ChatDao;
import com.birdwind.inspire.medical.diary.sqlLite.dao.ChatMemberDao;
import com.birdwind.inspire.medical.diary.sqlLite.dao.FriendDao;

import org.jetbrains.annotations.NotNull;

@Database(entities = {PatientResponse.Response.class, ChatResponse.Response.class, ChatMemberResponse.Response.class},
    version = 1)
@TypeConverters(DataConverter.class)
public abstract class DatabaseConfig extends RoomDatabase {

    public abstract FriendDao friendDao();

    public abstract ChatDao chatDao();

    public abstract ChatMemberDao chatMemberDao();

    private static final String DB_Name = Config.APP_DEFINE_NAME;

    private volatile static DatabaseConfig databaseConfig;

    public static DatabaseConfig getInstance(Context context) {
        if (databaseConfig == null) {
            databaseConfig = init(context);
        }
        return databaseConfig;
    }

    private static DatabaseConfig init(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), DatabaseConfig.class, DB_Name)
            .allowMainThreadQueries() // 允許主執行緒中查詢
            .fallbackToDestructiveMigration() // 資料庫升級直接放棄舊的資料
            .build();
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull @NotNull SupportSQLiteDatabase database) {

        }
    };

}
