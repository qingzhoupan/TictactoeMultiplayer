import java.util.ArrayList;
import java.util.Observable;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
/**
 * 
 * @author Yongqi Jia & Qingzhou Pan
 *
 */


public class Connect4View extends Application implements java.util.Observer{
	private Connect4Controller controller;
	private ArrayList<ArrayList<Circle>> shape_list; 
	private Alert error;

	/**
	 * Constructor
	 */
	public Connect4View(){
		super();
		shape_list = new ArrayList<ArrayList<Circle>>();
		error = new Alert(AlertType.ERROR);
		Connect4Model model = new Connect4Model();
		controller = new Connect4Controller(model);
		Connect4.link = new Connect4Link(controller);
		model.addObserver(this);
	}
	
	/**
	 * Automatically called by Observer and change the 
	 * view according to the information which was stored at model. 
	 * 
	 * And then, check whether the game is over.
	 */
	@Override
	public void update(Observable o, Object arg) {
		Connect4MoveMessage move = (Connect4MoveMessage) arg;
		Color color = null; 
		if(move.getColor() == 1) {
			color = Color.YELLOW;
		}else if(move.getColor() == 2) {
			color = Color.RED;
		}
		
		shape_list.get(move.getColumn()).get(move.getRow()).setFill(color);
		
		Platform.runLater(() -> {
			if(controller.isOver() == 1 ) {
				if(Connect4.link.CREATE.equals("server")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Message");
					alert.setHeaderText("Message");
					alert.setContentText("You(" + Connect4Link.CREATE + " play as " + Connect4Link.PLAY_AS + ") won!");
					alert.showAndWait();
					System.exit(1);
				}else if(Connect4.link.CREATE.equals("client")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Message");
					alert.setHeaderText("Message");
					alert.setContentText("You(" + Connect4Link.CREATE + " play as " + Connect4Link.PLAY_AS + ") lost!");
					alert.showAndWait();
					System.exit(1);
				}	
			}else if(controller.isOver() == 2 ) {
				if(Connect4.link.CREATE.equals("server")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Message");
					alert.setHeaderText("Message");
					alert.setContentText("You(" + Connect4Link.CREATE + " play as " + Connect4Link.PLAY_AS + ") lost!");
					alert.showAndWait();
					System.exit(1);
				}else if(Connect4.link.CREATE.equals("client")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Message");
					alert.setHeaderText("Message");
					alert.setContentText("You(" + Connect4Link.CREATE + " play as " + Connect4Link.PLAY_AS + ") won!");
					alert.showAndWait();
					System.exit(1);
				}	
			}	
			if (Connect4.link.PLAY_AS.equals("Computer") && Connect4.human_turn) {
				controller.computerTurn();
			}
	    });
		
	}

	/**
	 * overridding the start method.
	 * Draw the board
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Connect4");
		BorderPane board = new BorderPane();
		
		MenuBar mb = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem mi = new MenuItem("New Game");
		// set action on mi button
		mi.setOnAction(e -> {
			Connect4Dialog dialog = new Connect4Dialog();
			dialog.showdialog();
		});
		menu.getItems().add(mi);
		mb.getMenus().add(menu);
		board.setTop(mb);
		
		Scene scene = new Scene(board, 344, 324);
		primaryStage.setScene(scene);
		drawGridPane(board);	
		primaryStage.show();
	}

	/**
	 * Draw the grid pane and set the mouse click event.
	 * @param board board is the borderpane which contains all
	 */
	private void drawGridPane(BorderPane board) {
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-background-color:blue;");
		gridPane.setOnMouseClicked(e-> {
			if(Connect4.human_turn) {
				int col = (int) e.getX();
				if(controller.check_error(col)) {
					controller.humanTurn(col);
				}else {
					error.setTitle("Error");
					error.setHeaderText("Error");
					error.setContentText("Column full, pick somewhere else!");
					error.showAndWait();
				}
			}
		});
		// draw all the white circles
		for(int i = 0; i < 7; i++) {
			ArrayList<Circle> tmp = new ArrayList<Circle>();
			for(int j = 0; j < 6; j++) {
				Circle c = new Circle(10, 10, 20);
				tmp.add(c);
				c.setFill(Color.WHITE);
				gridPane.add(c, i, j);
			}
			shape_list.add(tmp);
		}
		gridPane.setVgap(8);
		gridPane.setHgap(8);
		gridPane.setPadding(new Insets(8, 8, 8, 8)); 
		board.setCenter(gridPane);
	}

}
