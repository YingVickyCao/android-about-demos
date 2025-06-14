package com.example.kotlin.test.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kotlin.test.db.PageData;
import com.example.kotlin.test.respository.PageDataRepository;

import java.util.List;

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
}
