package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WelcomeController implements Initializable {

	@FXML
	Button fbutton ; 
	
	@FXML
	Button sbutton ; 
	
	public void fbutton(ActionEvent event )
	{
		
		try {
		
		((Node)event.getSource()).getScene().getWindow().hide() ; 
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		
		Pane root = loader.load(getClass().getResource("/application/FLogin.fxml").openStream());
		
		FLoginController flogincontroller = (FLoginController)loader.getController() ; 
		
		
		Scene scene = new Scene(root, 1000, 1000);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}

		
	}
	
	public void sbutton(ActionEvent event )
	{
		
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide() ; 
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/Slogin.fxml").openStream());
			
			SloginController slogincontroller = (SloginController)loader.getController() ; 
			
			
			Scene scene = new Scene(root, 1000, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// TODO Auto-generated method stub
		
	}

}
