package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.RepliesListPojo;


public class GetViewRequestReplyCallback extends CommonResponseCallback{

    @SerializedName("ticketid")
    @Expose
    public String ticketid;
    @SerializedName("tid")
    @Expose
    public String tid;
    @SerializedName("c")
    @Expose
    public String c;
    @SerializedName("deptid")
    @Expose
    public String deptid;
    @SerializedName("deptname")
    @Expose
    public String deptname;
    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("contactid")
    @Expose
    public String contactid;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("cc")
    @Expose
    public String cc;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("subject")
    @Expose
    public String subject;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("priority")
    @Expose
    public String priority;
    @SerializedName("admin")
    @Expose
    public String admin;
    @SerializedName("lastreply")
    @Expose
    public String lastreply;
    @SerializedName("flag")
    @Expose
    public String flag;
    @SerializedName("service")
    @Expose
    public String service;
    @SerializedName("replies")
    @Expose
    public RepliesListPojo replies;
    @SerializedName("notes")
    @Expose
    public String notes;


    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getLastreply() {
        return lastreply;
    }

    public void setLastreply(String lastreply) {
        this.lastreply = lastreply;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public RepliesListPojo getReplies() {
        return replies;
    }

    public void setReplies(RepliesListPojo replies) {
        this.replies = replies;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
