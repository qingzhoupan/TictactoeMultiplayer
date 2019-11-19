import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

public class test {
	
	@Test
	public void put() {
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		controller.check_error(3);
		controller.check_error(500);
		Connect4MoveMessage move = new Connect4MoveMessage(1,1,1);
		model.redrawn(move);
		assertTrue(move.getColor() == 1);
		assertTrue(move.getColumn() == 1);
		assertTrue(move.getRow() == 1);
		controller.isOver();
		controller.move(move);
		
		controller.humanTurn(3);
		controller.computerTurn();
	}
	
	@Test
	public void test_isOver_H() {
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		model.get_circle_list().get(0).set(0, 1);
		model.get_circle_list().get(1).set(0, 1);
		model.get_circle_list().get(2).set(0, 1);
		model.get_circle_list().get(3).set(0, 1);
		controller.isOver();
		model.get_circle_list().get(0).set(0, 2);
		model.get_circle_list().get(1).set(0, 2);
		model.get_circle_list().get(2).set(0, 2);
		model.get_circle_list().get(3).set(0, 2);
		controller.isOver();
	}
	
	@Test
	public void test_isOver_V() {
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		model.get_circle_list().get(0).set(0, 1);
		model.get_circle_list().get(0).set(1, 1);
		model.get_circle_list().get(0).set(2, 1);
		model.get_circle_list().get(0).set(3, 1);
		controller.isOver();
		model.get_circle_list().get(0).set(0, 2);
		model.get_circle_list().get(0).set(1, 2);
		model.get_circle_list().get(0).set(2, 2);
		model.get_circle_list().get(0).set(3, 2);
		controller.isOver();
	}
	
	@Test
	public void test_isOver_D1() {
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		model.get_circle_list().get(0).set(0, 1);
		model.get_circle_list().get(1).set(1, 1);
		model.get_circle_list().get(2).set(2, 1);
		model.get_circle_list().get(3).set(3, 1);
		controller.isOver();
		model.get_circle_list().get(0).set(0, 2);
		model.get_circle_list().get(1).set(1, 2);
		model.get_circle_list().get(2).set(2, 2);
		model.get_circle_list().get(3).set(3, 2);
		controller.isOver();
	}
	
	@Test
	public void test_isOver_D2() {
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		model.get_circle_list().get(3).set(0, 1);
		model.get_circle_list().get(2).set(1, 1);
		model.get_circle_list().get(1).set(2, 1);
		model.get_circle_list().get(0).set(3, 1);
		controller.isOver();
		model.get_circle_list().get(3).set(0, 2);
		model.get_circle_list().get(2).set(1, 2);
		model.get_circle_list().get(1).set(2, 2);
		model.get_circle_list().get(0).set(3, 2);
		controller.isOver();
	}
	
	@Test
	public void test_check_error() {
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		model.get_circle_list().get(0).set(0, 1);
		model.get_circle_list().get(0).set(1, 1);
		model.get_circle_list().get(0).set(2, 1);
		model.get_circle_list().get(0).set(3, 1);
		model.get_circle_list().get(0).set(4, 1);
		model.get_circle_list().get(0).set(5, 1);
		controller.check_error(0);
		controller.check_error(53);
		controller.check_error(101);
		controller.check_error(149);
		controller.check_error(197);
		controller.check_error(245);
		controller.check_error(293);
	}
	
	@Test
	public void test_turn() {
		Connect4.main(null);
		Connect4Model model = new Connect4Model();
		Connect4Controller controller = new Connect4Controller(model);
		Connect4Link.CREATE = "server";
		controller.computerTurn();
	}	
}
