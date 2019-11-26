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
import java.awt.Dimension;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * @author jacobwinters
 * @class CSIS 1410
 * @assignment FINAL PROJECT
 * @date 11/20/19
 * @description This is our Window class. It contains our panels and buttons for our GUI
 * The main window of the program.
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
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	
   	JPanel controlPanel = new JPanel();
   	controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
   	getContentPane().add(controlPanel, BorderLayout.EAST);
   	
   	JButton btnStartSimulation = new JButton("Start");
   	btnStartSimulation.setBounds(6, 6, 70, 25);
   	btnStartSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			simulation.start();
   		}
   	});
   	controlPanel.setLayout(null);
   	controlPanel.setLayout(new GridLayout(0, 1, 0, 10));
   	controlPanel.add(btnStartSimulation);
   	
   	JButton btnStopSimulation = new JButton("Stop");
   	btnStopSimulation.setBounds(82, 6, 67, 25);
   	btnStopSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			simulation.stop();
   		}
   	});
   	controlPanel.add(btnStopSimulation);
   	
   	JButton btnResetSimulation = new JButton("Reset");
   	btnResetSimulation.setBounds(155, 6, 75, 25);
   	controlPanel.add(btnResetSimulation);
   	
   	JButton btnLoadSimulation = new JButton("Load");
   	btnLoadSimulation.setBounds(236, 6, 69, 25);
   	btnLoadSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			W_FileToLoad loadWindow = new W_FileToLoad(simulation);
   			loadWindow.setVisible(true);
   		}
   	});
   	controlPanel.add(btnLoadSimulation);
   	
   	JButton btnSaveSimulation = new JButton("Save");
   	btnSaveSimulation.setBounds(311, 6, 68, 25);
   	btnSaveSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			W_FileToSave saveWindow = new W_FileToSave(simulation);
   			saveWindow.setVisible(true);
   		}
   	});
   	controlPanel.add(btnSaveSimulation);
   	
   	SimulationPanel simulationPanel = new SimulationPanel(simulation.getWorld());
   	getContentPane().add(simulationPanel, BorderLayout.WEST);
   	pack(); // makes the frame the appropriate size to accommodate the panel
   }
   
}