package csis1410.SimFlame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;

/**
 * The main window of the program.
 * 
 * Contains buttons and sliders for controlling the simulation.
 * Contains a SimulatorPanel.
 */
public class Window extends JFrame {
   
   // Fields
	private Simulation simulation;
   
   // Constructors
   
   /**
    * Constructor for Window
    * 
    * @param simulation reference to the simulation
    */
   public Window(Simulation simulation) {
   	
   	JPanel panel = new JPanel();
   	getContentPane().add(panel, BorderLayout.EAST);
   	panel.setLayout(new MigLayout("", "[117px]", "[29px][29px][29px][][]"));
   	
   	JButton btnStartSimulation = new JButton("Start");
   	panel.add(btnStartSimulation, "cell 0 0,alignx left,aligny top");
   	
   	JButton btnStopSimulation = new JButton("Stop");
   	panel.add(btnStopSimulation, "cell 0 1,alignx left,aligny top");
   	
   	JButton btnResetSimulation = new JButton("Reset");
   	panel.add(btnResetSimulation, "cell 0 2,alignx left,aligny top");
   	
   	JButton btnLoadSimulation = new JButton("Load");
   	btnLoadSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			
   		}
   	});
   	panel.add(btnLoadSimulation, "cell 0 3");
   	
   	JButton btnSaveSimulation = new JButton("Save");
   	btnSaveSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   		}
   	});
   	panel.add(btnSaveSimulation, "cell 0 4");
      // TODO: Write me
   }
   // This is so cool
   // 
   // Method
   //method
   
   /**
    * Shows the Window
    */
   public void show() {
      // TODO: Write me
   }
   
   /**
    * Starts the simulation
    */
   private void startSimulation() {
      // TODO: Write me
   }
   
   /**
    * Stops the simulation
    */
   private void stopSimulation() {
      // TODO: Write me
   }
   
   /**
    * Resets the simulation 
    */
   private void resetSimulation() {
      // TODO: Write me
   }
   
}