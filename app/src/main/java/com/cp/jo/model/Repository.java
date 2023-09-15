package com.cp.jo.model;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.cp.jo.callback.CallbackKey;
import com.cp.jo.callback.CallbackProtection;
import com.cp.jo.callback.CallbackServer;
import com.cp.jo.logic.LogicDownload;
import com.cp.jo.logic.LogicKey;
import com.cp.jo.logic.LogicProtection;
import com.cp.jo.logic.LogicServer;

import java.util.List;

public class Repository {
    @SuppressLint("StaticFieldLeak")
    private static Repository instance;
    @SuppressLint("StaticFieldLeak")
    private static Application context;

    public static Repository getInstance(Application context) {
        Repository.context = context;
        if (instance == null) instance = new Repository();
        return instance;
    }

    public MutableLiveData<CallbackServer> getDateServer(String email, String Password) {
        return new LogicServer(context).getDateServer(email, Password);
    }

    public MutableLiveData<CallbackKey> getDateKey(String key) {
        return new LogicKey(context).getDateKey(key);
    }

    public MutableLiveData<CallbackProtection> getDateProtection() {
        return new LogicProtection(context).getDateProtection();
    }

    public MutableLiveData<Boolean> getDateDownload(List<String> urls) {
        return new LogicDownload(context).getDateDownload(urls);
    }

}
