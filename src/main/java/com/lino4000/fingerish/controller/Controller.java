package com.lino4000.fingerish.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lino4000.fingerish.service.UserService;
import com.lino4000.fingerish.view.Screen;
import com.lino4000.fingerish.view.Screen.View;
import com.lino4000.fingerish.model.Database;
import com.lino4000.fingerish.model.Role;
import com.lino4000.fingerish.model.User;
import com.lino4000.fingerish.repository.DatabaseRepository;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintTemplate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

@Component
public class Controller implements Initializable {
	
	private Screen app = Screen.getSingleton();
	
	@FXML private ImageView imageView;
	@FXML private TableView<Database> tableView;
	@FXML private TextField lvl1;
	@FXML private TextField lvl2;
	@FXML private TextField lvl3;

	private FingerprintTemplate fgAttempt;
	private User user;

	@Autowired
	private DatabaseRepository dataRepository;
	@Autowired
	private UserService userService;
	
	public Controller() {
		
	}
    
    @FXML
    private void login(ActionEvent event) {
        event.consume();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
        	new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
       
        File selectedFile = fileChooser.showOpenDialog(null);

        this.imageView.setImage(new Image(selectedFile.toURI().toString()));
		byte[] probeImage = null;
		try {
			probeImage = Files.readAllBytes(selectedFile.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	this.fgAttempt = new FingerprintTemplate(
        	    new FingerprintImage()
        	        .dpi(300)
        	        .decode(probeImage));
		System.out.println(Base64.getEncoder().encodeToString(fgAttempt.toByteArray()));
    	this.user = userService.verifyFingerPrint(this.fgAttempt);
    	if( Objects.nonNull(this.user) ) {
    		this.app.removeAllAndActivate(View.LOGGED);
    	}
    	
    }

    @FXML
    private void load(ActionEvent event) {
    	
		if (this.user.getRole().getId() <= 3)
			this.lvl3.setText( dataRepository.findByRole(Role.builder().id(3).build()).getData());
		if (this.user.getRole().getId() <= 2)
			this.lvl2.setText( dataRepository.findByRole(Role.builder().id(2).build()).getData());
		if (this.user.getRole().getId() <= 1)
			this.lvl1.setText( dataRepository.findByRole(Role.builder().id(1).build()).getData());
		
    }
   
    
    @FXML
    private void logout(ActionEvent event) {
    	this.app.removeAllAndActivate(View.LOGIN);
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}