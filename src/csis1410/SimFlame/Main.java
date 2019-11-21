package csis1410.SimFlame;

/**
 * The class containing the main method
 *
 */
public class Main {
   
   // Methods
   
   /**
    * The main method.
    * Instantiates the World, Simulation and Window.
    * 
    * @param args command line arguments
    */
   public static void main(String[] args) {
      World world = new World(100, 100);
      world.addFuelLine(new Point(0, 0), new Point(10, 10)); // remove me
      Simulation simulation = new Simulation(world);
      Window window = new Window(simulation);
      window.setVisible(true);
   }
   
}