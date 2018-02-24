package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomfieldsPojo {
    public ArrayList<CustomFieldPojo> getCustomFieldPojos() {
        return customFieldPojos;
    }

    public void setCustomFieldPojos(ArrayList<CustomFieldPojo> customFieldPojos) {
        this.customFieldPojos = customFieldPojos;
    }

    @SerializedName("customfieldProductPojo")
    ArrayList<CustomFieldPojo> customFieldPojos;
}
