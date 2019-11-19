import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Yongqi Jia & Qingzhou Pan
 *
 */

public class Connect4Controller {
	private Connect4Model model;
	
	/**
	 * Constructor
	 */
	public Connect4Controller(Connect4Model model){
		this.model = model;
		model.set_circle_list(initialize_circle_list());
	}
	
	/**
	 * initialize the circle list in model
	 * @return the result circle list 
	 */
	private ArrayList<ArrayList<Integer>> initialize_circle_list() {
		ArrayList<ArrayList<Integer>> circle_list = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < 7; i++) {
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for(int j = 0; j < 6; j++) {
				tmp.add(0);
			}
			circle_list.add(tmp);
		}
		return circle_list;
	}

	
	/**
	 * handle human turn.
	 * Put circle with right color on the right place.
	 * @param col col is index of gridpane
	 */
	public void humanTurn(int col) {
		Connect4.human_turn = false;
		int column = find_column(col);
		int row = 0;
		int color = 0;
		// find the lowest position of the column
		for(int pos = 0; pos < 6; pos++) {
			if(model.get_circle_list().get(column).get(pos) == 0) {
				row = Math.max(row, pos);
			}
		}
		// determine which color
		if(Connect4Link.CREATE.equals("server")){
			color = 1;
		}else if(Connect4Link.CREATE.equals("client")) {
			color = 2;
		}
		// update the circle in the circle list of model
		model.get_circle_list().get(column).set(row, color);
		Connect4MoveMessage move = new Connect4MoveMessage(row, column, color);
		try {
			Connect4.link.output(move);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.redrawn(move);
	}
	
	/**
	 * handle computer turn.
	 * Put one circle with right color on the random place.
	 */
	public void computerTurn() {
		Connect4.human_turn = false;
		int row = -1;
		int color = 0;
		int column;
		// find a random empty position
		while(true) {
			Random rand = new Random();
			column = rand.nextInt(7);
			for(int pos = 0; pos < 6; pos++) {
				if(model.get_circle_list().get(column).get(pos) == 0) {
					row = Math.max(row, pos);
				}
			}
			if(row != -1) {
				break;
			}
		}
		if(Connect4Link.CREATE.equals("server")){
			color = 1;
		}else if(Connect4Link.CREATE.equals("client")) {
			color = 2;
		}
		model.get_circle_list().get(column).set(row, color);
		Connect4MoveMessage move = new Connect4MoveMessage(row, column, color);
		try {
			Connect4.link.output(move);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.redrawn(move);
	}
	
	/**
	 * update board
	 * @param move move is the Connect4MoveMessage obejct
	 */
	public void move(Connect4MoveMessage move) {
		Connect4.human_turn = true;
		model.get_circle_list().get(move.getColumn()).set(move.getRow(), move.getColor());
		model.redrawn(move);
	}

	/**
	 * find the correct column of the grid pane according to the index
	 * @param col col is the index x of grid pane
	 * @return the correct column
	 */
	private int find_column(int col) {
		int column = 0;
		if(col >=0 && col < 52) {
			column = 0;
		}else if(col >= 52 && col < 100) {
			column = 1;
		}else if(col >= 100 && col < 148) {
			column = 2;
		}else if(col >= 148 && col < 196) {
			column = 3;
		}else if(col >= 196 && col < 244) {
			column = 4;
		}else if(col >= 244 && col < 292) {
			column = 5;
		}else if(col >= 292 && col <= 344) {
			column = 6;
		}
		return column;
	}

	/**
	 * Check whether the column is full
	 * @param col col is the target column
	 * @return whether it is full
	 */
	public boolean check_error(int col) {
		int column = find_column(col);
		for(int pos : model.get_circle_list().get(column)) {
			if(pos == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check whether the game is over.
	 * @return the result
	 */
	public int isOver() {
		int not_over = 0;
		ArrayList<ArrayList<Integer>> circle_list = model.get_circle_list();
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				int target = circle_list.get(i).get(j);
				// check horizontally
				if(i + 3 <= 6 && target != 0) {		
					if(target == circle_list.get(i+1).get(j) && 
					   target == circle_list.get(i+2).get(j) && 
					   target == circle_list.get(i+3).get(j)) {
						//check who win
						if(target == 1) {
							return 1;
						}else if(target == 2) {
							return 2;
						}
					}	
				}
				// check vertically
				if(j + 3 <= 5 && target != 0) {
					if(target == circle_list.get(i).get(j+1) && 
					   target == circle_list.get(i).get(j+2) && 
					   target == circle_list.get(i).get(j+3)) {
						//check who win
						if(target == 1) {
							return 1;
						}else if(target == 2) {
							return 2;
						}
					}
				}

				// check diagonally down_left
				if(i - 3 >= 0 && j + 3 <= 5 && target != 0) {
					if(target == circle_list.get(i-1).get(j+1) && 
					   target == circle_list.get(i-2).get(j+2) && 
					   target == circle_list.get(i-3).get(j+3)) {
						//check who win
						if(target == 1) {
							return 1;
						}else if(target == 2) {
							return 2;
						}
					}
				}
				// check diagonally down_right
				if(i + 3 <= 6 && j + 3 <= 5 && target != 0) {
					if(target == circle_list.get(i+1).get(j+1) && 
					   target == circle_list.get(i+2).get(j+2) && 
					   target == circle_list.get(i+3).get(j+3)) {
						//check who win
						if(target == 1) {
							return 1;
						}else if(target == 2) {
							return 2;
						}
					}
				}
			}
		}
		return not_over;
	}
}
