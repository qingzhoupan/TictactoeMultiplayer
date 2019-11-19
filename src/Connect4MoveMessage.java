import java.io.Serializable;

/**
 * This class contains the information for transporting posiiton and color.
 * @author Yongqi Jia & Qingzhou Pan
 *
 */

public class Connect4MoveMessage implements Serializable {

       public static int YELLOW = 1;

       public static int RED = 2;

 

       private static final long serialVersionUID = 1L;

 

       private int row;

       private int col;

       private int color;

 

       public Connect4MoveMessage(int row, int col, int color) {
    	   this.row = row;
    	   this.col = col;
    	   this.color = color;
       }

       /**
        * Row position getter
        * @return which row
        */
       public int getRow() {
    	   return row;
       }

       /**
        * Column position getter
        * @return which Column
        */
       public int getColumn() {
    	   return col;
       }

       /**
        * Color getter
        * @return which color
        */
       public int getColor() {
    	   return color;
       }

}
