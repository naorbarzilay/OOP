package view;
import Utils.Specialization;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Exceptions.EmptyField;
import Exceptions.ShortPassExepction;
import Exceptions.UserTakenException;
import Exceptions.WrongField;
import Model.Doctor;
import Model.Hospital;
import Model.SubDepartment;

import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.event.ActionEvent;

public class AddDoctor extends JPanel {
	private JTextField textField;
	private Specialization [] specials=new Specialization[]{Specialization.UROLOGY,Specialization.PSYCHIATRY,
			Specialization.OPHTHALMOLOGY,Specialization.GYNECOLOGY,Specialization.NEUROLOGY,Specialization.FAMILY,Specialization.EMERGENCY};
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;
	private Doctor doc=null;
	JProgressBar progressBar;

	/**
	 * Create the panel.
	 */
	public AddDoctor() {

		setBackground(Color.ORANGE);
		setForeground(Color.LIGHT_GRAY);
		setLayout(null);

		textField = new JTextField();
		textField.setBounds(154, 80, 116, 22);
		add(textField);
		textField.setColumns(10);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstName.setBounds(53, 83, 89, 16);
		add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLastName.setBounds(53, 112, 89, 16);
		add(lblLastName);

		JLabel lblSubDepartmentId = new JLabel("Sub Department Id:");
		lblSubDepartmentId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSubDepartmentId.setBounds(53, 141, 158, 16);
		add(lblSubDepartmentId);

		textField_1 = new JTextField();
		textField_1.setBounds(154, 109, 116, 22);
		add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(205, 139, 65, 22);
		add(textField_2);
		textField_2.setColumns(10);

		JComboBox comboBox = new JComboBox(specials);
		comboBox.setBounds(319, 110, 98, 22);
		add(comboBox);

		JLabel lblSpcialtizon = new JLabel("Specialzation");
		lblSpcialtizon.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSpcialtizon.setBounds(319, 83, 116, 16);
		add(lblSpcialtizon);

		JLabel lblAddDoctor = new JLabel("Add Doctor :");
		lblAddDoctor.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblAddDoctor.setBounds(148, 32, 158, 16);
		add(lblAddDoctor);

		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUserName.setBounds(53, 186, 89, 16);
		add(lblUserName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(53, 217, 89, 16);
		add(lblPassword);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fname=textField.getText(); // get the first name
				String lname=textField_1.getText(); // get the last name
				int subid=Integer.parseInt(textField_2.getText()); // sub dep number
				Specialization special=(Specialization)comboBox.getSelectedItem();
				SubDepartment sd=Hospital.getInstance().getRealSubDepartment(subid); // check if the subdep exist
				String user=textField_3.getText();
				String password=passwordField.getText();
				try {
					if(fname.isEmpty()||lname.isEmpty()||subid<=0||user.isEmpty()||password.isEmpty())
						throw new EmptyField("Please complete all fields");
					if(password.length()<6) // if the password too short 
					{
						throw new ShortPassExepction("Password too short");
					}
					if(sd==null)
						throw new WrongField("Sub Department doesn't exist");
					if(ActionsWindow.userExist(user)) // check if the user already exist on the system 
					{
						throw new UserTakenException("User already exist");
					}
					Doctor d=new Doctor(fname,lname,special,sd); // new doctor
					if(Hospital.getInstance().addDoctor(d,sd))
					{
						doc=d;
						Hospital.getInstance().getDoctorUsers().put(user+" "+password,Hospital.getInstance().getRealDoctor(Doctor.getID()-1));
						JOptionPane.showMessageDialog(null, d.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);	
						textField.setText("");
						textField_1.setText("");
						textField_2.setText("");
						textField_3.setText("");
						passwordField.setText("");
					}
					else
					{
						throw new Exception("Doctor already exist");
					}
				}
				catch(ShortPassExepction e)
				{
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(WrongField e)
				{
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(UserTakenException e)
				{
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setBounds(292, 329, 116, 37);
		add(btnSave);


		textField_3 = new JTextField();
		textField_3.setBounds(154, 183, 116, 22);
		add(textField_3);
		textField_3.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(154, 214, 116, 22);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				checkPassWord(passwordField.getText());
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				checkPassWord(passwordField.getText());
			}
		});
		add(passwordField);

		JButton btnNewButton = new JButton("Show password");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,passwordField.getText(),"Password", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton.setBounds(420, 214, 136, 25);
		add(btnNewButton);		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnBack.setBounds(154, 335, 97, 25);
		add(btnBack);

		lblNewLabel = new JLabel("Picture");
		lblNewLabel.setBounds(12, 16, 75, 54);
		lblNewLabel.setIcon(ActionsWindow.getProfPic().getIcon());
		add(lblNewLabel);
		JButton btnBrowseImage = new JButton("Browse Image");
		btnBrowseImage.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) { //upload picture 
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				//filter the files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);
				//if the user click on save in Jfilechooser
				if(result == JFileChooser.APPROVE_OPTION){
					File selectedFile = file.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					lblNewLabel.setIcon(ActionsWindow.ResizeImage(path));
					if(doc!=null)
					{
						doc.setImgPath(path);
					}
				}
				//if the user click on save in Jfilechooser
				else if(result == JFileChooser.CANCEL_OPTION){
					JOptionPane.showMessageDialog(null,"No File Select","Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBrowseImage.setBounds(154, 249, 116, 25);
		add(btnBrowseImage);

		progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setBounds(292, 221, 116, 14);
		progressBar.setStringPainted(true);
		add(progressBar);

		JLabel lblAddImage = new JLabel("Add Image :");
		lblAddImage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddImage.setBounds(53, 253, 89, 21);
		add(lblAddImage);


	}
	
	/**
	 * check the password and change the progressBar according to the password
	 * @param password get the password 
	 */
	private void checkPassWord(String password)
	{
		if(password.isEmpty())
		{
			progressBar.setForeground(Color.GRAY);
			progressBar.setString("Empty");
			progressBar.setValue(0);
		}
		if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s]).{5,}")){
			progressBar.setForeground(Color.GREEN);
			progressBar.setString("Strong");
			progressBar.setValue(100);
		} else if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}")){
			progressBar.setForeground(Color.YELLOW);
			progressBar.setString("Medium");
			progressBar.setValue(70);
		} else if (password.matches("(?=.*[0-9]).{5,}")){
			progressBar.setForeground(Color.RED);
			progressBar.setString("Weak");
			progressBar.setValue(30);
		}
	}
}
