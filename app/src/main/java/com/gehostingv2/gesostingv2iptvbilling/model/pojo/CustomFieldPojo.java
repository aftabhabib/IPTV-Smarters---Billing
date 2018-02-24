package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.SerializedName;

public class CustomFieldPojo {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslatedName() {
        return translatedName;
    }

    public void setTranslatedName(String translatedName) {
        this.translatedName = translatedName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @SerializedName("translated_name")
    String translatedName;
    @SerializedName("value")
    String value;
}
