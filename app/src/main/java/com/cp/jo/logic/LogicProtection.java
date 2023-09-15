package com.cp.jo.logic;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.cp.jo.callback.CallbackProtection;
import com.cp.jo.enums.CallbackRequest;
import com.cp.jo.model.ModelProtection;
import com.cp.jo.tool.LocalData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LogicProtection {

    private Context context;

    public LogicProtection(Context context) {
        this.context = context;

    }

    public MutableLiveData<CallbackProtection> getDateProtection() {
        MutableLiveData<CallbackProtection> callback = new MutableLiveData<>();
        FirebaseFirestore.getInstance().collection(LocalData.COLLECTION_PROTECTION).orderBy(LocalData.PROTECTION_MODE_VIEW, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        List<ModelProtection> modelProtection = new ArrayList<>();
                        for (QueryDocumentSnapshot data : task.getResult()) {
                            modelProtection.add(new ModelProtection(
                                    data.getString(LocalData.PROTECTION_NAME),
                                    data.getDouble(LocalData.PROTECTION_DATE_CREATE).longValue(),
                                    data.getBoolean(LocalData.PROTECTION_IS_PREMIUM),
                                    data.getDouble(LocalData.PROTECTION_MODE_VIEW).intValue(),
                                    data.getString(LocalData.PROTECTION_NAME)
                            ));
                        }
                        callback.setValue(new CallbackProtection(CallbackRequest.SUCCESSFUL.ordinal(), null, modelProtection));
                    } else {
                        if (task.getException() != null)
                            callback.setValue(new CallbackProtection(CallbackRequest.EXCEPTION.ordinal(), task.getException().getMessage(), new ArrayList<>()));
                        else
                            callback.setValue(new CallbackProtection(CallbackRequest.ERROR.ordinal(), null, new ArrayList<>()));
                    }
                });
        return callback;
    }

}
