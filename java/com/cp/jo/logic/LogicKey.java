package com.cp.jo.logic;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.cp.jo.callback.CallbackKey;
import com.cp.jo.enums.CallbackRequest;
import com.cp.jo.model.ModelKey;
import com.cp.jo.tool.LocalData;
import com.cp.jo.tool.Preference;
import com.cp.jo.tool.Tools;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class LogicKey {

    private Context context;

    public LogicKey(Context context) {
        this.context = context;

    }

    public MutableLiveData<CallbackKey> getDateKey(String key) {
        MutableLiveData<CallbackKey> callback = new MutableLiveData<>();
        FirebaseFirestore.getInstance().collection(LocalData.COLLECTION_KEY).whereEqualTo(LocalData.KEY_KEY, key.trim()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        QueryDocumentSnapshot data = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);

                        long dateExpiration = data.getDouble(LocalData.KEY_DATE_USE).longValue();
                        long dateUse = data.getDouble(LocalData.KEY_DATE_USE).longValue();
                        String nameDevise = data.getString(LocalData.KEY_NAME_DEVICE);
                        String userUid = data.getString(LocalData.KEY_USER_UID);

                        if (dateUse > 0 &&  //-- RESET KEY
                                dateExpiration > 0 &&
                                nameDevise != null &&
                                userUid != null &&
                                Tools.millisToNumber(Tools.timeMillis()) < Tools.millisToNumber(dateExpiration) &&
                                Tools.differenceTwoDates(Tools.timeMillis(), dateExpiration) > 0) {

                            Map<String, Object> requestData = new HashMap<>();
                            requestData.put(LocalData.KEY_USER_UID, Tools.getUuidDevice(context));
                            requestData.put(LocalData.KEY_NAME_DEVICE, Tools.getNameDevices());
                            FirebaseFirestore.getInstance().collection(LocalData.COLLECTION_KEY).document(task.getResult().getDocuments().get(0).getId()).update(requestData)
                                    .addOnSuccessListener(unused -> {
                                        Preference.setValue(context, LocalData.KEY_KEY, key.trim());
                                        callback.setValue(new CallbackKey(CallbackRequest.DONE_KEY.ordinal(), null, new ModelKey()));
                                    }).addOnFailureListener(errorAdd -> {
                                        callback.setValue(new CallbackKey(CallbackRequest.EXCEPTION.ordinal(), errorAdd.getMessage(), new ModelKey()));
                                    });

                        } else if (dateUse > 0 &&  //-- USE KEY
                                dateExpiration > 0 &&
                                nameDevise == null &&
                                userUid == null &&
                                Tools.millisToNumber(Tools.timeMillis()) < Tools.millisToNumber(dateExpiration) &&
                                Tools.differenceTwoDates(Tools.timeMillis(), dateExpiration) > 0) {

                            if (data.getBoolean(LocalData.KEY_IS_PREMIUM) && !data.getString(LocalData.KEY_USER_UID).equals(userUid) && !data.getString(LocalData.KEY_NAME_DEVICE).equals(nameDevise)) {
                                callback.setValue(new CallbackKey(CallbackRequest.USED.ordinal(), null, new ModelKey()));
                                return;
                            }

                            callback.setValue(new CallbackKey(CallbackRequest.SUCCESSFUL.ordinal(), null, new ModelKey(
                                    data.getString(LocalData.KEY_ADMIN_UID),
                                    data.getDouble(LocalData.KEY_DATE_CREATE).longValue(),
                                    data.getDouble(LocalData.KEY_DATE_EXPIRATION).longValue(),
                                    data.getDouble(LocalData.KEY_DATE_USE).longValue(),
                                    data.getDouble(LocalData.KEY_HOUR_KEY).intValue(),
                                    data.getBoolean(LocalData.KEY_IS_PREMIUM),
                                    data.getString(LocalData.KEY_KEY),
                                    data.getString(LocalData.KEY_USER_UID),
                                    data.getString(LocalData.KEY_NAME_DEVICE)
                            )));

                            Tools.setPremium(data.getBoolean(LocalData.KEY_IS_PREMIUM));
                            Preference.setValue(context, LocalData.KEY_KEY, key.trim());

                        } else if (dateUse == 0 &&   //-- NOT USE KEY
                                dateExpiration == 0 &&
                                nameDevise == null &&
                                userUid == null) {

                            Map<String, Object> requestData = new HashMap<>();
                            requestData.put(LocalData.KEY_DATE_EXPIRATION, Tools.addHour(data.getDouble(LocalData.KEY_HOUR_KEY).intValue()));
                            requestData.put(LocalData.KEY_DATE_USE, Tools.timeMillis());
                            requestData.put(LocalData.KEY_USER_UID, Tools.getUuidDevice(context));
                            requestData.put(LocalData.KEY_NAME_DEVICE, Tools.getNameDevices());
                            FirebaseFirestore.getInstance().collection(LocalData.COLLECTION_KEY).document(task.getResult().getDocuments().get(0).getId()).update(requestData)
                                    .addOnSuccessListener(unused -> {
                                        Preference.setValue(context, LocalData.KEY_KEY, key.trim());
                                        callback.setValue(new CallbackKey(CallbackRequest.DONE_KEY.ordinal(), null, new ModelKey()));
                                    }).addOnFailureListener(errorAdd -> {
                                        callback.setValue(new CallbackKey(CallbackRequest.EXCEPTION.ordinal(), errorAdd.getMessage(), new ModelKey()));
                                    });

                        } else if (dateUse > 0 &&//-- DELETE KEY
                                dateExpiration > 0 &&
                                nameDevise != null &&
                                userUid != null &&
                                Tools.millisToNumber(Tools.timeMillis()) >= Tools.millisToNumber(dateExpiration) &&
                                Tools.differenceTwoDates(Tools.timeMillis(), dateExpiration) <= 0) {

                            callback.setValue(new CallbackKey(CallbackRequest.EXPIRATION.ordinal(), null, new ModelKey()));
                        }
                    } else {
                        if (task.getException() != null)
                            callback.setValue(new CallbackKey(CallbackRequest.EXCEPTION.ordinal(), task.getException().getMessage(), new ModelKey()));
                        else
                            callback.setValue(new CallbackKey(CallbackRequest.ERROR.ordinal(), null, new ModelKey()));
                    }
                });

        return callback;
    }

}
