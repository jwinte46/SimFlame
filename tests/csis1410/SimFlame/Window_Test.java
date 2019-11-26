package csis1410.SimFlame;

import static org.junit.Assert.*;

import org.junit.Test;

public class Window_Test {

	@Test
	public void test1() {
		Simulation simulation1 = new Simulation(new World(100, 100));
		Window testWindow1 = new Window(simulation1);
		testWindow1.setVisible(true);
		assertTrue(testWindow1.isVisible());
	}
	
	@Test
	public void test2() {
		Simulation simulation2 = new Simulation(new World(170,170));
		Window testWindow2 = new Window(simulation2);
		testWindow2.setVisible(true);
		assertTrue(testWindow2.isVisible());
	}
	
	@Test
	public void test3() {
		Simulation simulation3 = new Simulation(new World(75,75));
		Window testWindow3 = new Window(simulation3);
		testWindow3.setVisible(true);
		assertTrue(testWindow3.isVisible());
	}

}
