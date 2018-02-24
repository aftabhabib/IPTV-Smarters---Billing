package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class ProductPojo extends ExpandableGroup<ProductDetailPojo> {
    public ProductPojo(String title, List<ProductDetailPojo> items) {
        super(title, items);
    }

    public ArrayList<ProductDetailPojo> getProductDeailArrayList() {
        return productDeailArrayList;
    }

    public void setProductDeailArrayList(ArrayList<ProductDetailPojo> productDeailArrayList) {
        this.productDeailArrayList = productDeailArrayList;
    }

    @SerializedName("product")
    ArrayList<ProductDetailPojo> productDeailArrayList;
}
