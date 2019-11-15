package csis1410.SimFlame;

import java.util.ArrayList;

/**
 * Class representing the state of the world
 *
 * @author Tim Hansen
 */
public class World {
   
   // Fields
   
   private int width;
   private int height;
   private double[] heatMap; // contains the heat values for the world
   private ArrayList<Point> fuel; // a list of coordinates which contain fuel
   private double coolingRate; // the rate at which flame cools
   private Callback updateCallback; // the callback that gets fired when the world is updated
   
   // Constructors
   
   /**
    * Constructor for the world
    * 
    * if width or height are less than 1, constructs a 1x1 world
    * 
    * @param width the width of the grid
    * @param height the height of the grid
    */
   public World(int width, int height) {
      // width and height can't be less than 1
      if(width < 1 || height < 1) {
         width = 1;
         height = 1;
      }
      this.width = width;
      this.height = height;
      heatMap = new double[width * height];
      
   }
   
   // Methods
   
   /**
    * Adds a line of fuel to the world
    * 
    * @param start the starting point of the line
    * @param end the ending point of the line
    */
   public void addFuelLine(Point start, Point end) {
      // TODO: Write me
   }
   
   /**
    * Sets the heat value at the given coordinates
    * 
    * @param x the x coordinate
    * @param y the y coordinate
    * @param heat the new heat value
    */
   public void setHeatAt(int x, int y, double heat) {
      // TODO: Write me
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
    * Sets the callback to be fired when the world updates
    * @param callback the callback to fire on update 
    */
   public void setUpdateCallback(Callback callback) {
      updateCallback = callback;
   }
   
   /**
    * Replaces the world's heat map with a new one. Returns the current one.
    * Useful since we'll be double buffering the heat map.
    * 
    * @param newMap the new heat map for the world to use
    * @return the current heat map used by the world
    */
   public double[] swapHeatMap(double[] newMap) {
      // TODO: Write me
   }
   
   /**
    * Averages the heat at the given coordinates with its neighbors
    * 
    * @param x the x coordinate
    * @param y the y coordinate
    */
   public void diffuseHeat(int x, int y) {
      // TODO: Write me
   }
   
   /**
    * Converts two dimensional coordinates to an index usable in
    * the one dimensional heat map
    * 
    * @param p the point the convert
    * @return the index
    */
   public long pointToIndex(Point p) {
      return this.width * p.getY() + p.getX();
   }
   
}