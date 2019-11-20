package csis1410.SimFlame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TestGUI extends JFrame {

   private JPanel contentPane;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               TestGUI frame = new TestGUI();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public TestGUI() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.setLayout(new BorderLayout(0, 0));
      setContentPane(contentPane);
      
      JPanel testPanel = new TestPanel(100, 100, 10);
      contentPane.add(testPanel, BorderLayout.CENTER);
      addMouseListener((MouseListener)testPanel);
      addMouseMotionListener((MouseMotionListener)testPanel);
      pack();
   }
}
