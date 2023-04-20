package com.example.inventory_program;

import java.math.BigDecimal;

public class ProductData {
    private Integer productID;
    private String product_name;
    private Integer stock;
    private BigDecimal price_unit;
    private Integer min;
    private Integer max;
//    private Integer partID;
//    private String part_name;
//    private Integer part_stock;
//    private Integer part_price_unit;

    public ProductData (Integer productID, String product_name, Integer stock,BigDecimal price_unit) {
        this.productID = productID;
        this.product_name = product_name;
        this.stock = stock;
        this.price_unit = price_unit;
    }


    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(BigDecimal price_unit) {
        this.price_unit = price_unit;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

//    public Integer getPartID() {
//        return partID;
//    }
//
//    public void setPartID(Integer partID) {
//        this.partID = partID;
//    }

//    public String getPart_name() {
//        return part_name;
//    }
//
//    public void setPart_name(String part_name) {
//        this.part_name = part_name;
//    }
//
//    public Integer getPart_stock() {
//        return part_stock;
//    }
//
//    public void setPart_stock(Integer part_stock) {
//        this.part_stock = part_stock;
//    }
//
//    public Integer getPart_price_unit() {
//        return part_price_unit;
//    }
//
//    public void setPart_price_unit(Integer part_price_unit) {
//        this.part_price_unit = part_price_unit;
//    }
}
