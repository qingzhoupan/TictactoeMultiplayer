import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Yongqi Jia & Qingzhou Pan
 *
 */

public class Connect4Model extends java.util.Observable{
	private ArrayList<ArrayList<Integer>> circle_list;
	
	/**
	 * Constructor
	 */
	public Connect4Model(){
		circle_list = new ArrayList<ArrayList<Integer>>();
	}
	
	/**
	 * Set circle list
	 * @param circle_list circle_list is the board contains all circles
	 */
	public void set_circle_list(ArrayList<ArrayList<Integer>> circle_list) {
		this.circle_list = circle_list;
	}
	
	/**
	 * Circle list getter
	 * @return circle_list
	 */
	public ArrayList<ArrayList<Integer>> get_circle_list(){
		return this.circle_list;
	}
	
	/**
	 * put one circle on the board
	 * @param move move is the Connect4MoveMessage object that contains all information to put the circle
	 */
	public void redrawn(Connect4MoveMessage move) {
		setChanged();
		notifyObservers(move);	
	}
}
