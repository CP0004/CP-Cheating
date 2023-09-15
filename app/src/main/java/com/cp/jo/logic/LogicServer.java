package com.cp.jo.logic;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.cp.jo.callback.CallbackServer;
import com.cp.jo.enums.CallbackRequest;
import com.cp.jo.model.ModelServer;
import com.cp.jo.tool.LocalData;
import com.cp.jo.tool.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

@SuppressWarnings("unchecked")
public class LogicServer {

    private Context context;

    public LogicServer(Context context) {
        this.context = context;

    }

    public MutableLiveData<CallbackServer> getDateServer(String email, String Password) {
        MutableLiveData<CallbackServer> callback = new MutableLiveData<>();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, Password).
                addOnSuccessListener(authResult -> {
                    FirebaseFirestore.getInstance().collection(LocalData.COLLECTION_SERVER).get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                    QueryDocumentSnapshot data = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                                    int state = data.getDouble(LocalData.SERVER_STATE).intValue();
                                    if (state != CallbackRequest.DOWN.ordinal())
                                        callback.setValue(new CallbackServer(CallbackRequest.DOWN.ordinal(), null, new ModelServer()));
                                    else if (state == CallbackRequest.MAINTENANCE.ordinal())
                                        callback.setValue(new CallbackServer(CallbackRequest.MAINTENANCE.ordinal(), null, new ModelServer()));
                                    else if (state == CallbackRequest.SUCCESSFUL.ordinal()) {
                                        if (Tools.getVersion(context).equals(data.getString(LocalData.SERVER_VERSION))) {
                                            callback.setValue(new CallbackServer(CallbackRequest.SUCCESSFUL.ordinal(), null, new ModelServer(
                                                    (List<String>) data.get(LocalData.SERVER_LINK_FILES),
                                                    data.getString(LocalData.SERVER_LINK_GITHUB),
                                                    data.getString(LocalData.SERVER_LINK_INSTAGRAM),
                                                    data.getString(LocalData.SERVER_LINK_LOGO),
                                                    data.getString(LocalData.SERVER_LINK_TELEGRAM),
                                                    data.getString(LocalData.SERVER_LINK_TIKTOK),
                                                    data.getString(LocalData.SERVER_LINK_WEB),
                                                    data.getString(LocalData.SERVER_NEWS),
                                                    data.getDouble(LocalData.SERVER_STATE).intValue(),
                                                    data.getString(LocalData.SERVER_VERSION)

                                                    //If you correct the errors, congratulations to you. You are a human being who can be relied upon. Contact me https://t.me/cp_developer
                                            )));
                                        } else
                                            callback.setValue(new CallbackServer(CallbackRequest.UPDATE.ordinal(), null, new ModelServer()));
                                    } else
                                        callback.setValue(new CallbackServer(CallbackRequest.EXCEPTION.ordinal(), null, new ModelServer()));
                                } else {
                                    if (task.getException() != null)
                                        callback.setValue(new CallbackServer(CallbackRequest.EXCEPTION.ordinal(), task.getException().getMessage(), new ModelServer()));
                                    else
                                        callback.setValue(new CallbackServer(CallbackRequest.ERROR.ordinal(), null, new ModelServer()));
                                }
                            });
                }).addOnFailureListener(errorAuth -> {
                    callback.setValue(new CallbackServer(CallbackRequest.EXCEPTION.ordinal(), errorAuth.getMessage(), new ModelServer()));
                });
        return callback;
    }

}
