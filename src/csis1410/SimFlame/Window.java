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

/**
 * The main window of the program.
 * 
 * Contains buttons and sliders for controlling the simulation.
 * Contains a SimulatorPanel.
 */
public class Window extends JFrame {
   
   // Fields
	private Simulation simulation;
   private SimulationPanel simulationPanel;
   
   // Constructors
   
   /**
    * Constructor for Window
    * 
    * @param simulation reference to the simulation
    */
   public Window(Simulation simulation) {
      setSize(new Dimension(420, 278));
      setPreferredSize(new Dimension(200, 200));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	
   	JPanel controlPanel = new JPanel();
   	getContentPane().add(controlPanel, BorderLayout.EAST);
   	controlPanel.setLayout(new MigLayout("", "[117px]", "[29px][29px][29px][][]"));
   	
   	JButton btnStartSimulation = new JButton("Start");
   	btnStartSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			simulation.start();
   		}
   	});
   	controlPanel.setLayout(new MigLayout("", "[70px][67px][75px][69px][68px]", "[25px]"));
   	controlPanel.add(btnStartSimulation, "cell 0 0,alignx left,aligny top");
   	
   	JButton btnStopSimulation = new JButton("Stop");
   	btnStopSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			simulation.stop();
   		}
   	});
   	controlPanel.add(btnStopSimulation, "cell 1 0,alignx left,aligny top");
   	
   	JButton btnResetSimulation = new JButton("Reset");
   	controlPanel.add(btnResetSimulation, "cell 2 0,alignx left,aligny top");
   	
   	JButton btnLoadSimulation = new JButton("Load");
   	btnLoadSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			W_FileToLoad loadWindow = new W_FileToLoad(simulation);
   			loadWindow.setVisible(true);
   		}
   	});
   	controlPanel.add(btnLoadSimulation, "cell 3 0,alignx left,aligny top");
   	
   	JButton btnSaveSimulation = new JButton("Save");
   	btnSaveSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			W_FileToSave saveWindow = new W_FileToSave(simulation);
   			saveWindow.setVisible(true);
   		}
   	});
   	controlPanel.add(btnSaveSimulation, "cell 4 0,alignx left,aligny top");
   }
   
}