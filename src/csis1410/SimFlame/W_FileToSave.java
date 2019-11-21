package csis1410.SimFlame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class W_FileToSave extends JFrame {

	private JPanel contentPane;
	private JTextField txtWhichFileDo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					W_FileToSave frame = new W_FileToSave();
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
	public W_FileToSave() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		txtWhichFileDo = new JTextField();
		txtWhichFileDo.setHorizontalAlignment(SwingConstants.CENTER);
		txtWhichFileDo.setText("Which file do you want to save?");
		contentPane.add(txtWhichFileDo, BorderLayout.NORTH);
		txtWhichFileDo.setColumns(10);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("Save");
		panel.add(btnNewButton);
	}

}
