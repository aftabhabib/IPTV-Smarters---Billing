package com.gehostingv2.gesostingv2iptvbilling.model.callback;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.MyDetailClientPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.MyDetailStatsPojo;

public class MyDetailsCallback {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("client")
    @Expose
    private MyDetailClientPojo client;
    @SerializedName("stats")
    @Expose
    private MyDetailStatsPojo stats;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MyDetailClientPojo getClient() {
        return client;
    }

    public void setClient(MyDetailClientPojo client) {
        this.client = client;
    }

    public MyDetailStatsPojo getStats() {
        return stats;
    }

    public void setStats(MyDetailStatsPojo stats) {
        this.stats = stats;
    }


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullstate() {
        return fullstate;
    }

    public void setFullstate(String fullstate) {
        this.fullstate = fullstate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public Integer getPhonecc() {
        return phonecc;
    }

    public void setPhonecc(Integer phonecc) {
        this.phonecc = phonecc;
    }

    public String getPhonenumberformatted() {
        return phonenumberformatted;
    }

    public void setPhonenumberformatted(String phonenumberformatted) {
        this.phonenumberformatted = phonenumberformatted;
    }

    public Integer getBillingcid() {
        return billingcid;
    }

    public void setBillingcid(Integer billingcid) {
        this.billingcid = billingcid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getTwofaenabled() {
        return twofaenabled;
    }

    public void setTwofaenabled(Boolean twofaenabled) {
        this.twofaenabled = twofaenabled;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getDefaultgateway() {
        return defaultgateway;
    }

    public void setDefaultgateway(String defaultgateway) {
        this.defaultgateway = defaultgateway;
    }

    public String getCctype() {
        return cctype;
    }

    public void setCctype(String cctype) {
        this.cctype = cctype;
    }

    public String getCclastfour() {
        return cclastfour;
    }

    public void setCclastfour(String cclastfour) {
        this.cclastfour = cclastfour;
    }

    public Integer getSecurityqid() {
        return securityqid;
    }

    public void setSecurityqid(Integer securityqid) {
        this.securityqid = securityqid;
    }

    public String getSecurityqans() {
        return securityqans;
    }

    public void setSecurityqans(String securityqans) {
        this.securityqans = securityqans;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Boolean getTaxexempt() {
        return taxexempt;
    }

    public void setTaxexempt(Boolean taxexempt) {
        this.taxexempt = taxexempt;
    }

    public Boolean getLatefeeoveride() {
        return latefeeoveride;
    }

    public void setLatefeeoveride(Boolean latefeeoveride) {
        this.latefeeoveride = latefeeoveride;
    }

    public Boolean getOverideduenotices() {
        return overideduenotices;
    }

    public void setOverideduenotices(Boolean overideduenotices) {
        this.overideduenotices = overideduenotices;
    }

    public Boolean getSeparateinvoices() {
        return separateinvoices;
    }

    public void setSeparateinvoices(Boolean separateinvoices) {
        this.separateinvoices = separateinvoices;
    }

    public Boolean getDisableautocc() {
        return disableautocc;
    }

    public void setDisableautocc(Boolean disableautocc) {
        this.disableautocc = disableautocc;
    }

    public Boolean getEmailoptout() {
        return emailoptout;
    }

    public void setEmailoptout(Boolean emailoptout) {
        this.emailoptout = emailoptout;
    }

    public Boolean getOverrideautoclose() {
        return overrideautoclose;
    }

    public void setOverrideautoclose(Boolean overrideautoclose) {
        this.overrideautoclose = overrideautoclose;
    }

    public Integer getAllowSingleSignOn() {
        return allowSingleSignOn;
    }

    public void setAllowSingleSignOn(Integer allowSingleSignOn) {
        this.allowSingleSignOn = allowSingleSignOn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("companyname")
    @Expose
    private String companyname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("fullstate")
    @Expose
    private String fullstate;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("countrycode")
    @Expose
    private String countrycode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("statecode")
    @Expose
    private String statecode;
    @SerializedName("countryname")
    @Expose
    private String countryname;
    @SerializedName("phonecc")
    @Expose
    private Integer phonecc;
    @SerializedName("phonenumberformatted")
    @Expose
    private String phonenumberformatted;
    @SerializedName("billingcid")
    @Expose
    private Integer billingcid;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("twofaenabled")
    @Expose
    private Boolean twofaenabled;
    @SerializedName("currency")
    @Expose
    private Integer currency;
    @SerializedName("defaultgateway")
    @Expose
    private String defaultgateway;
    @SerializedName("cctype")
    @Expose
    private String cctype;
    @SerializedName("cclastfour")
    @Expose
    private String cclastfour;
    @SerializedName("securityqid")
    @Expose
    private Integer securityqid;
    @SerializedName("securityqans")
    @Expose
    private String securityqans;
    @SerializedName("groupid")
    @Expose
    private Integer groupid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("taxexempt")
    @Expose
    private Boolean taxexempt;
    @SerializedName("latefeeoveride")
    @Expose
    private Boolean latefeeoveride;
    @SerializedName("overideduenotices")
    @Expose
    private Boolean overideduenotices;
    @SerializedName("separateinvoices")
    @Expose
    private Boolean separateinvoices;
    @SerializedName("disableautocc")
    @Expose
    private Boolean disableautocc;
    @SerializedName("emailoptout")
    @Expose
    private Boolean emailoptout;
    @SerializedName("overrideautoclose")
    @Expose
    private Boolean overrideautoclose;
    @SerializedName("allowSingleSignOn")
    @Expose
    private Integer allowSingleSignOn;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("lastlogin")
    @Expose
    private String lastlogin;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;

}

