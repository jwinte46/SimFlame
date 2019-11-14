package csis1410.SimFlame;

/**
 * Class representing the state of the world
 *
 */
public class World {
   
   // Constructors
   
   /**
    * Constructor for the world
    * 
    * @param width the width of the grid
    * @param height the height of the grid
    */
   public World(int width, int height) {
      // TODO: Write me
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
    * Sets the callback to be fired when the world updates
    * @param callback the callback to fire on update 
    */
   public void setUpdateCallback(Callback callback) {
      // TODO: Write me
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
    * @param x the x coordinate
    * @param y the y coordinate
    */
   public void diffuseHeat(int x, int y) {
      // TODO: Write me
   }
   
}