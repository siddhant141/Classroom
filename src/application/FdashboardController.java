package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FdashboardController implements Initializable {

	@FXML 
	private Label fidLbl; 
	String fid ; 
	//FdashboardController fdashboard;

	FXMLLoader loader = new FXMLLoader();
	
	
	
	
	
	
	public void refreshbutton(ActionEvent event )
	{
		
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide() ; 
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/Fdashboard.fxml").openStream());
			
			FdashboardController fdashboardcontroller = (FdashboardController)loader.getController() ; 
			fdashboardcontroller.getid(fid) ; 
			
			List<VBox> vbx = fdashboardcontroller.showClasses(fid);
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
		
	}
	
	public void backbutton(ActionEvent event )
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
	
	
	
	
	
	
	
	
	
	

	public void button1Action(ActionEvent event) throws IOException
	{
		//((Node)event.getSource()).getScene().getWindow().hide() ; 
		CreatefController createcontroller = new CreatefController() ; 
		System.out.println(createcontroller);
		Stage primaryStage = new Stage();
		
		Parent root = loader.load(getClass().getResource("/application/Createf.fxml").openStream());   //use the xmlfile

		createcontroller = (CreatefController)loader.getController(); 
		String fid = fidLbl.getText() ; 
		System.out.println(fid + createcontroller);
		createcontroller.getclasscode(fid) ; 
		
		Scene scene = new Scene(root, 1000, 1000);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void getid(String Student)
	{
		
		fidLbl.setText(Student);
		fid = Student ; 
		
	}


	public List<VBox> showClasses(String faculty)
	{
		String sid;
		String className;
		String fid;
		String classCode;
		List<VBox> list = new ArrayList<VBox>();
		try
		{
			fid = faculty;
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classroom", "root", "");
			
			PreparedStatement ps = con.prepareStatement("select * from fclass where fid=?");
			ps.setString(1, fid);
			
			ResultSet res = ps.executeQuery();
			
			while(res.next())
			{
				VBox vbox = new VBox();
				Label cName = new Label();
				//fid = res.getString(3);
				classCode = res.getString(3);
				className = res.getString(1) ;
				String classcode = classCode ; 
				
				PreparedStatement ps1 = con.prepareStatement("Select sid from classes where classcode = ?");
				ps1.setString(1, classCode);
				ResultSet res1 = ps1.executeQuery();
				if(res1.next())
				{
					sid = res1.getString(1);
					cName.setText(className);
				}
				else
				{
					PreparedStatement ps2 = con.prepareStatement("Select classname from fclass where classcode = ?");
					ps2.setString(1, classCode);
					ResultSet res2 = ps2.executeQuery();
					if(res2.next())
					{
						sid = res2.getString(1);
						cName.setText(className);
					
					
					}
				}
				
				/*
				PreparedStatement ps1 = con.prepareStatement("Select sid from classes where classcode = ?");
				ps1.setString(1, classCode);
				ResultSet res1 = ps1.executeQuery();
				if(res1.next())
				{
					sid = res1.getString(1);
					//cName.setText(className);
				}
				*/
				Button go = new Button("Go");
				go.setOnAction(e -> {
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					
					try
					{
						
						Pane root1 = loader.load(getClass().getResource("/application/Fwork.fxml").openStream());
						FworkController fworkController = (FworkController)loader.getController();
						System.out.println(fworkController);
						
						fworkController.get(classcode);
						
						List<VBox> vb = fworkController.showSubmissions( classcode, primaryStage , fid) ; 
						
						VBox v = new VBox() ; 
						v.getChildren().addAll(vb) ; 
					
						v.setLayoutX(10);
						v.setLayoutY(100);
						root1.getChildren().add(v) ; 
						
						
						Scene scene = new Scene(root1, 1000, 1000);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					}
					catch (IOException ex)
					{
						ex.printStackTrace();
					}  
					
				});
				
				
				vbox.setStyle("-fx-border-color: black ; ");
				
				vbox.setSpacing(20);
				vbox.setStyle("-fx-background-color: white ; ");
				vbox.setPadding(new Insets(2, 2, 2, 2));
				vbox.getChildren().addAll(cName, go);
				vbox.setAlignment(Pos.CENTER);
				list.add(vbox);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	



	
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// TODO Auto-generated method stub
		
	}
	
	

}
