package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.protocol.Resultset;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JoinsController
{
	@FXML
	private TextField txtjoin;

	@FXML
	private Button btnjoin;

	@FXML
	private Label joinstatus;

	String user;

	public void join(String username)
	{
		user = username;
	}

	public void joinC(ActionEvent event)
	{
		String classCode = txtjoin.getText();
		String fid;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classroom", "root", "");

			PreparedStatement ps = con.prepareStatement("select fid from fclass where classcode = ?");
			ps.setString(1, classCode);
			ResultSet res = ps.executeQuery();
			// System.out.println(res.next());
			boolean t = res.next();
			System.out.println(t);
			if (t)
			{
				fid = res.getString(1);
				PreparedStatement ps0 = con.prepareStatement("select * from classes where classcode = ? and sid = ? and fid = ?");
				ps0.setString(1, classCode);
				ps0.setString(2, user);
				ps0.setString(3, fid);
				ResultSet res0 = ps0.executeQuery();
				if (res0.next())
				{
					joinstatus.setText("Already Joined");
				} else
				{

					PreparedStatement ps1 = con.prepareStatement("insert into classes values (?, ?, ?)");
					ps1.setString(1, classCode);
					ps1.setString(2, user);
					ps1.setString(3, fid);
					ps1.executeUpdate();
					joinstatus.setText("Joining Success");
				}
			} else
			{
				joinstatus.setText("Invalid Course");
			}

		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
