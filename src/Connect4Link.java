import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Yongqi Jia & Qingzhou Pan
 *
 */

public class Connect4Link {
	private static ObjectInputStream input;
	private static ObjectOutputStream output;
	private static Connect4Controller controller;
	private static boolean close = false;
	
	private static Socket connection;
	public static String CREATE;
	public static String PLAY_AS;
	public static String SERVER;
	public static String PORT;
	
	/**
	 * Constructor of Connect4Link
	 * @param controller controller is the Connect4Controller obejct
	 */
	public Connect4Link(Connect4Controller controller) {
		super();
		this.controller = controller;
	}
	
	/**
	 * create link between server and client
	 */
	public static void create_link() {
		try {
			if(CREATE.equals("server")) {
				ServerSocket server = new ServerSocket(Integer.parseInt(PORT));
				connection = server.accept();
			}else if(CREATE.equals("client")) {
				connection =  new Socket(SERVER, Integer.parseInt(PORT));
			}
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());			

			// if it is the human turn and player is computer, play computer round
			if(Connect4.human_turn && PLAY_AS.equals("Computer")) {
				controller.computerTurn();
			}
			
			while (!close) {
				Connect4MoveMessage move = (Connect4MoveMessage) input.readObject();
				controller.move(move);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		      	e.printStackTrace();
		}
	}
	
	/**
	 * write the move message into the output stream
	 * @param move move is the Connect4MoveMessage object that contains the position and color
	 * @throws IOException
	 */
	public void output(Connect4MoveMessage move) throws IOException {
		output.writeObject(move);
	}	
}
