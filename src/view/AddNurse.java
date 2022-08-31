package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Exceptions.EmptyField;
import Exceptions.ShortPassExepction;
import Exceptions.UserTakenException;
import Exceptions.WrongField;
import Model.Doctor;
import Model.Hospital;
import Model.Nurse;
import Model.SubDepartment;
import Utils.Specialization;
import Utils.Treatments;

public class AddNurse extends JPanel {

	private JTextField textField;
	private Treatments [] treats=new Treatments[]{Treatments.ANTIVIRAL,Treatments.BLOOD_PLASMA_TRANSFUSIONS,Treatments.BREATHING_SUPPORT,
			Treatments.STEROIDS};
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;
	private Nurse nur=null;
	JProgressBar progressBar;

	public AddNurse() {
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
		if(MainWindow.getInstance().getKindofuser()==1) // check the kind of user , if nurse so he can add only on his dep
		{
			Integer id=MainWindow.getInstance().getUser().getsDepartment().getId();
			textField_2.setText(id.toString());
			textField_2.disable();
		}
		add(textField_2);
		textField_2.setColumns(10);

		JComboBox comboBox = new JComboBox(treats);
		comboBox.setBounds(310, 109, 98, 22);
		add(comboBox);

		JLabel lblTreatment = new JLabel("Treatment");
		lblTreatment.setBounds(319, 83, 89, 16);
		add(lblTreatment);

		JLabel lblAddNurse = new JLabel("Add Nurse :");
		lblAddNurse.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblAddNurse.setBounds(131, 32, 158, 16);
		add(lblAddNurse);

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
				String fname=textField.getText(); // first name
				String lname=textField_1.getText(); // lase name
				int subid=Integer.parseInt(textField_2.getText());
				if(MainWindow.getInstance().getKindofuser()==0)
				{
					if(!checkSameDep(subid)) // check if the new nurse on the same subdep
					{
						JOptionPane.showMessageDialog(null, "Nurse doesn't in the same department like you", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				SubDepartment sd=Hospital.getInstance().getRealSubDepartment(subid);
				String user=textField_3.getText();
				String password=passwordField.getText();
				Treatments treat=(Treatments)comboBox.getSelectedItem();
				try {
					if(fname.isEmpty()||lname.isEmpty()||subid<=0||user.isEmpty()||password.isEmpty())
						throw new EmptyField("Please complete all fields");
					if(sd==null)
						throw new WrongField("Sub Department doesn't exist");
					if(password.length()<6)
						throw new ShortPassExepction("PassWord too short");
					if(ActionsWindow.userExist(user))
					{
						throw new UserTakenException("User already exist");
					}
					Nurse n=new Nurse(fname,lname,treat,sd);
					if(Hospital.getInstance().addNurse(n,sd))
					{
						Hospital.getInstance().getNurseUsers().put(user+" "+password,Hospital.getInstance().getRealNurse(Nurse.getID()-1));
						JOptionPane.showMessageDialog(null, n.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);	
						textField.setText("");
						textField_1.setText("");
						textField_2.setText("");
						textField_3.setText("");
						passwordField.setText("");
					}
					else
						throw new Exception("Nurse doesn't added");
				}
				catch(EmptyField e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(WrongField e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(ShortPassExepction e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(UserTakenException e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setBounds(350, 326, 116, 37);
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
		btnNewButton.setBounds(410, 214, 135, 25);
		add(btnNewButton);		

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));

			}
		});
		btnBack.setBounds(192, 332, 97, 25);
		add(btnBack);
		lblNewLabel = new JLabel("Picture");
		lblNewLabel.setBounds(25, 16, 75, 54);
		lblNewLabel.setIcon(ActionsWindow.getProfPic().getIcon());
		add(lblNewLabel);
		JButton btnBrowseImage = new JButton("Browse Image"); // upload picture
		btnBrowseImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
					if(nur!=null)
					{
						nur.setImgPath(path);
					}
				}
				//if the user click on save in Jfilechooser
				else if(result == JFileChooser.CANCEL_OPTION){
					JOptionPane.showMessageDialog(null,"No File Select","Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBrowseImage.setBounds(173, 258, 116, 25);
		add(btnBrowseImage);

		progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setBounds(282, 221, 116, 14);
		progressBar.setStringPainted(true);
		add(progressBar);

		JLabel lblUploadImage = new JLabel("Upload Image :");
		lblUploadImage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUploadImage.setBounds(53, 254, 116, 30);
		add(lblUploadImage);


	}
	/**
	 * the function check if the sub department exist on the nurse dep
	 * @param subdepid get the id of the sub dep
	 * @return true if the supdep exist on the dep 
	 */
	private boolean checkSameDep(int subdepid)
	{
		for(SubDepartment sd : MainWindow.getInstance().getUser().getsDepartment().getDepartment().getSdepts())
		{
			if(sd.getId()==subdepid)
			{
				return true;
			}
		}
		return false;
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