package com.birdwind.init.sqlLite;

import com.birdwind.init.App;
import com.birdwind.init.base.Config;

import android.content.Context;

import androidx.room.Room;

public class DatabaseInitialize {
    private static Context context;

    private static DatabaseConfig databaseConfig;

    public DatabaseInitialize(Context context) {}

    public static DatabaseConfig getInstance() {
        Context context = App.getAppContext();
        if (databaseConfig == null) {
            databaseConfig =
                Room.databaseBuilder(context.getApplicationContext(), DatabaseConfig.class, Config.APP_DEFINE_NAME)
                    .allowMainThreadQueries() // 允許主執行緒中查詢
                    .build();
        }
        return databaseConfig;
    }
}
