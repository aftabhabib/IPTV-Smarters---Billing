package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartmentDetailPojo {
    @SerializedName("id")
    @Expose
    private String departmentId;
    @SerializedName("name")
    @Expose
    private String departmentName;
    @SerializedName("awaitingreply")
    @Expose
    private String awaitingreply;
    @SerializedName("opentickets")
    @Expose
    private String opentickets;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAwaitingreply() {
        return awaitingreply;
    }

    public void setAwaitingreply(String awaitingreply) {
        this.awaitingreply = awaitingreply;
    }

    public String getOpentickets() {
        return opentickets;
    }

    public void setOpentickets(String opentickets) {
        this.opentickets = opentickets;
    }
}
