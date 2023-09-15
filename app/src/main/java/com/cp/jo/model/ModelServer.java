package com.cp.jo.model;

import java.util.List;

public class ModelServer {
    private List<String> linkFiles;
    private String linkGithub;
    private String linkInstagram;
    private String linkLogo;
    private String linkTelegram;
    private String linkTiktok;
    private String linkWeb;
    private String news;
    private int state;
    private String version;

    public ModelServer() {
    }

    public ModelServer(List<String> linkFiles, String linkGithub, String linkInstagram, String linkLogo, String linkTelegram, String linkTiktok, String linkWeb, String news, int state, String version) {
        this.linkFiles = linkFiles;
        this.linkGithub = linkGithub;
        this.linkInstagram = linkInstagram;
        this.linkLogo = linkLogo;
        this.linkTelegram = linkTelegram;
        this.linkTiktok = linkTiktok;
        this.linkWeb = linkWeb;
        this.news = news;
        this.state = state;
        this.version = version;
    }

    public List<String> getLinkFiles() {
        return linkFiles;
    }

    public void setLinkFiles(List<String> linkFiles) {
        this.linkFiles = linkFiles;
    }

    public String getLinkGithub() {
        return linkGithub;
    }

    public void setLinkGithub(String linkGithub) {
        this.linkGithub = linkGithub;
    }

    public String getLinkInstagram() {
        return linkInstagram;
    }

    public void setLinkInstagram(String linkInstagram) {
        this.linkInstagram = linkInstagram;
    }

    public String getLinkLogo() {
        return linkLogo;
    }

    public void setLinkLogo(String linkLogo) {
        this.linkLogo = linkLogo;
    }

    public String getLinkTelegram() {
        return linkTelegram;
    }

    public void setLinkTelegram(String linkTelegram) {
        this.linkTelegram = linkTelegram;
    }

    public String getLinkTiktok() {
        return linkTiktok;
    }

    public void setLinkTiktok(String linkTiktok) {
        this.linkTiktok = linkTiktok;
    }

    public String getLinkWeb() {
        return linkWeb;
    }

    public void setLinkWeb(String linkWeb) {
        this.linkWeb = linkWeb;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ModelServer{" +
                "linkFiles=" + linkFiles +
                ", linkGithub='" + linkGithub + '\'' +
                ", linkInstagram='" + linkInstagram + '\'' +
                ", linkLogo='" + linkLogo + '\'' +
                ", linkTelegram='" + linkTelegram + '\'' +
                ", linkTiktok='" + linkTiktok + '\'' +
                ", linkWeb='" + linkWeb + '\'' +
                ", news='" + news + '\'' +
                ", state='" + state + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
