package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CreatefController implements Initializable {

 
	
	@FXML 
	private Label fidLbl ;
	
	@FXML
	private Label submitLbl ; 
	
	@FXML 
	private TextField classnameText ; 
	
	@FXML
	private Label classcodeLbl ; 
	
	String fid , classcode ; 
	
	
	/*

	public void refreshbutton(ActionEvent event )
	{
		
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide() ; 
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/Createf.fxml").openStream());
			
			CreatefController createfcontroller = (CreatefController)loader.getController() ; 
			
			
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
			
			Pane root = loader.load(getClass().getResource("/application/Fdashboard.fxml").openStream());
			
			FdashboardController fcontroller = (FdashboardController)loader.getController() ; 
			
			
			Scene scene = new Scene(root, 1000, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
	
	
	
	
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
		classcode = RandomString.getAlphaNumericString(10) ; 
		classcodeLbl.setText(classcode);
		
	} 
	
	
	public void createclass(ActionEvent event) throws SQLException
	{
		String classname = classnameText.getText() ;
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classroom", "root", "");

		//Statement stmt = con.createStatement();

		//PreparedStatement ps = con.prepareStatement("select * from student where sid=? and password=?");

		//ps.setString(1, );
		//ps.setString(2, );
		/*
		ResultSet res = stmt.executeQuery("INSERT INTO fclass VALUES (classname, fid,classcode )");
		
		 while ( res.next() ) {
             //String lastName = res.getString("Lname");
             //System.out.println(lastName);
			 
			 submitLbl.setText("success");
			 
         }
         con.close();
		*/
		String query = "INSERT INTO fclass Values(? , ? , ?) " ; 
		PreparedStatement st = con.prepareStatement(query) ; 
		st.setString( 1  , classname) ; 
		st.setString(2 , fid);
		st.setString(3 ,  classcode);
		
		int res =  st.executeUpdate() ;
		
		if(res == 0 )
		{
			System.out.println("try again later");
			submitLbl.setText("try again later");
		}
		else
		{
			System.out.println("success");
			submitLbl.setText("success");
		}
		
		st.close() ; 
		
		
		
		
		
		
	}
	
	
	public void getclasscode(String facultyid)
	{
		fid = facultyid ; 
		//System.out.println(fid);
		fidLbl.setText(fid);
	}
	
	
	
	
}
