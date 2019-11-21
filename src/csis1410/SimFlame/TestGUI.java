package csis1410.SimFlame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class TestGUI extends JFrame {

   private JPanel contentPane;
   private Simulation simulation;

   

   /**
    * Create the frame.
    */
   public TestGUI(Simulation simulation) {
      this.simulation = simulation;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.setLayout(new BorderLayout(0, 0));
      setContentPane(contentPane);
      
      TestPanel testPanel = new TestPanel(simulation.getWorld(), 10);
      contentPane.add(testPanel, BorderLayout.WEST);
      addMouseListener((MouseListener)testPanel);
      addMouseMotionListener((MouseMotionListener)testPanel);
      
      JPanel panel = new JPanel();
      contentPane.add(panel, BorderLayout.EAST);
      
      JButton btnStart = new JButton("Start");
      btnStart.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            simulation.start();
         }
      });
      panel.setLayout(new GridLayout(0, 1, 0, 0));
      panel.add(btnStart);
      
      JCheckBox chckbxGrid = new JCheckBox("Grid");
      chckbxGrid.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            testPanel.setDrawGridLines(chckbxGrid.isSelected());
            testPanel.repaint();
         }
      });
      panel.add(chckbxGrid);
      pack();
   }
}
