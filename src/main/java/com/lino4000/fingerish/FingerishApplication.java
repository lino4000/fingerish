package com.lino4000.fingerish;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import com.lino4000.fingerish.view.Screen;

@SpringBootApplication
public class FingerishApplication {
	
	public static void main(String[] args) {
		Application.launch(Screen.class, args);
	}
}