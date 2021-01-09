package application;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

public class FworkController implements Initializable
{

	public static String path;
	@FXML
	private Label classCodeLbl;

	// @FXML
	// private Label addworkLbl ;

	String classCode, fid , grade;

	/*
	public void backbutton(ActionEvent event )
	{
		
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide() ; 
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/Fdashboard.fxml").openStream());
			
			FdashboardController fdashboardcontroller = (FdashboardController)loader.getController() ; 
			
			
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
			
			Pane root = loader.load(getClass().getResource("/application/FWork.fxml").openStream());
			
			FworkController fcontroller = (FworkController)loader.getController() ; 
			
			
			Scene scene = new Scene(root, 1000, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	*/

	

	
	
	
	
	
	
	
	
	
	
	
	public void get(String classcode)
	{

		classCodeLbl.setText(classcode);

	}
	
	
	

	public void addwork(ActionEvent event)
	{

		try
		{

			// ((Node)event.getSource()).getScene().getWindow().hide() ;

			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/application/Faddwork.fxml").openStream()); // use the
																										// xmlfile

			FaddworkController faddworkController = (FaddworkController) loader.getController();
			Button but = faddworkController.get(classCode, fid, primaryStage);
			VBox vbox = new VBox();
			vbox.getChildren().add(but);
			vbox.setLayoutX(172);
			vbox.setLayoutY(300);
			root.getChildren().add(vbox);

			Scene scene = new Scene(root, 1000, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// TODO Auto-generated method stub

	}

	public List<VBox> showSubmissions(String classc, Stage stage, String facid)
	{
		classCode = classc;
		fid = facid;
		// String fid = fids;
		List<VBox> v = new ArrayList<VBox>();

		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classroom", "root", "");

			PreparedStatement ps = con.prepareStatement("select * from sdashboard where classcode = ? order by workid");
			// ps.setString(1, fid);
			ps.setString(1, classc);

			ResultSet res = ps.executeQuery();

			while (res.next())
			{
				VBox vbox = new VBox();
				Label wlbl = new Label();
				Label sfilelbl = new Label();
				Label gradeLbl = new Label() ; 

				Label showgradeLbl = new Label() ; 
				
				Label sidLbl = new Label() ; 
				
				Label wnameLbl = new Label() ; 
				
				String wid = res.getString(3);
				String sid = res.getString(1);
				sidLbl.setText(sid);
				
				
				wlbl.setText(wid);
				vbox.getChildren().add(wlbl);
				
				TextField grader = new TextField() ; 
				
				Label downloadLbl = new Label();

				Button view = new Button("View Project");
				view.setOnAction(e -> {
					String path = "E:\\" + wid + "_" + sid + ".png";
					FileOutputStream fos;
					String path1 = path;
					try
					{
						PreparedStatement ps1 = con
								.prepareStatement("select * from sdashboard where classcode = ? and workid = ?");

						ps1.setString(1, classc);
						ps1.setString(2, wid);
						ResultSet res1 = ps1.executeQuery();
						if (res1.next())
						{
							fos = new FileOutputStream(path1);
							Blob blob = res1.getBlob(4);
							byte b[] = blob.getBytes(1, (int) blob.length());
							fos.write(b);
							fos.close();
							downloadLbl.setText("Download Success");

						}
					} catch (Exception e1)
					{
						e1.printStackTrace();
					}
				});
				
				Button submit = new Button("grade") ; 
				submit.setOnAction(e -> {
					try {
						grade = grader.getText() ; 
						showgradeLbl.setText(grade) ; 
						PreparedStatement ps2 = con.prepareStatement("update sdashboard set grade = ? where sid = ? and classcode = ? and workid = ?  ");
						ps2.setString(1, grade);
						ps2.setString(2, sid);
						//String classcode;
						ps2.setString(3 ,classCode) ; 
						ps2.setString(4, wid);
						int res2 = ps2.executeUpdate() ; 
						
						if(res2 == 1)
						{
							gradeLbl.setText("graded SuccessFully");
						}
						else {
							
							gradeLbl.setText("try again");
							
						}
						
						
					}catch(Exception ex)
					{
						ex.printStackTrace();
					}
					
					
				});
				
				showgradeLbl.setText(grade) ; 
				PreparedStatement ps3 = con.prepareStatement("select workname from  fdashboard  where classcode = ? and workid = ?  ");
				ps3.setString(1, classCode);
				ps3.setString(2, wid);
				
				ResultSet res3 = ps3.executeQuery() ; 
				
				if(res3.next())
				{
					wnameLbl.setText(res3.getString(1)) ;  
				}
				else
				{
					wnameLbl.setText("no work id");
				}
				
				
				sfilelbl.setStyle("-fx-background-color : white;");
				downloadLbl.setStyle("-fx-background-color : white;");
				//vbox.setSpacing(10);
				vbox.getChildren().add(wnameLbl) ; 
				vbox.getChildren().add(view);
				//vbox.getChildren().add(workname); 
				vbox.getChildren().add(sidLbl) ; 
				vbox.getChildren().add(downloadLbl);
				vbox.getChildren().add(sfilelbl);
				// vbox.getChildren().add(choose);
				vbox.getChildren().add(grader);
				vbox.getChildren().add(submit);
				vbox.getChildren().add(gradeLbl) ; 
				vbox.getChildren().add(showgradeLbl) ; 
				vbox.setSpacing(10);
				vbox.setStyle("-fx-background-color : white ;");
				
				v.add(vbox);

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return v;
	}

}
