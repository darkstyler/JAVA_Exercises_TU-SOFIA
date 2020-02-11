package net.kursova.inventory.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import net.kursova.inventory.App;
import net.kursova.inventory.libs.BaseController;
import net.kursova.inventory.models.User;


public class LoginController extends BaseController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    protected void btnLoginPressed(ActionEvent event) {
        User user = inventoryService.getUserByEmail(emailField.getText());
        if (user.getPassword().equals(passwordField.getText())) {
            App.showPage("dashboard");
        } else {
            errorLabel.setVisible(true);
        }
    }
    
    @FXML
    protected void btnRegister(ActionEvent event) {
    	App.showPage("register");
    }
    
    
    
}