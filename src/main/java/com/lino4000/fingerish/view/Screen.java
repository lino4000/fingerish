package com.lino4000.fingerish.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.lino4000.fingerish.FingerishApplication;
import com.lino4000.fingerish.controller.Controller;
import com.lino4000.fingerish.model.User;

public class Screen extends Application {
	
	private ConfigurableApplicationContext context;
	@Autowired
	private Controller controller;
	
    // Singleton
    private static Screen singleton;
    public Screen() { singleton = this; }
    public static Screen getSingleton() { return singleton; }
    
    private Stage stage;

    private Map<View, Parent> parents = new HashMap<>();
	
	public void init() throws Exception {
		ApplicationContextInitializer<GenericApplicationContext> initializer =
				ac -> {
					ac.registerBean(Application.class, () -> Screen.this);
					ac.registerBean(Parameters.class, this::getParameters);
				};
		this.context = new SpringApplicationBuilder()
				.sources(FingerishApplication.class)
				.initializers(initializer)
				.run(getParameters().getRaw().toArray(new String[0]));
		
	}

	@Override
	public void start(Stage primaryStage) {
	    stage = primaryStage;
	    stage.setTitle("Fingerish");
	    add(View.LOGIN);
	    stage.setScene(new Scene(parents.get(View.LOGIN)));
	    stage.show();
	}
	
	public void add(View view) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(view.location));
		loader.setControllerFactory(context::getBean);
	
	    try {
	        Parent root = loader.load();
	        parents.put(view, root);
	    } catch (IOException e) { /* Do something */ }
	}
	
	public void remove(View view) {
	    parents.remove(view);
	}
	
	public void activate(View view) {
	    stage.getScene().setRoot(parents.get(view));
	}
	
	public void removeAllAndActivate(View view) {
	    parents.clear();
	    add(view);
	    activate(view);
	}
	
    
    public enum View {
        LOGIN("Login.fxml"),
        LOGGED("Logged.fxml");

        public final String location;

        View(String location) {
            this.location = "/fxml/" + location;
        }
    }
}