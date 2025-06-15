package com.example.kotlin.test.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.nio.file.attribute.FileAttribute;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// error: annotation value must be a class literal @Database(entities = (Menu.class), version = 1)

/**
 * exportSchema = true =>
 * https://stackoverflow.com/questions/44322178/room-schema-export-directory-is-not-provided-to-the-annotation-processor-so-we
 * javaCompileOptions {
 * // used by Room, to test migrations
 * annotationProcessorOptions {
 * arguments += ["room.schemaLocation": "$projectDir/schemas".toString()]
 * }
 * }
 */
@Database(entities = {Menu.class, PageData.class}, /*views = {MenuPageDataDetails.class},*/ version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";

    public abstract MenuDao menuDao();

    public abstract PageDataDao pageDataDao();

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
                    // https://programmerah.com/room-db-error-appdatabase_impl-does-not-exist-how-to-solve-56474/
                    // //    annotationProcessor libs.androidx.room.compiler
                    // changed to
                    //    annotationProcessor 'androidx.room:room-compiler:2.7.1'
                    //    kapt 'androidx.room:room-compiler:2.7.1'
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "test.db")
                            .addCallback(roomDatabaseCallback)
                            // 迁移数据库
//                            .addMigrations(MIGRATION_1_to_2)
                            // false - crash app> java.lang.IllegalStateException: Migration didn't properly handle: menu(com.example.kotlin.test.db.Menu)
                            // true = //迁移数据库如果发生错误，将会删除数据，而不是发生崩溃
                            .fallbackToDestructiveMigration(false)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_to_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
//            super.migrate(db);
            Log.e(TAG, "migrate: SupportSQLiteDatabase");
            // db.execSQL("ALTER TABLE menu ADD COLUMN country TEXT ");

            //     android.database.sqlite.SQLiteException: table aaaa has 4 columns but 3 values were supplied (code 1 SQLITE_ERROR[1]): , while compiling: insert into aaaa select code  ,menu_title ,menu_type from menu
            db.execSQL("CREATE TABLE aaaa(code INTEGER PRIMARY KEY,menu_title text,menu_type text,country text)");
            db.execSQL("INSERT INTO aaaa SELECT code,menu_title,menu_type from menu");
            db.execSQL("DROP TABLE menu");
            db.execSQL("alter table  aaaa rename to menu");

        }

        @Override
        public void migrate(@NonNull SQLiteConnection connection) {
            super.migrate(connection);
            Log.e(TAG, "migrate: SQLiteConnection");
        }
    };


    // To delete all content and populate the database when the app is installed, you create a RoomDatabase.Callback and override onCreate().

    public static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SQLiteConnection connection) {
            super.onCreate(connection);
            Log.e(TAG, "onCreate: SQLiteConnection");
        }

        @Override
        public void onOpen(@NonNull SQLiteConnection connection) {
            super.onOpen(connection);
            Log.e(TAG, "onCreate: SQLiteConnection");
        }

        @Override
        public void onDestructiveMigration(@NonNull SQLiteConnection connection) {
            super.onDestructiveMigration(connection);
            Log.e(TAG, "onDestructiveMigration: SQLiteConnection");
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.e(TAG, "onCreate: SupportSQLiteDatabase");

            // If you want to keep data through app restarts, comment out the following block
//            databaseExecutorService.execute(() -> {
//                // / Populate the database in the background.
//                // If you want to start with more words, just add them.
//
//                // delete all the menus
//                MenuDao dao = INSTANCE.menuDao();
//                dao.deleteAll();
//
//                // insert new menus
//                dao.insert(new Menu(1, "Hello", "native"));
//                dao.insert(new Menu(2, "World", "React"));
//            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.e(TAG, "onOpen: SupportSQLiteDatabase");
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
            Log.e(TAG, "onDestructiveMigration: SupportSQLiteDatabase");
        }
    };
}