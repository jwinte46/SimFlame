package csis1410.SimFlame;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

/**
 * A simulation of the flame upon the panel
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
	private double coolingRate = 0.01;
	private double diffusionRate = 0.5;
	private Random rand;

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
		rand = new Random();
	}

	// Methods 

	/**
	 * Starts the simulation using the simulationTimer
	 */
	public void start() {
		simulationTimer = new Timer();
		simulationTimer.schedule(new SimulationTimerTask(), 0, simulationPeriod);
	}

	/**
	 * Stops the simulation using the simulationTimer
	 */
	public void stop() {
		if(simulationTimer != null)
			simulationTimer.cancel();
	}

	/**
	 * Resets the simulation to its initial state using simulationTimer
	 */
	public void reset() {
		if(simulationTimer != null)
			simulationTimer.cancel();
		world.resetHeat();
	}

	/**
	 * One "step" of the simulation
	 * 
	 * Gets called repeatedly by simulationTimer. Is responsible for progressing the simulation based on user interaction.
	 */
	public void step() {
		Set<Point> fuel = world.getFuelSet();
		synchronized(fuel) {
			for(int i = 0; i < world.getWidth(); i++) {
				for(int j = 0; j < world.getHeight(); j++) {
					Point p = new Point(i, j);
					double heatHere = 0;
					// seeding
					if(fuel.contains(p)) {
						// make fuel hot
						heatHere = 1.0 - (rand.nextDouble() - 0.5);
					} else {
						// convection + wind
						int index = world.pointToIndex(new Point(i, j));
						int windX = world.getWindXAt(index);
						int windY = world.getWindYAt(index);
						int convectFrom = world.pointToIndex(new Point(i + windX, j + 1 + windY));
						heatHere = world.getHeatAt(convectFrom);

						// set wind to new random values
						world.randomizeWindXAt(index);
						world.randomizeWindYAt(index);

						// diffuse
						double nearbyHeat = 0;
						for(int u = -1; u <= 1; u++) {
							for(int v = -1; v <= 1; v++) {
								nearbyHeat += world.getHeatAt(i + u, j + v);
							}
						}
						double averageHeat = nearbyHeat / 9.0;
						heatHere = heatHere * (1 - diffusionRate) + averageHeat * diffusionRate;
						// cool + random variation
						double randomCooling = (rand.nextDouble() - 0.5) * 0.125;
						heatHere -= coolingRate;
						if(world.getHeatAt(convectFrom) != 0.0)
							heatHere += randomCooling;
						if(heatHere < 0)
							heatHere = 0;
					}
					// clamp
					if(heatHere > 1.0)
						heatHere = 1.0;
					secondHeatMap[world.pointToIndex(p)] = heatHere;


				}
			}
			secondHeatMap = world.swapHeatMap(secondHeatMap);
		}
	}

	/**
	 * Makes fuel hot
	 * @param p the point to check
	 * @return 1.0 if there's fuel here
	 * @return the previous heat value of this point if there's not fuel
	 */
	public double seed(Point p) {
		if(world.getFuelSet().contains(p)) { // if there's fuel
			return 1.0; // make it hot
		} else {
			return world.getHeatAt(p.getX(), p.getY());
		}
	}

	/**
	 * Gets the Point below this one
	 * @param position the point to look below
	 * @return the point below the given one
	 */
	public Point convectFrom(Point position) {
		Point newPoint = new Point(position.getX(), position.getY() + 1);
		if(newPoint.getY() >= world.getHeight())
			newPoint.setY(world.getHeight() - 1);
		return newPoint;
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
	 * @param world, the world to set it to
	 */
	public void setWorld(World world) {
		// transfer the old world's callback to the new one if it doesn't have one
		if(world.getUpdateCallback() == null) {
			Callback oldCallback = this.world.getUpdateCallback();
			world.setUpdateCallback(oldCallback);
		}
		this.world = world;     
		secondHeatMap = new double[world.getWidth() * world.getHeight()];
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
	 * @param period, the period to set it to
	 */
	public void setSimulationPeriod(int period) {
		simulationPeriod = period;
		start();
	}

	/**
	 * Gets the Timer the Simulation uses
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

	/**
	 * Sets the diffusion rate of the world
	 * @param diffusionRate, the new diffusion rate
	 */
	public void setDiffusionRate(double diffusionRate) {
		this.diffusionRate = diffusionRate;      
	}

}