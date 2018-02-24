package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyPojo {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("suffix")
    @Expose
    public String suffix;
    @SerializedName("format")
    @Expose
    public String format;
    @SerializedName("rate")
    @Expose
    public String rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
