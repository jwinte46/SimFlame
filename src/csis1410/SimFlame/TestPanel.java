package csis1410.SimFlame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TestPanel extends JPanel implements MouseListener, MouseMotionListener{

   int width;
   int height;
   int cellSize;
   boolean mouseDown = false;;
   private World world;
   private MouseEvent lastMouseEvent = null; /* to make smooth, unbroken lines, we need to
                                       * keep a reference to the previous mouse event.
                                       * That way, we can draw a line between the previous 
                                       * and current mouse coordinates */
   private boolean drawGridLines; // whether or not to draw grid lines
   
   /**
    * Create the panel.
    * 
    * @param width the number of cells horizontally
    * @param height the number of cells vertically
    */
   public TestPanel(World world, int cellSize) {
      this.width = world.getWidth();
      this.height = world.getHeight();
      this.cellSize = cellSize;
      setPreferredSize(new Dimension(width * cellSize, height * cellSize));
      
   }

   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      // background
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, width * cellSize, height * cellSize);
      
      // draw fuel cells
      g.setColor(Color.ORANGE);
      for(Point el : world.getFuelList()) {
         int x = el.getX() * cellSize;
         int y = el.getY() * cellSize;
         
         g.fillRect(x, y, cellSize, cellSize);
      }
      
      // heat
      for(int i = 0; i < world.getWidth() * world.getHeight(); i++) {
         world.setHeatAt(i, 1.0);
         g.setColor(new Color((float)(world.getHeatAt(i)), 0.0f, 0.0f));
         Point drawPoint = world.indexToPoint(i);
         if(i % 2 == 0)
            g.fillRect(drawPoint.getX() * cellSize, drawPoint.getY() * cellSize, cellSize, cellSize);
         
         /*Color heatColor = new Color((float)heat, 0, 0); // try this. it's not gonna look good.
         g.setColor(heatColor);
         Point drawPoint = world.indexToPoint(i);
         int drawX = drawPoint.getX() * cellSize;
         int drawY = drawPoint.getY() * cellSize;
         g.fillRect(drawX, drawY, cellSize, cellSize);*/
      }
      
      // grid lines
      if(drawGridLines) {
         g.setColor(Color.white);
         for(int i = 0; i < width; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, height * cellSize);
            for(int j = 0; j < height; j++) {
               g.drawLine(0, j * cellSize, width * cellSize, j * cellSize);
            }
         }
      }
   }

   public void setDrawGridLines(boolean b) {
      drawGridLines = b;
   }
   
   private Point panelCoordsToWorldCoords(Point p) {
      return null;
   }

   @Override
   public void mouseClicked(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseEntered(MouseEvent arg0) {
      
      
   }

   @Override
   public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mousePressed(MouseEvent arg0) {
      
      mouseDown = true;
      
      if(lastMouseEvent == null) {
         lastMouseEvent = arg0;
      }
      
      MouseEvent converted = SwingUtilities.convertMouseEvent(getTopLevelAncestor(), arg0, this);
      int x = converted.getX();
      int y = converted.getY();
      int convertedX = x / cellSize;
      int convertedY = y / cellSize;
      
      lastMouseEvent = converted;
      
      world.addFuelAt(new Point(convertedX, convertedY));
      repaint();
      
   }

   @Override
   public void mouseReleased(MouseEvent arg0) {
      mouseDown = false;
      repaint();
   }

   @Override
   public void mouseDragged(MouseEvent arg0) {
      MouseEvent converted = SwingUtilities.convertMouseEvent(getTopLevelAncestor(), arg0, this);
      int x = converted.getX();
      int y = converted.getY();
      int convertedX = x / cellSize;
      int convertedY = y / cellSize;
      int lastX = lastMouseEvent.getX();
      int lastY = lastMouseEvent.getY();
      int lastConvertedX = lastX / cellSize;
      int lastConvertedY = lastY / cellSize;
      
      world.addFuelLine(new Point(lastConvertedX, lastConvertedY),new Point(convertedX, convertedY));
      lastMouseEvent = converted; // 
      repaint();
      
   }

   @Override
   public void mouseMoved(MouseEvent arg0) {
      
   }
}
