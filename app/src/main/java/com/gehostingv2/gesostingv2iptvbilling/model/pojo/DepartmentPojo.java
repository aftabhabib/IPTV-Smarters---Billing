package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DepartmentPojo {

    @SerializedName("department")
    @Expose
    private ArrayList<DepartmentDetailPojo> department = null;

    public ArrayList<DepartmentDetailPojo> getDepartment() {
        return department;
    }

    public void setDepartment(ArrayList<DepartmentDetailPojo> department) {
        this.department = department;
    }
}
