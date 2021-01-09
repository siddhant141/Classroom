package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SloginController implements Initializable
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

				PreparedStatement ps = con.prepareStatement("select * from slogin where sid=? and password=?");

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
						Pane root = loader.load(getClass().getResource("/application/Sdashboard.fxml").openStream());   //use the xmlfile
						
						SdashboardController sdashboardController = (SdashboardController)loader.getController() ; 
						sdashboardController.setid(user) ;
						List<VBox> vbox = sdashboardController.showClasses(user);
						HBox hori = new HBox();
						hori.getChildren().addAll(vbox);
						hori.setSpacing(50);
						hori.setLayoutX(10);
						hori.setLayoutY(100);
						root.getChildren().addAll(hori);
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
