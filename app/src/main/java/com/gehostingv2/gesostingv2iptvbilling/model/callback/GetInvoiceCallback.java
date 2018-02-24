package com.gehostingv2.gesostingv2iptvbilling.model.callback;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.InvoiceItemListPojo;

public class GetInvoiceCallback extends CommonResponseCallback{

    @SerializedName("invoiceid")
    @Expose
    public String invoiceid;
    @SerializedName("invoicenum")
    @Expose
    public String invoicenum;
    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("duedate")
    @Expose
    public String duedate;
    @SerializedName("datepaid")
    @Expose
    public String datepaid;
    @SerializedName("lastcaptureattempt")
    @Expose
    public String lastcaptureattempt;
    @SerializedName("subtotal")
    @Expose
    public String subtotal;
    @SerializedName("credit")
    @Expose
    public String credit;
    @SerializedName("tax")
    @Expose
    public String tax;
    @SerializedName("tax2")
    @Expose
    public String tax2;
    @SerializedName("total")
    @Expose
    public String total;
    @SerializedName("balance")
    @Expose
    public String balance;
    @SerializedName("taxrate")
    @Expose
    public String taxrate;
    @SerializedName("taxrate2")
    @Expose
    public String taxrate2;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("paymentmethod")
    @Expose
    public String paymentmethod;
    @SerializedName("notes")
    @Expose
    public String notes;
    @SerializedName("ccgateway")
    @Expose
    public Boolean ccgateway;
    @SerializedName("items")
    @Expose
    public InvoiceItemListPojo items;

    public String getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(String invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getInvoicenum() {
        return invoicenum;
    }

    public void setInvoicenum(String invoicenum) {
        this.invoicenum = invoicenum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getDatepaid() {
        return datepaid;
    }

    public void setDatepaid(String datepaid) {
        this.datepaid = datepaid;
    }

    public String getLastcaptureattempt() {
        return lastcaptureattempt;
    }

    public void setLastcaptureattempt(String lastcaptureattempt) {
        this.lastcaptureattempt = lastcaptureattempt;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTax2() {
        return tax2;
    }

    public void setTax2(String tax2) {
        this.tax2 = tax2;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(String taxrate) {
        this.taxrate = taxrate;
    }

    public String getTaxrate2() {
        return taxrate2;
    }

    public void setTaxrate2(String taxrate2) {
        this.taxrate2 = taxrate2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getCcgateway() {
        return ccgateway;
    }

    public void setCcgateway(Boolean ccgateway) {
        this.ccgateway = ccgateway;
    }

    public InvoiceItemListPojo getItems() {
        return items;
    }

    public void setItems(InvoiceItemListPojo items) {
        this.items = items;
    }

//    public String getTransactions() {
//        return transactions;
//    }
//
//    public void setTransactions(String transactions) {
//        this.transactions = transactions;
//    }
}
