package com.gehostingv2.gesostingv2iptvbilling.model.database;

public class PasswordStatusDBModel {
    private int idPaswordStaus;

    public int getIdPaswordStaus() {
        return idPaswordStaus;
    }

    public void setIdPaswordStaus(int idPaswordStaus) {
        this.idPaswordStaus = idPaswordStaus;
    }

    public String getPasswordStatusCategoryId() {
        return passwordStatusCategoryId;
    }

    public void setPasswordStatusCategoryId(String passwordStatusCategoryId) {
        this.passwordStatusCategoryId = passwordStatusCategoryId;
    }

    public String getPasswordStatusUserDetail() {
        return passwordStatusUserDetail;
    }

    public void setPasswordStatusUserDetail(String passwordStatusUserDetail) {
        this.passwordStatusUserDetail = passwordStatusUserDetail;
    }

    public String getPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(String passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    private String passwordStatusCategoryId;
    private String passwordStatusUserDetail;
    private String passwordStatus;
    public PasswordStatusDBModel(){}
    public PasswordStatusDBModel(String passwordStatusCategoryId,
                                 String passwordStatusUserDetail,
                                 String passwordStatus) {
        this.passwordStatusCategoryId = passwordStatusCategoryId;
        this.passwordStatusUserDetail = passwordStatusUserDetail;
        this.passwordStatus = passwordStatus;
    }
}
