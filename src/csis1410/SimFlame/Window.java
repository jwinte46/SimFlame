// TODO: Fix bug where clear button only partially clears the world
package csis1410.SimFlame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import java.awt.Component;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

/**
 * The main window of the program.
 * 
 * Contains buttons and sliders for controlling the simulation.
 * Contains a SimulatorPanel.
 */
public class Window extends JFrame {
   
   // Fields
	private Simulation simulation;
   private JFrame referenceToThisWindow; // for passing to subwindows
   private static int numWindows = 0; // the number of instances of this class in existence 
   private final ButtonGroup buttonGroup = new ButtonGroup();
   
   // Constructors
   
   /**
    * Constructor for Window
    * 
    * @param simulation reference to the simulation
    */
   public Window(Simulation simulation) {
      numWindows++;
      setTitle("SimFlame");
      referenceToThisWindow = this;
      // we have to override the default behavior when the window is closed
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent event) {
            numWindows--;
            if(numWindows == 0)
               System.exit(0);
            else dispose();
         }
      });
   	
   	JPanel controlPanel = new JPanel();
   	controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
   	getContentPane().add(controlPanel, BorderLayout.EAST);
   	
   	SimulationPanel simulationPanel = new SimulationPanel(simulation);
      getContentPane().add(simulationPanel, BorderLayout.WEST);
      addMouseListener(simulationPanel);
      addMouseMotionListener(simulationPanel);
      
      JButton btnClear = new JButton("Clear");
      btnClear.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            simulation.stop();
            // clear button
            simulation.getWorld().clear();
         }
      });
      
      JButton btnStartSimulation = new JButton("Start");
      btnStartSimulation.setBounds(6, 6, 70, 25);
      btnStartSimulation.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		simulation.start();
      		//btnClear.setEnabled(false);
      	}
      });
      controlPanel.add(btnStartSimulation);
      
      JButton btnStopSimulation = new JButton("Stop");
      btnStopSimulation.setBounds(82, 6, 67, 25);
      btnStopSimulation.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		simulation.stop();
      		//btnClear.setEnabled(true);
      	}
      });
      controlPanel.add(btnStopSimulation);
      
      JButton btnResetSimulation = new JButton("Reset");
      btnResetSimulation.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            simulation.stop();
            simulation.reset();
         }
      });
      btnResetSimulation.setBounds(155, 6, 75, 25);
      controlPanel.add(btnResetSimulation);
      controlPanel.add(btnClear);
   	controlPanel.setLayout(null);
   	controlPanel.setLayout(new GridLayout(0, 1, 0, 10));
   	
   	JCheckBox chckbxFlame = new JCheckBox("View Flame");
   	chckbxFlame.setSelected(true);
   	chckbxFlame.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      // flame check box
   	      simulationPanel.setFlameVisible(chckbxFlame.isSelected());
   	   }
   	});
   	controlPanel.add(chckbxFlame);
   	
   	JCheckBox chckbxFuel = new JCheckBox("View Fuel");
   	chckbxFuel.setSelected(true);
   	chckbxFuel.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      // fuel check box
   	      simulationPanel.setFuelVisible(chckbxFuel.isSelected());
   	   }
   	});
   	controlPanel.add(chckbxFuel);
   	
   	JCheckBox chckbxViewWind = new JCheckBox("View Wind");
   	chckbxViewWind.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	        simulationPanel.setWindVisible(chckbxViewWind.isSelected());
   	   }
   	});
   	controlPanel.add(chckbxViewWind);
   	
   	JRadioButton rdbtnOrangeFlame = new JRadioButton("Orange Flame");
   	rdbtnOrangeFlame.setSelected(true);
   	buttonGroup.add(rdbtnOrangeFlame);
   	controlPanel.add(rdbtnOrangeFlame);
   	
   	JRadioButton rdbtnBlueFlame = new JRadioButton("Blue Flame");
   	buttonGroup.add(rdbtnBlueFlame);
   	controlPanel.add(rdbtnBlueFlame);
   	
   	
   	JLabel lblLeftClickTo = new JLabel("Left click to add fuel. Right click to remove fuel");
   	lblLeftClickTo.setHorizontalAlignment(SwingConstants.CENTER);
   	getContentPane().add(lblLeftClickTo, BorderLayout.NORTH);
   	
   	JPanel controlPanel2 = new JPanel();
   	getContentPane().add(controlPanel2, BorderLayout.SOUTH);
   	controlPanel2.setLayout(new GridLayout(2, 2, 0, 0));
   	
   	JSlider sliderCoolingRate = new JSlider();
   	sliderCoolingRate.setMinorTickSpacing(0);
   	sliderCoolingRate.setName("");
   	sliderCoolingRate.setPaintLabels(true);
   	sliderCoolingRate.setValue(4);
   	sliderCoolingRate.setMaximum(100);
   	simulation.setCoolingRate(sliderCoolingRate.getValue() / 1000.0);
   	sliderCoolingRate.addChangeListener(new ChangeListener() {
   	   public void stateChanged(ChangeEvent arg0) {
   	      simulation.setCoolingRate(sliderCoolingRate.getValue() / 1000.0);
   	   }
   	});
   	
   	JLabel lblCoolingRate = new JLabel("Cooling rate");
   	controlPanel2.add(lblCoolingRate);
   	
   	JLabel lblDiffusionRate = new JLabel("Diffusion rate");
   	controlPanel2.add(lblDiffusionRate);
   	controlPanel2.add(sliderCoolingRate);
   	
   	JSlider sliderDiffusionRate = new JSlider();
   	sliderDiffusionRate.addChangeListener(new ChangeListener() {
   	   public void stateChanged(ChangeEvent arg0) {
   	      simulation.setDiffusionRate(sliderDiffusionRate.getValue() / 100.0);
   	   }
   	});
   	controlPanel2.add(sliderDiffusionRate);
   	
   	pack(); // makes the frame the appropriate size to accommodate the panel
   	
   	JMenuBar menuBar = new JMenuBar();
   	setJMenuBar(menuBar);
   	
   	JMenu mnFile = new JMenu("File");
   	menuBar.add(mnFile);
   	
   	JMenuItem mntmNew = new JMenuItem("New");
   	mntmNew.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      NewWorldWizard nww = new NewWorldWizard();
   	      nww.setVisible(true);
   	   }
   	});
   	mnFile.add(mntmNew);
   	
   	JMenuItem mntmLoad = new JMenuItem("Load");
   	mntmLoad.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      File currentDir = new File(".");
   	      JFileChooser fileChooser = new JFileChooser(currentDir.getAbsolutePath());
   	      int result = fileChooser.showOpenDialog(referenceToThisWindow);
   	      if(result == JFileChooser.APPROVE_OPTION) {
   	         World world = Serializer.load(fileChooser.getSelectedFile().getPath());
   	         if(world != null) {
   	            Simulation newSimulation = new Simulation(world);
   	            Window newWindow = new Window(newSimulation);
   	            newWindow.setVisible(true);
   	         } else {
   	            JOptionPane.showMessageDialog(null, "Error loading file");
   	         }
   	      }
   	      if(result == JFileChooser.ERROR_OPTION) {
   	         System.err.println("File could not be loaded");
   	      }
   	   }
   	});
   	mnFile.add(mntmLoad);
   	
   	JMenuItem mntmSave = new JMenuItem("Save");
   	mntmSave.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      File currentDir = new File(".");
   	      JFileChooser fileChooser = new JFileChooser(currentDir.getAbsolutePath());
   	      int result = fileChooser.showSaveDialog(referenceToThisWindow);
   	      if(result == JFileChooser.APPROVE_OPTION) {
   	         Serializer.save(simulation.getWorld(), fileChooser.getSelectedFile().getPath());
   	      }
   	      if(result == JFileChooser.ERROR_OPTION) {
   	         System.err.println("File could not be saved");
   	      }
   	   }
   	});
   	mnFile.add(mntmSave);
   }
   
   /**
    * Gets the number of instances of this class
    * @return the number of windows
    */
   public static int getNumWindows() {
      return numWindows;
   }
}