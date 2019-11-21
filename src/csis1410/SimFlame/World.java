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
   private int width;
   private int height;
   private double[] heatMap; // contains the heat values for the world
   private HashSet<Point> fuel; /* a set of coordinates which contain fuel
                                   NOTE: This used to be an ArrayList.
                                   It's better to have it be a set, since
                                   we don't want it to have any duplicate elements */
   private double coolingRate; // the rate at which flame cools
   private Callback updateCallback = null; // the callback that gets fired when the world is updated
   
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
      updateCallback.fire();
   }
   
   public void removeFuelAt(Point p) {
      int x = p.getX();
      int y = p.getY();
      if(x >= width || y >= height)
         return;
      fuel.remove(p);
      updateCallback.fire();
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
      
      updateCallback.fire();
   }
   
   public void removeFuelLine(Point start, Point end) {
      
      int deltaX = end.getX() - start.getX();
      int deltaY = end.getY() - start.getY();
      //int x = start.getX();
      //int y = start.getY();
      int xDir = (deltaX > 0 ? 1 : -1);
      int yDir = (deltaY > 0 ? 1 : -1);
      
      
      // lines where the start and end point are identical
      if(start.equals(end)) {
         fuel.remove(start);
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
            fuel.remove(new Point(x, y));
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
            fuel.remove(new Point(x, y));
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
            fuel.remove(new Point(x, y));
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
            fuel.remove(new Point(x, y));
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
            fuel.remove(new Point(x, y));
            y += yDir;
            numerator += Math.abs(deltaX);
            if(numerator >= Math.abs(deltaY)) {
               numerator -= Math.abs(deltaY);
               x += xDir;
            }
         }
         return;
      }
      
      updateCallback.fire();
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
    * Sets the heat value at the given index to the array
    * @param i the index
    * @param heat the new heat value
    */
   public void setHeatAt(int i, double heat) {
      heatMap[i] = heat;
   }
   
   /**
    * Sets the heat to zero across the whole world
    */
   public void resetHeat() {
      Arrays.fill(heatMap, 0);
      updateCallback.fire();
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
    * Gets the heat at the given index to the array
    * @param i the index
    * @return the heat value
    */
   public double getHeatAt(int i) {
      return heatMap[i];
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
   
   public Callback getUpdateCallback() {
      return updateCallback;
   }
   
   /**
    * Removes all fuel and sets all heat to 0
    */
   public void clear() {
      fuel.clear();
      Arrays.fill(heatMap, 0.0);
      updateCallback.fire();
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
    * converts an index of the array into a two dimensional point
    * 
    * @param i the index
    * @return the point
    */
   public Point indexToPoint(int i) {
      /****
       *012
       *345
       *678
       */
      // y = i / width
      // x = i % width
      return new Point(i % width, i / width);
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
    * Gets the fuel set
    * @return the fuel set
    */
   public HashSet<Point> getFuelSet() {
      return fuel;
   }
   
}