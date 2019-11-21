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
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;

public class W_FileToLoad extends JFrame {

	private JPanel contentPane;
	private JTextField txtWhichFileDo;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public W_FileToLoad(Simulation simulation) {//Capitalized: Name of a type. Uncapitalized: Name of the variable
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		txtWhichFileDo = new JTextField();
		txtWhichFileDo.setHorizontalAlignment(SwingConstants.CENTER);
		txtWhichFileDo.setText("Which file do you want to load?");
		contentPane.add(txtWhichFileDo, BorderLayout.NORTH);
		txtWhichFileDo.setColumns(10);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("Load");
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
	}

}
