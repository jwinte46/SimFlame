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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import java.awt.Component;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

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
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	
   	JPanel controlPanel = new JPanel();
   	controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
   	getContentPane().add(controlPanel, BorderLayout.EAST);
   	
   	SimulationPanel simulationPanel = new SimulationPanel(simulation,1);
      getContentPane().add(simulationPanel, BorderLayout.WEST);
      addMouseListener(simulationPanel);
      addMouseMotionListener(simulationPanel);
      
      JButton btnClear = new JButton("Clear");
      btnClear.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // clear button
            simulation.getWorld().clear();
         }
      });
      controlPanel.add(btnClear);
   	
   	JButton btnStartSimulation = new JButton("Start");
   	btnStartSimulation.setBounds(6, 6, 70, 25);
   	btnStartSimulation.addActionListener(new ActionListener() {
   		public void actionPerformed(ActionEvent e) {
   			simulation.start();
   			btnClear.setEnabled(false);
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
   			btnClear.setEnabled(true);
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
   	
   	JCheckBox chckbxGrid = new JCheckBox("Grid");
   	chckbxGrid.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      // grid check box
   	      simulationPanel.setGridVisible(chckbxGrid.isSelected());
   	   }
   	});
   	controlPanel.add(chckbxGrid);
   	
   	JCheckBox chckbxFlame = new JCheckBox("Flame");
   	chckbxFlame.setSelected(true);
   	chckbxFlame.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      // flame check box
   	      simulationPanel.setFlameVisible(chckbxFlame.isSelected());
   	   }
   	});
   	controlPanel.add(chckbxFlame);
   	
   	JCheckBox chckbxFuel = new JCheckBox("Fuel");
   	chckbxFuel.setSelected(true);
   	chckbxFuel.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      // fuel check box
   	      simulationPanel.setFuelVisible(chckbxFuel.isSelected());
   	   }
   	});
   	controlPanel.add(chckbxFuel);
   	
   	JLabel lblLeftClickTo = new JLabel("Left click to add fuel. Right click to remove fuel");
   	lblLeftClickTo.setHorizontalAlignment(SwingConstants.CENTER);
   	getContentPane().add(lblLeftClickTo, BorderLayout.NORTH);
   	
   	JPanel controlPanel2 = new JPanel();
   	getContentPane().add(controlPanel2, BorderLayout.SOUTH);
   	controlPanel2.setLayout(new GridLayout(2, 2, 0, 0));
   	
   	JSlider sliderCoolingRate = new JSlider();
   	sliderCoolingRate.setName("");
   	sliderCoolingRate.setPaintLabels(true);
   	sliderCoolingRate.setValue(4);
   	sliderCoolingRate.setMaximum(50);
   	sliderCoolingRate.addChangeListener(new ChangeListener() {
   	   public void stateChanged(ChangeEvent arg0) {
   	      simulation.setCoolingRate(sliderCoolingRate.getValue() / 100.0);
   	   }
   	});
   	
   	JLabel lblCoolingRate = new JLabel("Cooling rate");
   	controlPanel2.add(lblCoolingRate);
   	
   	JLabel lblDiffusionRate = new JLabel("Diffusion rate");
   	controlPanel2.add(lblDiffusionRate);
   	controlPanel2.add(sliderCoolingRate);
   	
   	JSlider slider = new JSlider();
   	controlPanel2.add(slider);
   	
   	pack(); // makes the frame the appropriate size to accommodate the panel
   }
   
}