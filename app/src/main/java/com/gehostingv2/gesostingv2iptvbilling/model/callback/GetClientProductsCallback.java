package com.gehostingv2.gesostingv2iptvbilling.model.callback;


import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ProductPojo;

public class GetClientProductsCallback extends CommonResponseCallback{

    @SerializedName("clientId")
    private int clientId;
    @SerializedName("products")
    private ProductPojo productts;
    public ProductPojo getProductts() {
        return productts;
    }
    public void setProductts(ProductPojo productts) {
        this.productts = productts;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}

