package com.example.inventory_program;

import java.math.BigDecimal;

/**
 * @author Evelyn G Morrow.
 * @version 1.0.
 * Public class ProductData used to display the products tableview on the dashboard at the Home Page.
 */
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

    /**
     * Public ProductData Constructor is called when the ProductData object is instantiated.
     * It has the function of initialize the newly created object before it is used.
     * The list of parameters that the public ProductData constructor can take are declared into the parenthesis, and listed below:
     * @param productID is taken by public ProductData Constructor, and initializes the private Integer productID variable.
     * @param product_name is taken by public ProductData Constructor, and initializes the private String product_name variable.
     * @param stock is taken by public ProductData Constructor, and initializes the private Integer stock variable.
     * @param price_unit is taken by public ProductData Constructor, and initializes the private BigDecimal price_unit variable.
     */
    public ProductData (Integer productID, String product_name, Integer stock,BigDecimal price_unit) {
        this.productID = productID;
        this.product_name = product_name;
        this.stock = stock;
        this.price_unit = price_unit;
    }

    /**
     * public Integer getProductID() returns the ID of the product.
     * @return productID
     */
    public Integer getProductID() {
        return productID;
    }

    /**
     * public void setProductID() accepts the productID and sets it to the ProductData object.
     * @param productID is the ID of the product
     */
    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    /**
     * public String getProduct_name() returns the name of the product.
     * @return product_name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * public void setProduct_name() accepts the product_name and sets it to the ProductData object
     * @param product_name is the name of the product
     */
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /**
     * public Integer getStock() returns the number of products available in the inventory
     * @return stock is the inventory number of the product
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * public void setStock() accepts the stock and sets it to the ProductData object
     * @param stock is the inventory number of the product
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * public BigDecimal getPrice_unit() returns the price per unit of that product
     * @return price_unit is the price per unit of the product
     */
    public BigDecimal getPrice_unit() {
        return price_unit;
    }

    /**
     *public void setPrice_unit() accepts the price_unit and sets it to the oductData Probject
     * @param price_unit is the price per unit of the product
     */
    public void setPrice_unit(BigDecimal price_unit) {
        this.price_unit = price_unit;
    }

    /**
     *public Integer getMin() returns the min inventory allowed for that product
     * @return min is the min inventory allowed for that product
     */
    public Integer getMin() {
        return min;
    }

    /**
     *public void setMin() accepts the min inventory allowed for that product and sets it to the ProductData object.
     * @param min is the min inventory allowed for that product
     */
    public void setMin(Integer min) {
        this.min = min;
    }

    /**
     *public Integer getMax() returns the max inventory allowed for that product.
     * @return max is the max inventory allowed for that product
     */
    public Integer getMax() {
        return max;
    }

    /**
     *public void setMax() accepts the max inventory allowed for that product and sets it to the ProductData object.
     * @param max is the max inventory allowed for that part
     */
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
