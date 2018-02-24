package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.DepartmentPojo;

public class GetDepartmentCallback extends CommonResponseCallback{
    @SerializedName("departments")
    @Expose
    private DepartmentPojo departments;

    public DepartmentPojo getDepartments() {
        return departments;
    }

    public void setDepartments(DepartmentPojo departments) {
        this.departments = departments;
    }
}
