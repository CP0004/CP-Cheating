package com.cp.jo.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.cp.jo.callback.CallbackKey;
import com.cp.jo.callback.CallbackProtection;
import com.cp.jo.callback.CallbackServer;
import com.cp.jo.model.Repository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<CallbackServer> getDateServer;
    public MutableLiveData<CallbackKey> getDateKey;
    public MutableLiveData<CallbackProtection> getDateProtection;
    public MutableLiveData<Boolean> getDateDownload;

    private final Application context;
    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public void initGetDataServer(String email, String password, boolean isRefresh) {
        if (getDateServer == null || isRefresh) {
            repository = Repository.getInstance(context);
            getDateServer = repository.getDateServer(email, password);
        }
    }

    public void initGetDateKey(String key, boolean isRefresh) {
        if (getDateKey == null || isRefresh) {
            repository = Repository.getInstance(context);
            getDateKey = repository.getDateKey(key.trim());
        }
    }

    public void initGetDateProtection(boolean isRefresh) {
        if (getDateProtection == null || isRefresh) {
            repository = Repository.getInstance(context);
            getDateProtection = repository.getDateProtection();
        }
    }

    public void initGetDateDownload(List<String> urls, boolean isRefresh) {
        if (getDateDownload == null || isRefresh) {
            repository = Repository.getInstance(context);
            getDateDownload = repository.getDateDownload(urls);
        }
    }
}
