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
      World world = new World(400, 400);
      Simulation simulation = new Simulation(world);
      Window window = new Window(simulation);
      window.setVisible(true);
   }
   
}