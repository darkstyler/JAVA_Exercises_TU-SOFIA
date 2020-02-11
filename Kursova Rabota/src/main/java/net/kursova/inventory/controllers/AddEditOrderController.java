package net.kursova.inventory.controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.cell.TextFieldTableCell;
import net.kursova.inventory.App;
import net.kursova.inventory.libs.BaseController;
import net.kursova.inventory.models.*;

public class AddEditOrderController extends BaseController implements Initializable {

    private static final String STATUS_IN_PROGRESS = "чакаща";
    private static final String STATUS_COMPLETED = "завършена";


    @FXML
    private TextField fldName;
    @FXML
    private TextField fldCity;
    @FXML
    private TextField fldAddress;
    @FXML
    private TextField fldZip;
    @FXML
    private TextArea fldComment;
    @FXML
    private ComboBox<String> comboStatus;
    @FXML
    private ComboBox<String> comboType;

    @FXML
    private ComboBox<Product> comboProducts;
    @FXML
    private ComboBox<GroupVariant> comboVariants;

    @FXML
    private TableView<OrderItem> tblItems;
    @FXML
    private TableColumn<Product, String> colProduct;
    @FXML
    private TableColumn<GroupVariant, String> colVariant;
    @FXML
    private TableColumn<OrderItem, String> colPrice;

    @FXML
    private Label errorLabel;
    @FXML
    private Label savedLabel;

    //На базата на OrderId знаем дали сме в секция добави или Редактирай
    private String _orderId = App.getInstance().repository.get("selectedOrderId");
    private Order editingOrder;

    private List<OrderItem> removeList = new ArrayList<OrderItem>();

    public void initialize(URL url, ResourceBundle rb) {

        _fillAllProductsCombo();
        comboStatus.getItems().addAll(STATUS_IN_PROGRESS, STATUS_COMPLETED);

        comboType.getItems().addAll("buy", "sell");

        tblItems.setEditable(true);

        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colVariant.setCellValueFactory(new PropertyValueFactory<>("groupVariant"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setCellFactory(TextFieldTableCell.<OrderItem>forTableColumn());

        colPrice.setOnEditCommit((TableColumn.CellEditEvent<OrderItem, String> t) -> {
            ((OrderItem) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setPrice(t.getNewValue());
        });

        if (_orderId != null) {
            _loadOrderData(_orderId);
        }

    }

    private void _loadOrderData(String orderId) {

        Order order = inventoryService.getOrder(orderId);

        fldName.setText(order.getName());
        fldCity.setText(order.getCity());
        fldAddress.setText(order.getAddress());
        fldZip.setText(order.getZip());
        fldComment.setText(order.getComment());

        comboStatus.getSelectionModel().select(order.getStatus());

        String selectedType = order.getType().equals("buy") ? "buy" : "sell";

        comboType.getSelectionModel().select(selectedType);

        tblItems.getItems().addAll(order.getItems());
        this.editingOrder = order;
    }

    private void _fillAllProductsCombo() {
        comboProducts.getItems().addAll(
                inventoryService.getProducts()
        );
        comboProducts.getSelectionModel().selectFirst();
    }

    private void _fillVariantsCombo(Product product) {

        ProductGroup group = product.getGroup();
        comboVariants.getItems().clear();
        comboVariants.getItems().addAll(
                group.getGroupVariants()
        );
        comboVariants.getSelectionModel().selectFirst();
    }

    // Зарежда Вариантите когато продукт бива избран
    @FXML
    private void productSelected() {
    	
        Product selectedProduct = comboProducts.getSelectionModel().getSelectedItem();

        _fillVariantsCombo(selectedProduct);
    }

    // Изпълнява се като се натисне бутон добави
    @FXML
    private void addProduct() {

     

        Product product = comboProducts.getSelectionModel().getSelectedItem();
        GroupVariant variant = comboVariants.getSelectionModel().getSelectedItem();

        if (product != null) {
            OrderItem item = new OrderItem();

            item.setProduct(product);
            item.setGroupVariant(variant);

            item.setOrder(this.editingOrder); // Застраховаме се дали е записана поръчката

            tblItems.getItems().add(item);
            comboProducts.getSelectionModel().selectFirst();
            comboVariants.getItems().clear();
        }
    }

    @FXML
    protected boolean save() {

        Order order = new Order();

        if (this.editingOrder == null) {
            order.setCreated(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
        } else {
            order = this.editingOrder;
        }

        order.setName(fldName.getText());
        order.setType(comboType.getSelectionModel().getSelectedItem().toLowerCase());
        order.setAddress(fldAddress.getText());
        order.setCity(fldCity.getText());
        order.setZip(fldZip.getText());
        order.setStatus(comboStatus.getSelectionModel().getSelectedItem());
        order.setComment(fldComment.getText());
        order.setItems(tblItems.getItems());
        
        
        if (inventoryService.saveOrder(order)) {
            this.removeItems();
            App.showPage("orders");
        } else {
            System.out.println("Вашата поръчка не може да бъде записана!");
            errorLabel.setVisible(true);
        }

        return true;
    }

    private void removeItems() {
        for (OrderItem item : this.removeList) {
            inventoryService.removeOrderItem(item);
        }
    }

    @FXML
    private void handleBack() {
        App.showPage("orders");
    }

    @FXML
    private void handleRemove() {

        OrderItem selectedItem = tblItems.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            tblItems.getItems().removeAll(selectedItem);
            this.removeList.add(selectedItem);
        }
    }

}