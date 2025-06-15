package com.example.kotlin.test.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kotlin.test.db.Menu;
import com.example.kotlin.test.db.MenuPageData;
import com.example.kotlin.test.db.PageData;
import com.example.kotlin.test.respository.PageDataRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public class PageDataViewModule extends AndroidViewModel {
    PageDataRepository repository;

    public PageDataViewModule(@NonNull Application application) {
        super(application);
        repository = new PageDataRepository(application);
    }

    public LiveData<List<PageData>> getAll() {
        return repository.getAll();
    }

    public LiveData<List<String>> getTitle(int menuCode) {
        return repository.getTitle(menuCode);
    }

    public List<String> getTitle2(int menuCode) {
        return repository.getTitle2(menuCode);
    }


    public void insert(PageData... data) {
        repository.insert(data);
    }

    public Map<Menu, List<PageData>> loadMenuAndPageData() {
        return repository.loadMenuAndPageData();
    }

    public List<MenuPageData> loadMenuPageData() {
        return repository.loadMenuPageData();
    }

    public Single<List<MenuPageData>> loadMenuPageData2() {
        return repository.loadMenuPageData2();
    }
}
