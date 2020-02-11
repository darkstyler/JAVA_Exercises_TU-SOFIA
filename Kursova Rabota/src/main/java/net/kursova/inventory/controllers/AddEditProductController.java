package net.kursova.inventory.controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import net.kursova.inventory.App;
import net.kursova.inventory.libs.BaseController;
import net.kursova.inventory.models.Product;
import net.kursova.inventory.models.ProductGroup;

public class AddEditProductController extends BaseController implements Initializable {

    @FXML
    private TextField fldName;
    @FXML
    private TextField fldPrice;
    @FXML
    private Label errorLabel;
    @FXML
    private Label savedLabel;
    @FXML
    private ComboBox<ProductGroup> groupCombo;

    // На базата на това дали id-to е null или число влизаме в Редактиране или добавяне
    private String _productId = App.getInstance().repository.get("selectedProductId");

    public void initialize(URL url, ResourceBundle rb) {
        if (_productId != null) {
            _loadProductData(_productId);
            _fillGroupComboData(inventoryService.getProduct(_productId).getGroup());
        } else {
            _fillGroupComboData(null);
        }
    }

    private void _loadProductData(String productId) {
        Product product = inventoryService.getProduct(productId);
        fldName.setText(product.getName());
        fldPrice.setText(product.getPrice());
    }

    private boolean _fillGroupComboData(ProductGroup selectedGroup) {

        ObservableList<ProductGroup> comboData = FXCollections.observableArrayList(
                inventoryService.getGroups()
        );
        groupCombo.getItems().addAll(comboData);

        if (selectedGroup != null && selectedGroup.getId() > 0) {
            groupCombo.getSelectionModel().select(selectedGroup);
        }

        return true;
    }

    @FXML
    protected void hideLabels() {
        errorLabel.setVisible(false);
        savedLabel.setVisible(false);
    }

    @FXML
    protected void handleBack() {
        App.showPage("products");
    }

    @FXML
    protected boolean handleSaveProduct(ActionEvent event) {

        if (!fldPrice.getText().matches("[0-9.]*") || fldName.getText().length() < 2) {
            errorLabel.setVisible(true);
            return false;
        }

        //Модел на продукта и записването му в базата данни
        Product product = new Product();
        if (_productId != null) {
            product = inventoryService.getProduct(_productId);
        }
        product.setName(fldName.getText());
        product.setPrice(fldPrice.getText());
        // Ако е избрана група на продукта се добавя в нейната база данни
        ProductGroup selectedGroup = groupCombo.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            
            product.setGroup(selectedGroup);
        }

        boolean isProductSaved = inventoryService.saveProduct(product);

        if (!isProductSaved) {
            errorLabel.setText("Грешка!");
            return false;
        } else {
            // Изчиства полетата след добавяне на елемент
            if (_productId == null) {
                fldName.setText("");
                fldPrice.setText("");
            }
            savedLabel.setVisible(true);
            errorLabel.setVisible(false);
            return true;
        }

    }
}

