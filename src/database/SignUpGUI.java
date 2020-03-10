package database;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import sun.net.www.content.image.jpeg;

/**
 * 
 */

/**
 * @author vini
 *
 */
public class SignUpGUI {
	public static JPanel cteateJPanel(String name) {
		JPanel panel = new JPanel();
		JPanel title = new JPanel();
		JTextField fieldFillIN = new JTextField(20);
		JLabel label = new JLabel(name);
		title.add(label);
		panel.add(title);
		panel.add(fieldFillIN);
		return panel;
	}

	
	
	public static void main(String[] args) {
		JPanel bigJPanel = new JPanel();
		JPanel smallPanel = new JPanel();
		smallPanel = cteateJPanel("UserName");
		bigJPanel.add(smallPanel);
		smallPanel = cteateJPanel("password");
		bigJPanel.add(smallPanel);
		smallPanel = cteateJPanel("Confirm password");
		bigJPanel.add(smallPanel);

		JLabel signUpJLabel = new JLabel("Sign Up");
		JButton signUpButton = new JButton();
		signUpButton.add(signUpJLabel);
		bigJPanel.add(signUpButton);

		JFrame frame = new JFrame();
		frame.setBounds(300, 200, 400, 280);
		frame.setBackground(Color.white);
		frame.add(bigJPanel);

		frame.setVisible(true);
	}
}
