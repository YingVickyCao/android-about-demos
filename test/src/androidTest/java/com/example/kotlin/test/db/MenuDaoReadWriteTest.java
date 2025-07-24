package com.example.kotlin.test.db;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kotlin.test.data.db.AppDatabase;
import com.example.kotlin.test.data.db.Menu;
import com.example.kotlin.test.data.db.MenuDao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MenuDaoReadWriteTest {
    private MenuDao menuDao;
    private AppDatabase db;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        menuDao = db.menuDao();
    }

    @After
    public void closeDb() {
        db.close();
    }


    @Test
    public void writeMenuAndReadInList() throws Exception {
        Menu menu = new Menu(1, "menu_1", "native");
        menuDao.insert(menu);
        Menu r = menuDao.findByCode(1);
        Assert.assertEquals(1, r.code);
    }
}


