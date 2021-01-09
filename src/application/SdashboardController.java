package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SdashboardController implements Initializable
{
	String sid;
	
	@FXML
	private Label sidLbl;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
	
	public void refreshbutton(ActionEvent event )
	{
		
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide() ; 
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/application/Sdashboard.fxml").openStream());   //use the xmlfile
			
			SdashboardController sdashboardController = (SdashboardController)loader.getController() ; 
			sdashboardController.setid(sid) ;
			List<VBox> vbox = sdashboardController.showClasses(sid);
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
		
	}
	
	public void backbutton(ActionEvent event )
	{
		
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide() ; 
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/SLogin.fxml").openStream());
			
			SloginController slogincontroller = (SloginController)loader.getController() ; 
			
			
			Scene scene = new Scene(root, 1000, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void joinClass(ActionEvent event)
	{
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		
		try
		{
			
			Pane root = loader.load(getClass().getResource("/application/Joins.fxml").openStream());
			JoinsController joinsController = (JoinsController)loader.getController();
			System.out.println(joinsController);
			joinsController.join(sidLbl.getText());
			Scene scene = new Scene(root, 1000, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
		}  
	

	}
	
	public void setid(String student)
	{
		sidLbl.setText(student);
		sid = student;
	}
	
	@FXML
	public List<VBox> showClasses(String student)
	{
		String sid;
		String className;
		String fid;
		String classCode;
		List<VBox> list = new ArrayList<VBox>();
		try
		{
			sid = student;
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classroom", "root", "");
			
			PreparedStatement ps = con.prepareStatement("select * from classes where sid=?");
			ps.setString(1, sid);
			
			ResultSet res = ps.executeQuery();
			
			while(res.next())
			{
				VBox vbox = new VBox();
				Label cName = new Label();
				fid = res.getString(3);
				classCode = res.getString(1);
				String classc = classCode;
				
				PreparedStatement ps1 = con.prepareStatement("Select classname from fclass where classcode = ?");
				ps1.setString(1, classCode);
				ResultSet res1 = ps1.executeQuery();
				if(res1.next())
				{
					className = res1.getString(1);
					cName.setText(className);
				}

				
				Button go = new Button("Go");
				go.setOnAction(e -> {
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					
					try
					{
						
						Pane root1 = loader.load(getClass().getResource("/application/Swork.fxml").openStream());
						SworkController sworkController = (SworkController)loader.getController();
						System.out.println(sworkController);
						List<VBox> vb = sworkController.showWork(sid, classc, primaryStage);
						
						VBox v = new VBox();
						v.getChildren().addAll(vb);
						v.setLayoutX(10);
						v.setLayoutY(100);
						root1.getChildren().add(v);
						
						Scene scene = new Scene(root1, 1000, 1000);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (IOException ex)
					{
						ex.printStackTrace();
					}  
					
				});
				vbox.setStyle("-fx-border-color : black;");
				vbox.setStyle("-fx-background-color : white;");
				vbox.setAlignment(Pos.CENTER);
				vbox.setPadding(new Insets(2, 2, 2, 2));
				vbox.setSpacing(20);
				vbox.getChildren().addAll(cName, go);
				list.add(vbox);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	

}
