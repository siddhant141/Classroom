package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StudentController implements Initializable 
{
	@FXML
	private Label studentLbl;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
		
	}
	
	public void showName(String student)
	{
		studentLbl.setText(student);
	}
	
	
	
	public void signOut(ActionEvent event)
	{
		
	}
}
