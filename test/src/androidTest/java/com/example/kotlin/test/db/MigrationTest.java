package com.example.kotlin.test.db;

import androidx.room.Room;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {
    private static final String TEST_DB = "migration-test.db";
    @Rule
    public MigrationTestHelper helper;

    public MigrationTest() {
        // TODO: 2025/6/15  MigrationTestHelper
        this.helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(), AppDatabase.class.getCanonicalName(), new FrameworkSQLiteOpenHelperFactory());
    }

    // TODO: 2025/6/15  
    @Test
    public void migration_all() throws IOException {
        // create the early version of the database
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);
        db.close();

        // database chas the schema version, insert some dat using SQL queries
        AppDatabase appDb = Room.databaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(), AppDatabase.class, TEST_DB)
                .createFromAsset("database/test.db")
//                .addMigrations(AppDatabase.MIGRATION_1_to_2)
                .addMigrations(AppDatabase.MIGRATION_1_to_2, AppDatabase.MIGRATION_2_to_3)
                .build();

        appDb.getOpenHelper().getWritableDatabase();
        appDb.close();
    }
}
