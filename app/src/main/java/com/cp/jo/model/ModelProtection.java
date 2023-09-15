package com.cp.jo.model;

public class ModelProtection {

    private String commandShell;
    private long dateCreate;
    private boolean isPremium;
    private int ModeView;
    private String name;

    public ModelProtection() {
    }

    public ModelProtection(String commandShell, long dateCreate, boolean isPremium, int modeView, String name) {
        this.commandShell = commandShell;
        this.dateCreate = dateCreate;
        this.isPremium = isPremium;
        ModeView = modeView;
        this.name = name;
    }

    public String getCommandShell() {
        return commandShell;
    }

    public void setCommandShell(String commandShell) {
        this.commandShell = commandShell;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public int getModeView() {
        return ModeView;
    }

    public void setModeView(int modeView) {
        ModeView = modeView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ModelProtection{" +
                "commandShell='" + commandShell + '\'' +
                ", dateCreate=" + dateCreate +
                ", isPremium=" + isPremium +
                ", ModeView=" + ModeView +
                ", name='" + name + '\'' +
                '}';
    }
}
