 package application;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileChooserController {
	
	@FXML
	private Button btn1 ; 
	
	@FXML
	private ListView listview ; 
	
	public void ButtonAction(ActionEvent event) {
		
		FileChooser fc = new FileChooser() ; 
		fc.setInitialDirectory(new File ("C:\\Users"));
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files" , "*.jpeg", "*.jpg"));
		List<File> selectedFiles = fc.showOpenMultipleDialog(null) ; 
		
		if(selectedFiles != null ) {
			for(int i = 0 ; i < selectedFiles.size(); i++)
			{
			listview.getItems().add(selectedFiles.get(i).getAbsolutePath()) ; 
			}
		}
		else {
			System.out.println("not valid file");
		}
		
	}
	
	
}
