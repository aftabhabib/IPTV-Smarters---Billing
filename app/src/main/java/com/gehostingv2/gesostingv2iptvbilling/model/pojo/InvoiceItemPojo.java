package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceItemPojo {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("relid")
    @Expose
    public String relid;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("taxed")
    @Expose
    public String taxed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelid() {
        return relid;
    }

    public void setRelid(String relid) {
        this.relid = relid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTaxed() {
        return taxed;
    }

    public void setTaxed(String taxed) {
        this.taxed = taxed;
    }
}
