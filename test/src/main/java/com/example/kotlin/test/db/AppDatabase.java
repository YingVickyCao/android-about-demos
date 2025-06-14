package com.example.kotlin.test.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// error: annotation value must be a class literal @Database(entities = (Menu.class), version = 1)
@Database(entities = {Menu.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MenuDao menuDao();

    // Define a singletone to avoid having multiple instances of database opened at the same time.
    private static volatile AppDatabase INSTANCE;
    // created an ExecutorService with a fixed thread pool that you will use to run database operations asynchronously on a background thread.
    private static final int NUMBERS_OF_THREAD = 4;

    // TODO: 2025/6/14 delay init
    public static final ExecutorService databaseExecutorService = Executors.newFixedThreadPool(NUMBERS_OF_THREAD);

    public static AppDatabase getDatabase(final Context context) {
        if (null == INSTANCE) {
            synchronized (AppDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "test")
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    // To delete all content and populate the database when the app is installed, you create a RoomDatabase.Callback and override onCreate().
}