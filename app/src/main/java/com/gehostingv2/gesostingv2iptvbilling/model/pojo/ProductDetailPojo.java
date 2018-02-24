package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductDetailPojo implements Parcelable {
    protected ProductDetailPojo(Parcel in) {
        name = in.readString();
        billingCycle = in.readString();
        status = in.readString();
        recurringAmount = in.readString();
        registarationDate = in.readString();
        paymentMethod = in.readString();
        firstPaymentAmount = in.readString();
        paymentMethodName = in.readString();
    }

    public static final Creator<ProductDetailPojo> CREATOR = new Creator<ProductDetailPojo>() {
        @Override
        public ProductDetailPojo createFromParcel(Parcel in) {
            return new ProductDetailPojo(in);
        }

        @Override
        public ProductDetailPojo[] newArray(int size) {
            return new ProductDetailPojo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    private String name;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @SerializedName("id")
    private String serviceId;
    @SerializedName("billingcycle")
    private String billingCycle;
    @SerializedName("nextduedate")
    private String nextDueDate;
    @SerializedName("status")
    private String status;
    @SerializedName("recurringamount")
    private String recurringAmount;
    @SerializedName("regdate")
    private String registarationDate;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @SerializedName("groupname")
    private String groupName;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @SerializedName("domain")
    private String domain;

    public CustomfieldsPojo getCustomFields() {
        return customFields;
    }

    public void setCustomFields(CustomfieldsPojo customFields) {
        this.customFields = customFields;
    }

    @SerializedName("customfields")
    private CustomfieldsPojo  customFields;


    @SerializedName("username")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    @SerializedName("password")
    private String password;
    @SerializedName("serverip")
    private String serverIp;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @SerializedName("pid")
    private String productId;

    public String getRegistarationDate() {
        return registarationDate;
    }

    public void setRegistarationDate(String registarationDate) {
        this.registarationDate = registarationDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getFirstPaymentAmount() {
        return firstPaymentAmount;
    }

    public void setFirstPaymentAmount(String firstPaymentAmount) {
        this.firstPaymentAmount = firstPaymentAmount;
    }

    @SerializedName("paymentmethod")
    private  String paymentMethod;

    @SerializedName("paymentmethodname")
    private  String paymentMethodName;

    @SerializedName("firstpaymentamount")
    private String firstPaymentAmount;

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(String nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecurringAmount() {
        return recurringAmount;
    }

    public void setRecurringAmount(String recurringAmount) {
        this.recurringAmount = recurringAmount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(billingCycle);
        parcel.writeString(status);
        parcel.writeString(recurringAmount);
        parcel.writeString(registarationDate);
        parcel.writeString(paymentMethod);
        parcel.writeString(firstPaymentAmount);
        parcel.writeString(paymentMethodName);
    }
}
