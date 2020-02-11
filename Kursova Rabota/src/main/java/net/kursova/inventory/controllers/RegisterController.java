package net.kursova.inventory.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import net.kursova.inventory.App;
import net.kursova.inventory.models.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import net.kursova.inventory.services.InventoryService;
import net.kursova.inventory.services.InventoryServiceHibernate;
import net.kursova.inventory.libs.BaseController;



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

public class RegisterController extends BaseController implements Initializable  {
	
	@FXML
    private TextField fldEmail;
    @FXML
    private TextField fldPass;
    @FXML
    private TextField fldName;
   
    
    private String _userID = App.getInstance().repository.get("selectedUserId");

    public void initialize(URL url, ResourceBundle rb) {

        if (_userID != null) {
            _LoadUserData(_userID);
        }
    }
    private void _LoadUserData(String userId) {
        
        User user = inventoryService.getUser(userId);
        fldEmail.setText(user.getEmail());
        fldPass.setText(user.getPassword());
    }
    @FXML
    protected boolean btnRegister(){
    	User user = new User();
    	user.setId(null);
    	user.setName(fldName.getText());
    	 user.setEmail(fldEmail.getText());
    	 user.setPassword(fldPass.getText());
       	user.setCreated(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
     	 user.setAccessToken(null);
    	System.out.println("Test 231");
    	inventoryService.btnRegister(user);
    	App.showPage("login");
    	 return true;
    	
    }
    @FXML
    private void handleBack() {
        App.showPage("login");
    }
    	
    }




	




	


