package com.cp.jo.callback;

import com.cp.jo.model.ModelServer;

public class CallbackServer {
    private int state;
    private String error;
    private ModelServer modelServer;

    public CallbackServer() {
    }

    public CallbackServer(int state, String error, ModelServer modelServer) {
        this.state = state;
        this.error = error;
        this.modelServer = modelServer;
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

    public ModelServer getModelServer() {
        return modelServer;
    }

    public void setModelServer(ModelServer modelServer) {
        this.modelServer = modelServer;
    }

    @Override
    public String toString() {
        return "CallbackServer{" +
                "state=" + state +
                ", error='" + error + '\'' +
                ", modelServer=" + modelServer +
                '}';
    }
}
