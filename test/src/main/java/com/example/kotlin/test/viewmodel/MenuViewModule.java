package com.example.kotlin.test.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kotlin.test.data.db.Menu;
import com.example.kotlin.test.data.db.SimpleMenu;
import com.example.kotlin.test.data.respository.MenuRepository;

import java.util.List;

// TODO: 2025/6/14 import androidx.lifecycle.AndroidViewModel;
// TODO: 2025/6/14  import androidx.lifecycle.ViewModel; 
public class MenuViewModule extends AndroidViewModel {
    private MenuRepository repository;
    private final LiveData<List<Menu>> menus;

    public MenuViewModule(@NonNull Application application) {
        super(application);

        repository = new MenuRepository(application);
        menus = repository.getAllMenus();
    }

    public LiveData<List<Menu>> getMenus() {
        return menus;
    }

    public void insert(Menu menu) {
        repository.insert(menu);
    }

    public LiveData<SimpleMenu> findMenuSimple(String meuCode) {
        return repository.findMenuSimple(meuCode);
    }

    public LiveData<String> findMenuTitle(String menuCode) {
        return repository.findMenuTitle(menuCode);
    }

    public void delete(Menu menu) {
        repository.delete(menu);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // close cursor
    }
}
