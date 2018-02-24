package com.gehostingv2.gesostingv2iptvbilling.model.database;

public class PasswordDBModel {
    private int id;
    private String userDetail;
    private String userPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(String userDetail) {
        this.userDetail = userDetail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public PasswordDBModel(){}
    public PasswordDBModel(String userDetail, String userPassword) {
        this.userDetail = userDetail;
        this.userPassword = userPassword;
    }

}
