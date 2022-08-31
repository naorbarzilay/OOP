package view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Exceptions.EmptyField;
import Exceptions.WrongField;
import Model.Hospital;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginWindow extends JPanel {
	private JTextField textField;
	private JPasswordField passwordField;
	
	public LoginWindow() {
		setBackground(Color.ORANGE);
		setLayout(null);
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if((textField.getText().isEmpty())||(passwordField.getText().isEmpty())) // check if the field not empty
					{
						throw new EmptyField("Please enter user and password field");
					}
					String code=textField.getText()+ " " +passwordField.getText(); // Connects the username and password to one code
					if(textField.getText().equals("admin")&&(passwordField.getText().equals("admin"))) //check first if admin
					{
						MainWindow.getInstance().setUser(null); 
						MainWindow.setKindofuser(2); // review the menu of admin (everything) 
						MainWindow.changeWindow(new ActionsWindow(2));
					}
					else if((Hospital.getInstance().getDoctorUsers().containsKey(code))) // if its doctor user 
					{
						MainWindow.getInstance().setUser(Hospital.getInstance().getDoctorUsers().get(code));
						MainWindow.setKindofuser(0); // doctor menu
						MainWindow.changeWindow(new ActionsWindow(0));
					}
					else if((Hospital.getInstance().getNurseUsers().containsKey(code))) // if its nurse
					{
						MainWindow.getInstance().setUser(Hospital.getInstance().getNurseUsers().get(code));
						MainWindow.setKindofuser(1); // nurse menu
						MainWindow.changeWindow(new ActionsWindow(1));
					}
					else
						throw new  WrongField("You entered wrong user or password");
				}
				catch(EmptyField ex1)
				{
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(WrongField ex2)
				{
					JOptionPane.showMessageDialog(null, ex2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(177, 163, 97, 25);
		add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(158, 97, 116, 22);
		add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(158, 132, 116, 22);
		add(passwordField);

		JLabel lblUserName = new JLabel("User Name :");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUserName.setBackground(Color.BLUE);
		lblUserName.setBounds(55, 100, 91, 16);
		add(lblUserName);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(65, 134, 102, 16);
		add(lblPassword);

		JLabel lblLoginWindow = new JLabel("Login Window");
		lblLoginWindow.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLoginWindow.setBounds(144, 37, 248, 32);
		add(lblLoginWindow);
	}
}
