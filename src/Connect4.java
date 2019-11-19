import javafx.application.Application;

/**
 * 
 * @author Yongqi Jia & Qingzhou Pan
 *
 */
public class Connect4 {
	public static Connect4Link link;
	public static boolean human_turn = false;
	public static void main(String[] args) {
		Application.launch(Connect4View.class, args);
	}
}
