package com.hhp.huuphuoc372.ontapcacmondaicuong.model;

public class Subject {
    private int imgSubject;
    private String nameSubject;
    private String slogan;

    public int getImgSubject() {
        return imgSubject;
    }

    public void setImgSubject(int imgSubject) {
        this.imgSubject = imgSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Subject(int imgSubject, String nameSubject, String slogan) {
        this.imgSubject = imgSubject;
        this.nameSubject = nameSubject;
        this.slogan = slogan;
    }

    public Subject() {
    }
}
