package csis1410.SimFlame;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class WorldTest {

   @Test
   public void testWorld() {
      
      boolean exceptionThrown = false;
      
      try {
         World world1 = new World(-9, -7);
      } catch(IndexOutOfBoundsException e) {
         exceptionThrown = true;
      }
      
      assertTrue(exceptionThrown);
      
      
      World world2 = new World(9, 7);
      assertEquals(world2.getWidth(), 9);
      assertEquals(world2.getHeight(), 7);
   }

   @Test
   public void testSwapHeatMap() {
      boolean exceptionThrown = false;
      
      World testWorld1 = new World(10, 2);
      try {
         testWorld1.swapHeatMap(new double[19]);
      } catch(IllegalArgumentException e) {
         exceptionThrown = true;
      }
      
      assertTrue(exceptionThrown);
      
      double[] newMap = new double[20];
      newMap[0] = 1.0;
      World testWorld2 = new World(10, 2);
      newMap = testWorld2.swapHeatMap(newMap);
      
      assertFalse(Arrays.equals(newMap, testWorld2.getHeatMap()));
      
   }

   @Test
   public void testPointToIndex() {
      World testWorld = new World(6, 3);
      Point testPoint1 = new Point(0, 0);
      Point testPoint2 = new Point(2, 1);
      assertEquals(testWorld.pointToIndex(testPoint1), 0);
      assertEquals(testWorld.pointToIndex(testPoint2), 8);
   }

}
