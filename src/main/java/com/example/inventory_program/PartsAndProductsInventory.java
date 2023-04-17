package com.example.inventory_program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartsAndProductsInventory {
    private ObservableList<ProductData> allProducts = FXCollections.observableArrayList();
    private ObservableList<PartData> allParts = FXCollections.observableArrayList();

    public void updatePart(PartData updatePartData) {
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getPartID() == updatePartData.getPartID()) {
                allParts.set(i, updatePartData);
                break;
            }
        }
        return;
    }

    public ObservableList<PartData> getAllParts() {
        return allParts;
    }

}
