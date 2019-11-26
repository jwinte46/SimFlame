package csis1410.SimFlame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author jacobwinters
 * @class CSIS 1410
 * @assignment FINAL PROJECT
 * @date 11/20/19
 * @description This is our W_FileToLoad class. It opens up when the load button is clicked on our GUI
 * It contains our panels and buttons for our W_FileToLoad GUI.
 * */
public class W_FileToLoad extends JFrame {

	private JPanel contentPane;
	private JLabel txtWhichFileDo;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public W_FileToLoad(Simulation simulation) {//Capitalized: Name of a type. Uncapitalized: Name of the variable
	   JFrame referenceToThisFrame = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 84);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		txtWhichFileDo = new JLabel();
		txtWhichFileDo.setHorizontalAlignment(SwingConstants.CENTER);
		txtWhichFileDo.setText("Which file do you want to load?");
		contentPane.add(txtWhichFileDo, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("Load");
		btnNewButton.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent arg0) {
		      World world = null;
		      world = Serializer.load(textField.getText());
		      if(world == null) {
		         JOptionPane.showMessageDialog(referenceToThisFrame, "Not a path to a compatible file!", "Error", JOptionPane.WARNING_MESSAGE);
		      } else {
   	         simulation.setWorld(world);
   	         referenceToThisFrame.dispose();
		      }
		      
		   }
		});
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
	}

}
