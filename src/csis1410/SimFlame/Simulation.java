package csis1410.SimFlame;

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
   // the simulation reads the data from the world's heatmap, and writes into
   // its own secondHeatMap array. Then it swaps heatMaps with the World.
   private double[] secondHeatMap;
   private Timer simulationTimer; // responsible for calling the step() method at a fixed interval
   private int simulationPeriod; // the number of milliseconds between steps
   
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
      // set the heat of fuel cells to 1.0
      world.getFuelList().add(new Point(1, 1));
      System.out.println(world.getFuelList());
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
   }
   
   /**
    * Gets the Timer the Simulation uses
    * 
    * @return the Timer
    */
   public Timer getSimulationTimer() {
      return simulationTimer;
   }
   
}