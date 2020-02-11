package net.kursova.inventory.controllers;

import net.kursova.inventory.App;

import net.kursova.inventory.models.GroupVariant;
import net.kursova.inventory.models.ProductGroup;

import net.kursova.inventory.libs.BaseController;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddEditGroupController extends BaseController implements Initializable {

    @FXML
    private TextField fldName;
    @FXML
    private TextField fldPrice;
    @FXML
    private Label errorLabel;
    @FXML
    private Label savedLabel;
    @FXML
    private TableView<GroupVariant> tblVariants;
    @FXML
    private TableColumn<GroupVariant, String> colVariantName;
    @FXML
    private TextField newVariantNameFld;

    //На базата на id-то разбираме дали сме в режим Нова група или Редкатиране
    private String _groupId = App.getInstance().repository.get("selectedGroupId");

    public void initialize(URL url, ResourceBundle rb) {

        if (_groupId != null) {
            _loadGroupData(_groupId);
        }
    }

    private void _loadGroupData(String groupId) {
       
        ProductGroup group = inventoryService.getGroup(groupId);
        fldName.setText(group.getGroupName());
        fldPrice.setText(group.getPrice());
        _loadGroupVariantsTable(group);
    }

    private void _loadGroupVariantsTable(ProductGroup group) {
        colVariantName.setCellValueFactory(new PropertyValueFactory<>("variantName"));
        tblVariants.getItems().addAll(group.getGroupVariants());
    }

    @FXML
    protected boolean saveGroup() {
//Буля запиши ако подадем нещо различно от цифри в цена връща грешка
        if (!fldPrice.getText().matches("[0-9.]*") || fldName.getText().length() < 2) {
        	//В такъв случай показва ErrorLabel-А
            errorLabel.setVisible(true);
            return false;
        } else {

            // Модел на инфромацията и нейното записване в базата данни
            ProductGroup group = new ProductGroup();
            if (_groupId != null && !_groupId.equals("")) {
                group = inventoryService.getGroup(_groupId);
            } else {
                group.setCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now()));
            }

            group.setGroupName(fldName.getText());
            group.setPrice(fldPrice.getText());
            group.setGroupVariants(tblVariants.getItems());

            // Нужно за да се запише успешно в Hibernate 
            for (GroupVariant gv : group.getGroupVariants()) {
                gv.setGroup(group);
            }

            if (!inventoryService.saveGroup(group)) {
                errorLabel.setText("Грешка групата не може да бъде записана.");
                return false;
            } else {

                if (_groupId == null) {
                    fldName.setText("");
                    fldPrice.setText("");
                }
                savedLabel.setVisible(true);
                errorLabel.setVisible(false);
                return true;
            }
        }
    }

    @FXML
    protected void hideLabels() {
        errorLabel.setVisible(false);
        savedLabel.setVisible(false);
    }

    @FXML
    protected void handleBack() {
        App.showPage("groups");
    }

    @FXML
    private void handleAddNewVariant() {
        if (newVariantNameFld.getText().length() > 0) {

            GroupVariant groupVariant = new GroupVariant();
            groupVariant.setVariantName(newVariantNameFld.getText());
            tblVariants.getItems().add(groupVariant);
            newVariantNameFld.setText("");
        }
    }
}

