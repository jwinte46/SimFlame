package csis1410.SimFlame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Set;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The panel responsible for graphically drawing the simulation as well as
 * translating mouse events into coordinates usable by the simulation.
 *
 * @authors Mortada Shogar and Tim Hansen
 */
public class SimulationPanel extends JPanel implements MouseListener, MouseMotionListener {

	// Fields

	private Simulation simulation;
	private Point lastGridPosition = null;
	private int cellSize = 5; // how big to draw each cell 
	private int worldWidth; 
	private int worldHeight;
	private Color backgroundColor = Color.BLACK;
	private Color fuelColor = Color.ORANGE;
	private boolean fuelVisible = true;
	private boolean flameVisible = true;
	private boolean windVisible = false;
	private int buttonDown = 0; // 0 = none, 1 = left mouse, 2 = middle mouse, 3 = right mouse
	private long lastDragRepaintTime = 0;
	private FlameColor flameColor = FlameColor.ORANGE;


	// Private Classes

	private class mouseDraggedTimerTask extends TimerTask {
		@Override
		public void run() {

		}
	}

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
			if (lastGridPosition == null)
				repaint();
		}

	}

	// Constructors

	/**
	 * Constructor for SimulationPanel
	 * @param simulation, the simulation
	 */
	public SimulationPanel(Simulation simulation) {
		this.simulation = simulation;
		this.worldWidth = simulation.getWorld().getWidth();
		this.worldHeight = simulation.getWorld().getHeight();
		this.cellSize = simulation.getWorld().getPixelSize();
		setPreferredSize(new Dimension(worldWidth * cellSize, worldHeight * cellSize));
		simulation.getWorld().setUpdateCallback(new RedrawCallback());
	}

	// Methods

	/**
	 * Converts mouse coordinates to coordinates which can be used as indices in the world
	 * 
	 * @param e the MouseEvent whose coordinates to convert
	 * @return a Point with the converted coordinates
	 */
	public Point mouseCoordsToGridCoords(MouseEvent e) {
		// convert from window coordinates to panel coordinates
		MouseEvent panelEvent = SwingUtilities.convertMouseEvent(getTopLevelAncestor(), e, this);

		// convert from panel coordinates to world coordinates
		int panelX = panelEvent.getX();
		int panelY = panelEvent.getY();
		int convertedX = panelX / cellSize;
		int convertedY = panelY / cellSize;
		return new Point(convertedX, convertedY);
	}

	@Override
	public void paintComponent(Graphics g) {
		int backgroundWidth = worldWidth * cellSize;
		int backgroundHeight = worldHeight * cellSize;

		// draw the background
		g.setColor(backgroundColor);
		g.fillRect(0, 0, backgroundWidth, backgroundHeight);

		// draw the flame
		if(flameVisible || windVisible) {
			int heatMapLength = simulation.getWorld().getWidth() * simulation.getWorld().getHeight();
			for(int i = 0; i < heatMapLength; i++) {
				// test
				Point p = simulation.getWorld().indexToPoint(i);
				int x = p.getX() * cellSize;
				int y = p.getY() * cellSize;
				if(flameVisible) {
					float redValue = 0;
					float greenValue = 0;
					float blueValue = 0;
					switch(flameColor) {
					case ORANGE:
						redValue = (float)(simulation.getWorld().getHeatAt(i));
						greenValue = (float)(simulation.getWorld().getHeatAt(i) / 5);
						blueValue = 0;
						break;
					}
					Color color = new Color(redValue, greenValue, blueValue);
					g.setColor(color);
					g.fillRect(x, y, cellSize, cellSize);
				}
				// Wind
				/* we're doing this inside of the flame's for loop to make it faster.
				 * it would be wasteful to do another for loop just for the wind.
				 */
				if(windVisible) {
					float redWindValue = simulation.getWorld().getWindXAt(i);
					float greenWindValue = simulation.getWorld().getWindYAt(i);
					g.setColor(new Color(Math.abs(redWindValue), Math.abs(greenWindValue), 0.0f, 0.5f)); // half transparency
					g.fillRect(x, y, cellSize, cellSize);
				}
			}
		}

		// draw fuel
		if(fuelVisible) {
			Set<Point> fuel = simulation.getWorld().getFuelSet();
			synchronized(fuel) {
				g.setColor(fuelColor);
				for(Point el : simulation.getWorld().getFuelSet()) {
					int x = el.getX() * cellSize;
					int y = el.getY() * cellSize;
					g.fillRect(x, y, cellSize, cellSize);
				}
			}
		}

	}

	public void setFuelVisible(boolean b) {
		fuelVisible = b;
		repaint();
	}

	public void setWindVisible(boolean b) {
		windVisible = b;
		repaint();
	}
	
	public void setFlameVisible(boolean b) {
		flameVisible = b;
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Nothing is written here, but we still need this listener to avoid compilation errors. 
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// Nothing is written here, but we still need this listener to avoid compilation errors. 
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Nothing is written here, but we still need this listener to avoid compilation errors. 
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// we need to initialize the lastMouseEvent variable
		lastGridPosition = mouseCoordsToGridCoords(e);
		int button = e.getButton();
		buttonDown = button;
		if(button == 1) // left button
			simulation.getWorld().addFuelAt(mouseCoordsToGridCoords(e)); // add fuel where the mouse is
		if(button == 3) // right button
			simulation.getWorld().removeFuelAt(mouseCoordsToGridCoords(e));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttonDown = 0;
		lastGridPosition = null;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		/* I can't do what I did in mousePressed, because for some reason the 
		 * getButton() method of MouseEvent returns 0 in the mouseDragged method.
		 * So instead, I need to keep track of it myself using the mousePressed and
		 * mouseReleased methods. buttonDown is an instance variable containing a number
		 * corresponding to which mouse button is currently down.
		 */
		long currentTime = System.currentTimeMillis();
		Point gridCoords = mouseCoordsToGridCoords(e);
		if(buttonDown == 1) // left button
			simulation.getWorld().addFuelLine(lastGridPosition,
					gridCoords);
		if(buttonDown == 3) // right button
			simulation.getWorld().removeFuelLine(lastGridPosition,
					gridCoords);

		lastGridPosition = gridCoords;
		if(currentTime - lastDragRepaintTime >= 50) {
			lastDragRepaintTime = currentTime;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Nothing is written here, but we still need this listener to avoid compilation errors. 
	}
}