package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputFieldPojo {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("defaultValue")
    @Expose
    public Object defaultValue;
    @SerializedName("maxLength")
    @Expose
    public Integer maxLength;
    @SerializedName("label")
    @Expose
    public String label;
    @SerializedName("optional")
    @Expose
    public Boolean optional;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("group")
    @Expose
    public Object group;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getGroup() {
        return group;
    }

    public void setGroup(Object group) {
        this.group = group;
    }
}
