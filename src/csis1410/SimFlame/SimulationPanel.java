package csis1410.SimFlame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * The panel responsible for graphically drawing the simulation as well as
 * translating mouse events into coordinates usable by the simulation.
 *
 */
public class SimulationPanel extends JPanel implements MouseListener {
   
   // Private classes
   
   /**
    * The callback that tells the panel to redraw itself
    * 
    * Gets fired when the world has been updated
    */
   private class RedrawCallback implements Callback {
      
      /**
       * Tells the panel to redraw itself
       */
      public void fire() {
         // TODO: Write me
      }
      
   }
   
   // Fields
   
   // Constructors
   
   public SimulationPanel(World world) {
      // TODO: Write me
   }
   
   // Methods
   
   /**
    * Converts mouse coordinates to coordinates which can be used as indices in the world
    * 
    * @param e the MouseEvent whose coordinates to convert
    * @return a Point with the converted coordinates
    */
   public Point mouseCoordsToGridCoords(MouseEvent e) {
      // TODO: Write me
   }
   
   @Override
   public void paintComponent(Graphics g) {
      // TODO: Write me
   }
   
   @Override
   public void mouseClicked(MouseEvent e) {
      // TODO: Write me
   }
   
   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO: Write me
   }
   
   @Override
   public void mouseExited(MouseEvent e) {
      // TODO: Write me
   }
   
   @Override
   public void mousePressed(MouseEvent e) {
      // TODO: Write me
   }
   
   @Override
   public void mouseReleased(MouseEvent e) {
      // TODO: Write me
   }
}