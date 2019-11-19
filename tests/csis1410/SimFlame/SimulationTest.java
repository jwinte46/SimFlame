package csis1410.SimFlame;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimulationTest {
   
   @Test
   public void testSimulation() {
      World testWorld = new World(5, 5);
      Simulation testSimulation = new Simulation(testWorld);
   }

   @Test
   public void testStart() {
      World world = new World(10, 10);
      Simulation testSimulation = new Simulation(world);
      
      testSimulation.start();
      assertNotNull(testSimulation.getSimulationTimer());
      }

   @Test
   public void testStop() {
      fail("Not yet implemented");
   }

   @Test
   public void testReset() {
      fail("Not yet implemented");
   }

   @Test
   public void testStep() {
      fail("Not yet implemented");
   }

}
