package com.cp.jo.callback;

import com.cp.jo.model.ModelProtection;

import java.util.List;

public class CallbackProtection {
    private int state;
    private String error;
    private List<ModelProtection> modelProtection;

    public CallbackProtection() {
    }

    public CallbackProtection(int state, String error, List<ModelProtection> modelProtection) {
        this.state = state;
        this.error = error;
        this.modelProtection = modelProtection;
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

    public List<ModelProtection> getModeProtection() {
        return modelProtection;
    }

    public void setModeProtection(List<ModelProtection> modelProtection) {
        this.modelProtection = modelProtection;
    }

    @Override
    public String toString() {
        return "CallbackProtection{" +
                "state=" + state +
                ", error='" + error + '\'' +
                ", modelProtection=" + modelProtection +
                '}';
    }
}
