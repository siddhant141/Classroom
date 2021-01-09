package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FLoginController implements Initializable
{
	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtUsername;

	@FXML
	private TextField txtPassword;
	
 

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	public void backbutton(ActionEvent event )
	{
		
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide() ; 
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/Welcome.fxml").openStream());
			
			WelcomeController welcomecontroller = (WelcomeController)loader.getController() ; 
			
			
			Scene scene = new Scene(root, 1000, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void refreshbutton(ActionEvent event )
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
	
	
	
	public void login(ActionEvent event)
	{
		String user = txtUsername.getText();
		String pass = txtPassword.getText();
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");

			try
			{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classroom", "root", "");

				Statement stmt = con.createStatement();

				PreparedStatement ps = con.prepareStatement("select * from flogin where fid=? and password=?");

				ps.setString(1, user);
				ps.setString(2, pass);

				ResultSet res = ps.executeQuery();

				if (res.next())
				{
					lblStatus.setText("Login Success");
					try {
						
						((Node)event.getSource()).getScene().getWindow().hide() ; 
						
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						Pane root = loader.load(getClass().getResource("/application/Fdashboard.fxml").openStream());   //use the xmlfile
						//StudentController studentController = (StudentController)loader.getController();
						//studentController.showName(user);
						
						FdashboardController fController = (FdashboardController)loader.getController() ; 
						//System.out.println(fController);
						fController.getid(user) ;
						//fController.getcontrol(loader);
						
						List<VBox> vbx = fController.showClasses(user);
						HBox hbox = new HBox() ; 
						hbox.getChildren().addAll(vbx) ;
						root.getChildren().addAll(hbox) ;
						
						hbox.setSpacing(50);
						hbox.setLayoutX(100);
						hbox.setLayoutY(200);
						
						Scene scene = new Scene(root, 1000, 1000);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch(Exception e) {
						e.printStackTrace();
					}
					
		
				} else
				{
					lblStatus.setText("Invalid Credentials Specified");
				}

				con.close();
			} catch (SQLException ex)
			{
				ex.printStackTrace();
			}

		} catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}

	}



}
