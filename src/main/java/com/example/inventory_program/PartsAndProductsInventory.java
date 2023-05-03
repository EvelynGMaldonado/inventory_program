package com.example.inventory_program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Evelyn G Morrow.
 * @version 1.0.
 * Public class PartsAndProductsInventory is used to manage the products and parts that EM Management System has in inventory.
 */
public class PartsAndProductsInventory {
    private ObservableList<ProductData> allProducts = FXCollections.observableArrayList();
    private ObservableList<PartData> allParts = FXCollections.observableArrayList();

    /**
     * Public void updatePart() method takes the updatePartData parameter from the PartDara public class.
     * @param updatePartData is used to retrieve the partID od the part we are trying to update.
     */
    public void updatePart(PartData updatePartData) {
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getPartID() == updatePartData.getPartID()) {
                allParts.set(i, updatePartData);
                break;
            }
        }
        return;
    }

    /**
     * Public ObeservableList gets all the parts from the PartDara public class.
     * @return allParts.
     */
    public ObservableList<PartData> getAllParts() {
        return allParts;
    }

}
