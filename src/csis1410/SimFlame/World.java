package csis1410.SimFlame;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Class representing the state of the world
 *
 * @author Tim Hansen
 */
public class World {
   
   // Fields
   // TODO change fuel into a hashset
   private int width;
   private int height;
   private double[] heatMap; // contains the heat values for the world
   private HashSet<Point> fuel; // a list of coordinates which contain fuel
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
         throw(new IndexOutOfBoundsException("World cannot have negative dimensions"));
      }
      this.width = width;
      this.height = height;
      heatMap = new double[width * height];
      Arrays.fill(heatMap, 0.0);
      fuel = new HashSet<Point>();
      coolingRate = 0.5;
   }
   
   // Methods
   
   public void addFuelAt(Point p) {
      int x = p.getX();
      int y = p.getY();
      if(x >= width || y >= height)
         return; // no purpose in adding an out-of-bounds point
      fuel.add(p);
   }
   
   /**
    * Adds a line of fuel to the world using a line draw algorithm
    * 
    * @param start the starting point of the line
    * @param end the ending point of the line
    */
   public void addFuelLine(Point start, Point end) {
      // if I want to draw a line with 30 width and 10 height...
      /*  ------------------------------
       * |xxx
       * |   xxx
       * |      xxx
       * |         xxx
       * |            xxx
       * |               xxx
       * |                  xxx
       * |                     xxx
       * |                        xxx
       * |                           xxx
       */
      // the first pixel starts at (0,0)
      // the next pixel moves down by 1/3 of a pixel
      // the next pixel moves down by 2/3 of a pixel
      // the next moves down by 3/3 of a pixel
      
      /* OLD BROKEN VERSION
      // the starting coordinates of the line
      int x = start.getX();
      int y = start.getY();
      
      // first the edge case of a line that is just a single pixel
      if(x == end.getX() && y == end.getY()) {
         fuel.add(start);
         return;
      }
      
      // if it's not just a single pixel, we need more info
      int horizontalDirection = (end.getX() > x ? 1 : -1); // which way the line is pointing horizontally. either 1 or -1
      int verticalDirection   = (end.getY() > y ? 1 : -1); // which way it's pointing vertically
      int xDifference = end.getX() - start.getX();
      int yDifference = end.getY() - start.getY();
      
      // First account for horizontal
      if(start.getY() == end.getY()) { // Horizontal line
         boolean loop = true;
         while(loop) {
            if(x == end.getX())
               loop = false;
            fuel.add(new Point(x, y));
            x += horizontalDirection;
         }
         return;
      }
      
      // Now account for vertical lines
      if(start.getX() == end.getX()) {
         boolean loop = true;
         while(loop) {
            if(y == end.getY())
               loop = false;
            fuel.add(new Point(x, y));
            y += verticalDirection;
         }
         return;
      }
      
      // Perfectly diagonal lines
      if(xDifference == yDifference) {
         boolean loop = true;
         while(loop) {
            if(x == end.getX()) // this means we want this to be the last iteration of the loop
               loop = false;
            
            fuel.add(new Point(x, y));
            x += horizontalDirection;
            y += verticalDirection;
         }
         return;
      }
      
      
      // lines where xDifference is greater than yDifference
      if(xDifference > yDifference) {
         int numerator = 0;
         boolean loop = true;
         while(loop) {
            if(x == end.getX())
               loop = false;
            
            fuel.add(new Point(x, y));
            x += horizontalDirection;
            numerator += yDifference;
            if(numerator >= xDifference) {
               y += verticalDirection;
               numerator -= xDifference;
            }
         }
         return;
      }
      
      // lines where yDifference is greater than xDifference
      if(yDifference > xDifference) {
         int numerator = 0;
         boolean loop = true;
         while(y != end.getY()) {
            if(y == end.getY())
               loop = false;
            
            fuel.add(new Point(x, y));
            y += verticalDirection;
            numerator += xDifference;
            if(numerator >= xDifference) {
               x += horizontalDirection;
               numerator -= yDifference;
            }
         }
         return;
      }
      */
      
      // new version
      
      int deltaX = end.getX() - start.getX();
      int deltaY = end.getY() - start.getY();
      //int x = start.getX();
      //int y = start.getY();
      int xDir = (deltaX > 0 ? 1 : -1);
      int yDir = (deltaY > 0 ? 1 : -1);
      
      
      // lines where the start and end point are identical
      if(start.equals(end)) {
         fuel.add(start);
         return;
      }
      
      // horizontal lines
      if(deltaX != 0 && deltaY == 0) {
         int x = start.getX();
         int y = start.getY();
         boolean loop = true;
         while(loop) {
            if(x == end.getX())
               loop = false;
            fuel.add(new Point(x, y));
            x += xDir;
         }
         return;
      }
      
      // vertical lines
      if(deltaX == 0 && deltaY != 0) {
         int x = start.getX();
         int y = start.getY();
         boolean loop = true;
         while(loop) {
            if(y == end.getY())
               loop = false;
            fuel.add(new Point(x, y));
            y += yDir;
         }
         return;
      }
      
      // perfectly diagonal lines
      if(Math.abs(deltaX) == Math.abs(deltaY)) {
         int x = start.getX();
         int y = start.getY();
         boolean loop = true;
         while(loop) {
            if(x == end.getX() && y == end.getY())
               loop = false;
            fuel.add(new Point(x, y));
            x += xDir;
            y += yDir;
         }
         return;
      }
      
      // line where deltaX is greater than deltaY
      if(Math.abs(deltaX) > Math.abs(deltaY)) {
         int x = start.getX();
         int y = start.getY();
         int numerator = 0;
         boolean loop = true;
         while(loop) {
            if(x == end.getX())
               loop = false;
            fuel.add(new Point(x, y));
            x+= xDir;
            numerator += Math.abs(deltaY);
            if(numerator >= Math.abs(deltaX)) {
               numerator -= Math.abs(deltaX);
               y += yDir;
            }
         }
         return;
      }
      
      // line where deltaY is less than deltaX
      if(Math.abs(deltaX) < Math.abs(deltaY)) {
         int x = start.getX();
         int y = start.getY();
         int numerator = 0;
         boolean loop = true;
         while(loop) {
            if(y == end.getY())
               loop = false;
            fuel.add(new Point(x, y));
            y += yDir;
            numerator += Math.abs(deltaX);
            if(numerator >= Math.abs(deltaY)) {
               numerator -= Math.abs(deltaY);
               x += xDir;
            }
         }
         return;
      }
      
      
   }
   
   /**
    * Sets the heat value at the given coordinates
    * 
    * @param x the x coordinate
    * @param y the y coordinate
    * @param heat the new heat value
    */
   public void setHeatAt(int x, int y, double heat) {
      heatMap[pointToIndex(new Point(x, y))] = heat;
   }
   
   /**
    * Gets the heat value at the given coordinates
    * @param x the x coordinate
    * @param y the y coordinate
    * @return the heat value
    */
   public double getHeatAt(int x, int y) {
      return heatMap[pointToIndex(new Point(x, y))];
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
      if(heatMap.length != newMap.length) {
         throw(new IllegalArgumentException("Argument to swapHeatMap must be an array of the same length as the World's heatMap"));
      }
      double[] oldMap = heatMap;
      heatMap = newMap;
      return oldMap;
   }
   
   /**
    * Sets the heat at the given coordinates to the
    * average of its heat with its neighbors' heat
    * 
    * @param x the x coordinate
    * @param y the y coordinate
    */
   public void diffuseHeat(int x, int y) {
      // holds the heat values for the given cell and its neighbors
      double[] values = new double[5];
      values[0] = getHeatAt(x, y);
      values[1] = getHeatAt(x + 1, y);
      values[2] = getHeatAt(x - 1, y);
      values[3] = getHeatAt(x, y + 1);
      values[4] = getHeatAt(x, y - 1);
      double sum = 0;
      // get the sum of all values
      for(double el : values) {
         sum += el;
      }
      double average = sum / 5;
      // now set the heat at (x, y) to the average
      setHeatAt(x, y, average);
   }
   
   /**
    * Converts two dimensional coordinates to an index usable in
    * the one dimensional heat map
    * 
    * @param p the point the convert
    * @return the index
    */
   public int pointToIndex(Point p) {
      return this.width * p.getY() + p.getX();
   }
   
   /**
    * Gets the width of the world
    * 
    * @return the width
    */
   public int getWidth() {
      return width;
   }
   
   /**
    * Gets the height of the world
    * 
    * @return the height
    */
   public int getHeight() {
      return height;
   }
   
   /**
    * Gets the heat map
    * 
    * @return the heat map
    */
   public double[] getHeatMap() {
      return heatMap;
   }
   
   /**
    * @return
    */
   public HashSet<Point> getFuelList() {
      return fuel;
   }
   
}