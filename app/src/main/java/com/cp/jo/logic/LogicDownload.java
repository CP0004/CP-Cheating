package com.cp.jo.logic;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.cp.jo.enums.CallbackRequest;
import com.cp.jo.tool.LocalData;
import com.cp.jo.tool.ReadFilesUrl;

import java.util.List;

public class LogicDownload {

    private Context context;

    private int successfulDownload;

    public LogicDownload(Context context) {
        this.context = context;

    }

    public MutableLiveData<Boolean> getDateDownload(List<String> urls) {
        MutableLiveData<Boolean> callback = new MutableLiveData<>();
        successfulDownload = 0;
        if (!urls.isEmpty()) {
            for (int i = 0; i < urls.size(); i++) {
                new ReadFilesUrl(context,
                        urls.get(i),
                        LocalData.STATIC_LOCAL_PATH_DOWNLOAD,
                        urls.get(i).split("/")[urls.get(i).split("/").length - 1].split("\\?alt")[0],
                        (state, error, update) -> {
                            if (state == CallbackRequest.SUCCESSFUL.ordinal()) {
                                successfulDownload++;
                                if (successfulDownload == urls.size())
                                    callback.setValue(true);
                            } else
                                callback.setValue(false);

                        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            }
        } else
            callback.setValue(false);
        return callback;
    }

}
