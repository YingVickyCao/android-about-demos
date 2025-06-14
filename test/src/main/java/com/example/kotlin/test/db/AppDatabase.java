package com.example.kotlin.test.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                    // Caused by: java.lang.RuntimeException: Cannot find implementation for com.example.kotlin.test.db.AppDatabase. AppDatabase_Impl does not exist. Is Room annotation processor correctly configured?
                    // Caused by: java.lang.ClassNotFoundException: Didn't find class "com.example.kotlin.test.db.AppDatabase_Impl"
                    // //    annotationProcessor libs.androidx.room.compiler
                    // changed to
                    //    annotationProcessor 'androidx.room:room-compiler:2.7.1'
                    //    kapt 'androidx.room:room-compiler:2.7.1'
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "test")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    // To delete all content and populate the database when the app is installed, you create a RoomDatabase.Callback and override onCreate().

    public static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SQLiteConnection connection) {
            super.onCreate(connection);
        }

        @Override
        public void onOpen(@NonNull SQLiteConnection connection) {
            super.onOpen(connection);
        }

        @Override
        public void onDestructiveMigration(@NonNull SQLiteConnection connection) {
            super.onDestructiveMigration(connection);
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts, comment out the following block
            databaseExecutorService.execute(() -> {
                // / Populate the database in the background.
                // If you want to start with more words, just add them.

                // delete all the menus
                MenuDao dao = INSTANCE.menuDao();
                dao.deleteAll();

                // insert new menus
                dao.insert(new Menu(1, "Hello", "native"));
                dao.insert(new Menu(2, "World", "React"));
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }

    };
}