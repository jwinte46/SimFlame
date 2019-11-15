package csis1410.SimFlame;

/**
 * Represents coordinates in 2D space
 */
public class Point {
   
   // Fields
   
   private int x;
   private int y;
   
   // Methods
   
   /**
    * Gets the x coordinate
    * @return the x coordinate
    */
   public int getX() {
      return x;
   }
   
   /**
    * Gets the y coordinate
    * @return the y coordinate
    */
   public int getY() {
      return y;
   }
   
   /**
    * Sets the y coordinate to the specified number
    * @param x the number to set it to
    */
   public void setX(int x) {
      this.x = x;
   }
   
   /**
    * Sets the y coordinate to the specified number
    * @param y the number to set it to
    */
   public void setY(int y) {
      this.y = y;
   }
   
}