package com.cp.jo.callback;

import com.cp.jo.model.ModelKey;

public class CallbackKey {
    private int state;
    private String error;
    private ModelKey modelKey;

    public CallbackKey() {
    }

    public CallbackKey(int state, String error, ModelKey modelKey) {
        this.state = state;
        this.error = error;
        this.modelKey = modelKey;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ModelKey getModeKey() {
        return modelKey;
    }

    public void setModeKey(ModelKey modelKey) {
        this.modelKey = modelKey;
    }

    @Override
    public String toString() {
        return "CallbackKey{" +
                "state=" + state +
                ", error='" + error + '\'' +
                ", modelKey=" + modelKey +
                '}';
    }
}
