package com.gehostingv2.gesostingv2iptvbilling.model.database;

public class DatabaseUpdatedStatusDBModel {
    private int idAuto;
    private String dbUpadatedStatusState;
    private String dbLastUpdatedDate;
    private String dbCategory;
    private String dbCategoryID;

    public String getDbCategory() {
        return dbCategory;
    }

    public void setDbCategory(String dbCategory) {
        this.dbCategory = dbCategory;
    }

    public String getGetDbCategoryID() {
        return dbCategoryID;
    }

    public void setDbCategoryID(String dbCategoryID) {
        this.dbCategoryID = dbCategoryID;
    }



    public DatabaseUpdatedStatusDBModel(){}
    public DatabaseUpdatedStatusDBModel(
                                        String dbUpadatedStatusState,
                                        String dbLastUpdatedDate,
                                        String dbCategory,
                                        String dbCategoryID){
        this.dbUpadatedStatusState = dbUpadatedStatusState;
        this.dbLastUpdatedDate = dbLastUpdatedDate;
        this.dbCategory = dbCategory;
        this.dbCategoryID = dbCategoryID;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public String getDbUpadatedStatusState() {
        return dbUpadatedStatusState;
    }

    public void setDbUpadatedStatusState(String dbUpadatedStatusState) {
        this.dbUpadatedStatusState = dbUpadatedStatusState;
    }

    public String getDbLastUpdatedDate() {
        return dbLastUpdatedDate;
    }

    public void setDbLastUpdatedDate(String dbLastUpdatedDate) {
        this.dbLastUpdatedDate = dbLastUpdatedDate;
    }
}
