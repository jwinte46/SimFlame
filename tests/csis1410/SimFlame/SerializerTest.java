package csis1410.SimFlame;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.Test;

public class SerializerTest {

	@Test
	public void testSaveAndLoad() throws FileNotFoundException {
		
		World testWorld = new World(100, 100);
		testWorld.addFuelAt(new Point(0, 0));
		
		Serializer.save(testWorld, "saveTest.txt");
		
		World testWorld2 = Serializer.load("saveTest.txt");
		
	    assertTrue(testWorld.getFuelSet().contains(new Point(0, 0)) &&
	    		   testWorld2.getFuelSet().contains(new Point(0, 0)));		
	}

}
