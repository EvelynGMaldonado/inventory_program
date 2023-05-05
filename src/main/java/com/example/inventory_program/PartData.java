package com.example.inventory_program;

import java.math.BigDecimal;

/**
 * @author Evelyn G Morrow.
 * @version 1.0.
 * Public class PartData used to display the parts tableview on the dashboard at the Home Page, the founded parts at the Homepage, the available parts at the Add Product Page and at the Modify Product Page .
 * RUNTIME ERROR:
 * FUTURE ENHANCEMENT:
 */
public class PartData {
    private Integer partID;
    private String part_name;
    private Integer stock;
    private BigDecimal price_unit;
    private Integer min;
    private Integer max;
    private Integer machineID;
    private String company_name;

    /**
     * Public PartData Constructor is called when the ProductData object is instantiated.
     * It has the function of initialize the newly created object before it is used.
     * The list of parameters that the public PartData constructor can take are declared into the parenthesis, and listed below:
     * @param partID is taken by public PartData Constructor, and initializes the private Integer partID variable.
     * @param part_name is taken by public PartData Constructor, and initializes the private String part_name variable.
     * @param stock is taken by public PartData Constructor, and initializes the private Integer stock variable.
     * @param price_unit is taken by public PartData Constructor, and initializes the private BigDecimal price_unit variable.
     */
    public PartData(Integer partID, String part_name, Integer stock,BigDecimal price_unit) {
        this.partID = partID;
        this.part_name = part_name;
        this.stock = stock;
        this.price_unit = price_unit;
    }

    /**
     * public Integer getPartID() returns the ID of the part.
     * @return partID
     */
    public Integer getPartID() {
        return partID;
    }

    /**
     * public void setPartID() accepts the partID and sets it to the RowPartData object.
     * @param partID is the ID of the part
     */
    public void setPartID(Integer partID) {
        this.partID = partID;
    }

    /**
     * public String getPart_name() returns the name of the part.
     * @return part_name
     */
    public String getPart_name() {
        return part_name;
    }

    /**
     * public void setPart_name() accepts the part_name and sets it to the RowPartData object
     * @param part_name is the name of the part
     */
    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    /**
     * public Integer getStock() returns the number of parts available in the inventory
     * @return stock is the inventory number of the part
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * public void setStock() accepts the stock and sets it to the RowPartData object
     * @param stock is the inventory number of the part
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * public BigDecimal getPrice_unit() returns the price per unit od that part
     * @return price_unit is the price per unit of the part
     */
    public BigDecimal getPrice_unit() {
        return price_unit;
    }

    /**
     *public void setPrice_unit() accepts the price_unit and sets it to the RowPartData object
     * @param price_unit is the price per unit of the part
     */
    public void setPrice_unit(BigDecimal price_unit) {
        this.price_unit = price_unit;
    }

    /**
     *public Integer getMin() returns the min inventory allowed for that part
     * @return min is the min inventory allowed for that part
     */
    public Integer getMin() {
        return min;
    }

    /**
     *public void setMin() accepts the min inventory allowed for that part and sets it to the RowPartData object.
     * @param min is the min inventory allowed for that part
     */
    public void setMin(Integer min) {
        this.min = min;
    }

    /**
     *public Integer getMax() returns the max inventory allowed for that part.
     * @return max is the max inventory allowed for that part
     */
    public Integer getMax() {
        return max;
    }

    /**
     *public void setMax() accepts the max inventory allowed for that part and sets it to the RowPartData object.
     * @param max is the max inventory allowed for that part
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     *public Integer getMachineID() returns the machineID that produced the part.
     * @return machineID is the ID of the machine that produced the part
     */
    public Integer getMachineID() {
        return machineID;
    }

    /**
     *public void setMachineID() accepts the machineID and sets it to the RowPartData object
     * @param machineID is the ID of the machine that produces that part
     */
    public void setMachineID(Integer machineID) {
        this.machineID = machineID;
    }

    /**
     *public String getCompany_name() returns the company_name that produced the part.
     * @return company_name is the name of the company that produced the part
     */
    public String getCompany_name() {
        return company_name;
    }

    /**
     *public void setCompany_name() accepts the company_name and sets it to the RowPartData object
     * @param company_name is the name of the outsourced company that produces that part
     */
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

}
