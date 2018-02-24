package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketDetailPojo {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("tid")
    @Expose
    public String tid;
    @SerializedName("deptid")
    @Expose
    public String deptid;
    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("cc")
    @Expose
    public String cc;
    @SerializedName("c")
    @Expose
    public String c;
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
    @SerializedName("attachment")
    @Expose
    public String attachment;
    @SerializedName("lastreply")
    @Expose
    public String lastreply;
    @SerializedName("flag")
    @Expose
    public String flag;
    @SerializedName("service")
    @Expose
    public String service;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String deptName="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
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
}
