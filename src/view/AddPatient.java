package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Exceptions.EmptyField;
import Exceptions.WrongField;
import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Model.Patient;
import Model.SubDepartment;
import Utils.Specialization;


public class AddPatient extends JPanel {

	private JTextField textField;
	private JTextField textField_1;
	private JTextField SubDepID;
	private JComboBox<Disease> comboBox;
	private JLabel lblNewLabel;
	private Patient pat=null;
	private JTextField textField_2;
	/**
	 * Create the panel.
	 */
	public AddPatient() {
		setBackground(Color.ORANGE);
		setForeground(Color.LIGHT_GRAY);
		setLayout(null);
		    
		textField = new JTextField();
		textField.setBounds(154, 80, 116, 22);
		add(textField);
		textField.setColumns(10);
		

	    textField_2 = new JTextField();
	    textField_2.setColumns(10);
	    textField_2.setBounds(205, 170, 65, 22);
	    add(textField_2);
	    
		JLabel lblNewLabel = new JLabel("Picture");
	    lblNewLabel.setBounds(12, 16, 75, 54);
	    lblNewLabel.setIcon(ActionsWindow.getProfPic().getIcon());
	    add(lblNewLabel);
	    
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
		
		SubDepID = new JTextField();
		SubDepID.setBounds(205, 139, 65, 22);
		if(MainWindow.getInstance().getKindofuser()!=2) // if doc or nurse , they can add only on they dep
		{
			Integer id=MainWindow.getInstance().getUser().getsDepartment().getId();
			SubDepID.setText(id.toString());
			SubDepID.disable();		
		}
		add(SubDepID);
		SubDepID.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setBounds(53, 227, 217, 22);
		add(comboBox);
		
		
		JLabel lblAddPatient = new JLabel("Add Patient :");
		lblAddPatient.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblAddPatient.setBounds(133, 32, 158, 16);
		add(lblAddPatient);
			
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fname=textField.getText(); // first name
				String lname=textField_1.getText(); // last name 
				int subid=Integer.parseInt(SubDepID.getText()); // sub dep number
				Disease disease=(Disease)comboBox.getSelectedItem();
				SubDepartment sd=Hospital.getInstance().getRealSubDepartment(subid);
				try {
					if(fname.isEmpty()||lname.isEmpty()||subid<=0)
						throw new EmptyField("Please complete all fields");
					if(sd==null)
						throw new WrongField("Sub Department doesn't exist");
					Patient p=new Patient(fname,lname,sd,disease); // create patient
					if(!checkStatusnum(textField_2.getText())) // check that user put number and not char
					{
						throw new WrongField("please enter a numbers between 1-100,");
					}
					else {
					p.setStatus((Integer.parseInt(textField_2.getText())));
					}
					if(Hospital.getInstance().addPatient(p,sd))
					{	
						JOptionPane.showMessageDialog(null, p.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);	
						textField.setText("");
						textField_1.setText("");
						textField_2.setText("");
						SubDepID.setText("");
					}
					else
					throw new Exception("Patient doesn't added");
				}
				catch(EmptyField e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(WrongField e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setBounds(319, 212, 97, 37);
		add(btnSave);
		
		JLabel lblDisease = new JLabel("Disease :");
		lblDisease.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDisease.setBounds(53, 199, 158, 16);
		add(lblDisease);
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnBack.setBounds(319, 262, 97, 25);
		add(btnBack);
		ActionsWindow.addDisease(comboBox);
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
		                  if(pat!=null)
		                  {
		                	  pat.setImgPath(path);
		                  }
		              }
		               //if the user click on save in Jfilechooser
		              else if(result == JFileChooser.CANCEL_OPTION){
		            	  JOptionPane.showMessageDialog(null,"No File Select","Error", JOptionPane.ERROR_MESSAGE);
		              }
		            }
		    });
		    btnBrowseImage.setBounds(311, 159, 116, 25);
		    add(btnBrowseImage);
		    
		    JLabel lblStatus = new JLabel("Status:(1-100)");
		    lblStatus.setFont(new Font("Tahoma", Font.BOLD, 15));
		    lblStatus.setBounds(53, 170, 158, 16);
		    add(lblStatus);
		    
	}
	private boolean checkStatusnum(String str)
	{
		for(int i=0;i<str.length()-1;i++)
		{
			if((str.charAt(i)<'0')||(str.charAt(i)>'9'))
			{
				return false;
			}
		}
		return true;
	}
}
