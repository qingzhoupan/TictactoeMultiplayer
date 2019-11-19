import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Yongqi Jia & Qingzhou Pan
 *
 */

public class Connect4Dialog extends Stage{
	
	/**
	 * Allow the user to select which role they will handle
	 *  and whether they will be a human player or if your AI will be playing.
	 *  When the user clicks OK or Cancel, have the class provide methods 
	 *  to query the object for what the user selected.
	 *  Default the server to localhost and the port to 4000.
	 */
	public void showdialog(){
		this.initModality(Modality.APPLICATION_MODAL);
		this.setTitle("Network Setup");
		
		// draw the first column of gridpane
		GridPane gridPane = new GridPane();
		Label create = new Label("Create: ");
		Label play_as = new Label("Play as: ");
		Label server = new Label("Server");
		Button ok = new Button("OK");
		gridPane.add(create, 0, 0);
		gridPane.add(play_as, 0, 1);
		gridPane.add(server, 0, 2);
		gridPane.add(ok, 0, 3);
		
		// draw the second column of gridpane
		ToggleGroup create_group = new ToggleGroup();
		RadioButton create_server = new RadioButton("Server");
		RadioButton create_client = new RadioButton("Client");
		create_server.setToggleGroup(create_group);
		create_client.setToggleGroup(create_group);
		create_server.setSelected(true);
		create_server.setUserData("server");
		create_client.setUserData("client");
		gridPane.add(create_server, 1, 0);
		gridPane.add(create_client, 2, 0);
		
		// draw the third column of gridpane
		ToggleGroup playas_group = new ToggleGroup();
		RadioButton playas_human = new RadioButton("Human");
		RadioButton playas_computer = new RadioButton("Computer");
		playas_human.setToggleGroup(playas_group);
		playas_computer.setToggleGroup(playas_group);
		playas_human.setSelected(true);
		playas_human.setUserData("Human");
		playas_computer.setUserData("Computer");
		gridPane.add(playas_human, 1, 1);
		gridPane.add(playas_computer, 2, 1);
		
		// draw the localhost and port textfields
		TextField localhost = new TextField("localhost");
		Label port = new Label("              Port");
		TextField port_text = new TextField("4000");
		localhost.prefWidth(100);
		gridPane.add(localhost, 1, 2);
		gridPane.add(port, 2, 2);
		gridPane.add(port_text, 3, 2);
		
		Button cancel = new Button("Cancel");
		gridPane.add(cancel, 1, 3);
		
		// set action on ok button
		ok.setOnAction(e -> {
			Connect4Link.CREATE = (String) create_group.getSelectedToggle().getUserData();
			Connect4Link.PLAY_AS = (String) playas_group.getSelectedToggle().getUserData();
			Connect4Link.SERVER = localhost.getText();
			Connect4Link.PORT = port_text.getText();
			if (Connect4.link.CREATE.equals("server")) {
				Connect4.human_turn = true;
			}else {
			}
			Task<Void> task = new Task<Void>() {
			    @Override 
			    public Void call() {
			    	System.out.println("in");
			    	Connect4.link.create_link();
			        return null;
			    }
			};
			new Thread(task).start();
			this.close();
		});
		
		// set action on cancel button
		cancel.setOnAction(event -> {
			this.close();
		});
		
		gridPane.setVgap(25);
		gridPane.setHgap(10);
		gridPane.setPadding(new Insets(20, 20, 20, 20)); 
		Scene scene = new Scene(gridPane);
		this.setScene(scene);
		this.showAndWait();
			
	}
}
