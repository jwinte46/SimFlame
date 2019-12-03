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
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * 
 * @description This is our Window class. It contains our panels and buttons for our GUI
 * The main window of the program.
 * Contains buttons and sliders for controlling the simulation.
 * Contains a SimulatorPanel.
 * 
 * @authors Jacob Winters and Tim Hansen
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
    * @param simulation, reference to the simulation
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
      	}
      });
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
   	
   	JMenuItem mntmQuit = new JMenuItem("Quit");
   	mntmQuit.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      System.exit(0);
   	   }
   	});
   	mnFile.add(mntmQuit);
   	
   	JMenu mnView = new JMenu("View");
   	menuBar.add(mnView);
   	
   	JCheckBoxMenuItem chckbxFlame = new JCheckBoxMenuItem("View Flame");
   	chckbxFlame.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            // flame check box
            simulationPanel.setFlameVisible(chckbxFlame.isSelected());
         }
      });
   	mnView.add(chckbxFlame);
   	chckbxFlame.setSelected(true);
   	
   	JCheckBoxMenuItem chckbxFuel = new JCheckBoxMenuItem("View Fuel");
   	chckbxFuel.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
   	      simulationPanel.setFuelVisible(chckbxFuel.isSelected());
   	   }
   	});
   	mnView.add(chckbxFuel);
   	chckbxFuel.setSelected(true);
   	
   	JCheckBoxMenuItem chckbxViewWind = new JCheckBoxMenuItem("View Wind");
   	chckbxViewWind.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent arg0) {
            simulationPanel.setWindVisible(chckbxViewWind.isSelected());
   	   }
   	});
   	mnView.add(chckbxViewWind);
   	
   	JRadioButtonMenuItem rdbtnOrangeFlame = new JRadioButtonMenuItem("Orange Flame");
   	mnView.add(rdbtnOrangeFlame);
   	rdbtnOrangeFlame.setSelected(true);
   	buttonGroup.add(rdbtnOrangeFlame);
   	
   	JRadioButtonMenuItem rdbtnBlueFlame = new JRadioButtonMenuItem("Blue Flame");
   	mnView.add(rdbtnBlueFlame);
   	buttonGroup.add(rdbtnBlueFlame);
   }
   
   /**
    * Gets the number of instances of this class
    * @return the number of windows
    */
   public static int getNumWindows() {
      return numWindows;
   }
}