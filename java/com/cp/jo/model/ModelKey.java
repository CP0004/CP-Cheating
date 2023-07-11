package com.cp.jo.model;

public class ModelKey {

    private String adminUid;
    private long dateCreate;
    private long dateExpiration;
    private long dateUse;
    private int hourKey;
    private boolean isPremium;
    private String key;
    private String userUid;
    private String nameDevise;

    public ModelKey() {
    }

    public ModelKey(String adminUid, long dateCreate, long dateExpiration, long dateUse, int hourKey, boolean isPremium, String key, String userUid, String nameDevise) {
        this.adminUid = adminUid;
        this.dateCreate = dateCreate;
        this.dateExpiration = dateExpiration;
        this.dateUse = dateUse;
        this.hourKey = hourKey;
        this.isPremium = isPremium;
        this.key = key;
        this.userUid = userUid;
        this.nameDevise = nameDevise;
    }

    public String getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(String adminUid) {
        this.adminUid = adminUid;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public long getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(long dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public long getDateUse() {
        return dateUse;
    }

    public void setDateUse(long dateUse) {
        this.dateUse = dateUse;
    }

    public int getHourKey() {
        return hourKey;
    }

    public void setHourKey(int hourKey) {
        this.hourKey = hourKey;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getNameDevise() {
        return nameDevise;
    }

    public void setNameDevise(String nameDevise) {
        this.nameDevise = nameDevise;
    }

    @Override
    public String toString() {
        return "ModelKey{" +
                "adminUid='" + adminUid + '\'' +
                ", dateCreate=" + dateCreate +
                ", dateExpiration=" + dateExpiration +
                ", dateUse=" + dateUse +
                ", hourKey=" + hourKey +
                ", isPremium=" + isPremium +
                ", key='" + key + '\'' +
                ", userUid='" + userUid + '\'' +
                ", nameDevise='" + nameDevise + '\'' +
                '}';
    }
}
