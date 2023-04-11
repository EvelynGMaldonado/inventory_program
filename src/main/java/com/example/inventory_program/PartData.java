package com.example.inventory_program;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;

public class PartData {
    private Integer partID;
    private String part_name;
    private Integer stock;
    private BigDecimal price_unit;
    private Integer min;
    private Integer max;
    private Integer machineID;
    private String company_name;

    public PartData(Integer partID, String part_name, Integer stock,BigDecimal price_unit) {
        this.partID = partID;
        this.part_name = part_name;
        this.stock = stock;
        this.price_unit = price_unit;
    }



    public Integer getPartID() {
        return partID;
    }

    public void setPartID(Integer partID) {
        this.partID = partID;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
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

    public Integer getMachineID() {
        return machineID;
    }

    public void setMachineID(Integer machineID) {
        this.machineID = machineID;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }



}
