package csis1410.SimFlame;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simulation of flame
 * 
 * @author Tim Hansen
 */
public class Simulation {
   
   // Fields
   
   private World world; // the world which this simulation operates on
   
   private double[] secondHeatMap; /* the simulation reads the data from the world's heatmap, and writes into
                                      its own secondHeatMap array. Then it swaps heatMaps with the World.*/
   private Timer simulationTimer; // responsible for calling the step() method at a fixed interval
   private int simulationPeriod; // the number of milliseconds between steps
   private double coolingRate = 0.04;
   private double diffusionRate = 0.5;
   
   // Private Classes
   
   /**
    * TimerTask that progresses the simulation
    * 
    * @author Tim Hansen
    */
   private class SimulationTimerTask extends TimerTask {
      /**
       * The method to be executed when this TimerTask is triggered
       * 
       * Responsible for calling the Simulation's {@link #step() step} method
       */
      @Override
      public void run() {
         step();
      }
   }
   
   // Constructors
   
   /**
    * Constructor for the Simulation
    */
   public Simulation(World world) {
      this.world = world;
      simulationPeriod = 17; // default to stepping every 17 milliseconds
      simulationTimer = null;
      secondHeatMap = new double[world.getWidth() * world.getHeight()];
   }
   
   // Methods 
   
   /**
    * Starts the simulation
    */
   public void start() {
      simulationTimer = new Timer();
      simulationTimer.schedule(new SimulationTimerTask(), 0, simulationPeriod);
   }
   
   /**
    * Stops the simulation
    */
   public void stop() {
      simulationTimer.cancel();
   }
   
   /**
    * Resets the simulation to its initial state
    */
   public void reset() {
      simulationTimer.cancel();
      world.resetHeat();
   }
   
   /**
    * One "step" of the simulation
    * 
    * Gets called repeatedly by simulationTimer. Is responsible for progressing the simulation.
    */
   public void step() {
      Set<Point> fuel = world.getFuelSet();
      synchronized(fuel) {
         seed(); // make fuel hot
         for(int i = 0; i < world.getWidth() * world.getHeight(); i++) {
            convect(i); // make heat rise
            diffuse(i); // smooth heat values
            cool(i); // cool off         
         }
         secondHeatMap = world.swapHeatMap(secondHeatMap);
      }
   }
   
   /**
    * Makes the areas with fuel hot
    */
   public void seed() {
      // there's a bug in here. for some reason a ConcurrentModificationException gets thrown
      for(Point el : world.getFuelSet()) {
         int x = el.getX();
         int y = el.getY();
         secondHeatMap[world.pointToIndex(new Point(x, y))] = 1.0;
      }
   }
   
   /**
    * Makes the heat rise
    * 
    * @param i the index
    */
   public void convect(int i) {
      Point p = world.indexToPoint(i); // the current index, translated to a point
      // don't convect on fuel or on the last row
      if(world.getFuelSet().contains(p) ||
         p.getY() == world.getHeight() - 1) {
         return;
      } else {
         // make the heat at (x,y) be the same as (x,y+1)
         secondHeatMap[i] = world.getHeatAt(i + world.getWidth());
      }
      
   }
   
   /**
    * Makes the heat disperse 
    * 
    * @param i the index
    */
   public void diffuse(int i) {
      int[] neighbors = {i + 1, i - 1,
                         i + world.getWidth(),
                         i - world.getWidth()};
      
   }
   
   /**
    * Makes the heat cool off
    * 
    * @param i the index
    */
   public void cool(int i) {
      secondHeatMap[i] -= coolingRate;
      if(secondHeatMap[i] < 0)
         secondHeatMap[i] = 0;
   }
   
   /**
    * Gets the world this Simulation is operating on
    * 
    * @return the world
    */
   public World getWorld() {
      return world;
   }
   
   /**
    * Sets the world object this simulation operates on
    * 
    * @param world the world to set it to
    */
   public void setWorld(World world) {
      // transfer the old world's callback to the new one if it doesn't have one
      if(world.getUpdateCallback() == null) {
         Callback oldCallback = this.world.getUpdateCallback();
         world.setUpdateCallback(oldCallback);
      }
      this.world = world;         
      world.getUpdateCallback().fire();
   }
   /**
    * Gets the period of this Simulation in milliseconds
    * 
    * @return the period
    */
   public int getSimulationPeriod() {
      return simulationPeriod;
   }
   
   /**
    * Sets the period of this simulation
    * 
    * @param period the period to set it to
    */
   public void setSimulationPeriod(int period) {
      simulationPeriod = period;
      start();
   }
   
   /**
    * Gets the Timer the Simulation uses
    * 
    * @return the Timer
    */
   public Timer getSimulationTimer() {
      return simulationTimer;
   }
   
   /**
    * Sets the cooling rate of the world
    * @param coolingRate the new cooling rate
    */
   public void setCoolingRate(double coolingRate) {
      this.coolingRate = coolingRate;
   }
   
   /**
    * Gets the cooling rate of the world
    * @return the cooling rate 
    */
   public double getCoolingRate() {
      return coolingRate;
   }
   
}