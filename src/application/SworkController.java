package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import com.mysql.cj.protocol.Resultset;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SworkController implements Initializable
{
	String path;
	String wname;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// TODO Auto-generated method stub

	}

	public List<VBox> showWork(String sids, String classc, Stage stage)
	{
		String classCode = classc;
		String sid = sids;
		Label prompt = new Label();
		List<VBox> v = new ArrayList<VBox>();
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classroom", "root", "");

			PreparedStatement ps = con.prepareStatement("select * from fdashboard where classcode=? ");
			ps.setString(1, classc);

			ResultSet res = ps.executeQuery();

			while (res.next())
			{
				VBox vbox = new VBox();
				Label wlbl = new Label();
				Label sfilelbl = new Label();
				sfilelbl.setStyle("-fx-background-color : white;");
				
				
				String wid = res.getString(4);
				PreparedStatement ps2 = con.prepareStatement("select workname from fdashboard where classcode=? and workid = ?");
				ps2.setString(1, classc);
				ps2.setString(2, wid);
				ResultSet res0 = ps2.executeQuery();
				if(res0.next())
				{
					wname = res0.getString(1);
					wlbl.setText(wname);
					vbox.getChildren().add(wlbl);
				}
				
				//filechooser and getting the absolute path of the chosen file
				FileChooser chooser = new FileChooser();
				Button choose = new Button("Select");
				choose.setOnAction(e -> {
					File selected = chooser.showOpenDialog(stage);
					path = selected.getAbsolutePath();
					sfilelbl.setText(selected.getAbsolutePath());
					
				});
				
				//Submit button and entering the file in database
				Button submit = new Button("Submit");
				Label submitStatus = new Label();
				submit.setOnAction(e -> {
					try
					{

						PreparedStatement ps1 = con.prepareStatement("select * from sdashboard where sid=? and classcode = ? and workid = ?");
						ps1.setString(1, sids);
						ps1.setString(2, classc);
						ps1.setString(3, wid);
						ResultSet res1 = ps1.executeQuery();
						if(res1.next())
						{
							PreparedStatement ps3 = con.prepareStatement("Update sdashboard set sfile = LOAD_FILE(?) , status = ? where sid = ? and classcode = ? and workid = ?");
							ps3.setString(1, path);
							ps3.setInt(2, 1);
							ps3.setString(3, sids);
							ps3.setString(4, classc);
							ps3.setString(5, wid);
							ps3.executeUpdate();
							submitStatus.setText("Update Success");
						}
						else
						{
							PreparedStatement ps3 = con.prepareStatement("Insert into sdashboard values (?, ?, ?, LOAD_FILE(?), ?, ?)");
							
							ps3.setString(1, sids);
							ps3.setString(2,  classc);
							ps3.setString(3, wid);
							ps3.setString(4, path);
							ps3.setInt(5, 0);
							ps3.setInt(6, 1);
							ps3.executeUpdate();
							submitStatus.setText("Upload Success");
							
							
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					
				});
				Label downloadLbl = new Label();
				
				downloadLbl.setStyle("-fx-background-color : white;");
				//downloading the project file
				Button view = new Button("View Project");
				view.setOnAction(e -> {
					String path4 = "E:\\" + wname + "_" + sid + ".png";
					FileOutputStream fos;
					try
					{
						PreparedStatement ps1 = con.prepareStatement("select * from fdashboard where  classcode = ? and workid = ?");
						ps1.setString(1, classc);
						ps1.setString(2, wid);
						ResultSet res1 = ps1.executeQuery();
						if(res1.next())
						{
						fos = new FileOutputStream(path4);
						Blob blob = res1.getBlob(2);
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
				
				HBox hb = new HBox();
				hb.getChildren().add(choose);
				hb.getChildren().add(submit);
				hb.setSpacing(5);
				

				vbox.getChildren().add(view);
				vbox.getChildren().add(downloadLbl);
				vbox.getChildren().add(prompt);
				vbox.getChildren().add(sfilelbl);
				vbox.getChildren().add(hb);
				vbox.getChildren().add(submitStatus);
				vbox.setSpacing(20);
				v.add(vbox);
				
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return v;
	}

}
