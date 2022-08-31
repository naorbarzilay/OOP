package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Model.Department;
import Model.Doctor;
import Model.Hospital;
import Model.Nurse;
import Model.Patient;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class DetailsWindow extends JPanel  {

	private JComboBox comboBox;
	private JTextArea doctors;
	private JTextArea nurses;
	private JTextArea patients;
	private JComboBox comboBoxDoctors;
	private JComboBox comboBoxNurses;
	private JComboBox comboBoxPatients;
	
	
	public DetailsWindow() {
		setBackground(Color.ORANGE);
		setLayout(null);
		
		JLabel lblShowDetails = new JLabel("Show Details :");
		lblShowDetails.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblShowDetails.setBounds(104, 26, 209, 16);
		add(lblShowDetails);
		
		JLabel lblNewLabel = new JLabel("Picture");
	    lblNewLabel.setBounds(12, 9, 75, 54);
	    lblNewLabel.setIcon(ActionsWindow.getProfPic().getIcon());
	    add(lblNewLabel);
	    
	    comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Department d=(Department)comboBox.getSelectedItem();
				if(d!=null)
				{
					doctors.setText(d.printAllDoctors());
					nurses.setText(d.printAllNurses());
					patients.setText(d.printAllPatients());
					ActionsWindow.addPatients(comboBoxPatients);
					ActionsWindow.addDoctors(comboBoxDoctors);
					ActionsWindow.addNurses(comboBoxNurses);
				}
			}
		});
	    comboBox.setToolTipText("Department List");
		comboBox.setBounds(150, 79, 288, 22);
		add(comboBox);
		
		nurses = new JTextArea(5,20);
		nurses.setFont(new Font("Tahoma", Font.BOLD, 15));
		nurses.setBounds(45, 149, 264, 99);

		JScrollPane scroll1 = new JScrollPane(nurses);
		scroll1.setLocation(23, 298);
		scroll1.setSize(415, 101);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll1);
		
		patients = new JTextArea(5,20);
		patients.setFont(new Font("Tahoma", Font.BOLD, 15));
		patients.setBounds(45, 149, 264, 99);

		JScrollPane scroll2 = new JScrollPane(patients);
		scroll2.setLocation(23, 445);
		scroll2.setSize(415, 101);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll2);
		
		doctors = new JTextArea(5,20);
		doctors.setFont(new Font("Tahoma", Font.BOLD, 15));
		doctors.setBounds(45, 149, 264, 99);

		JScrollPane scroll3 = new JScrollPane(doctors);
		scroll3.setLocation(23, 155);
		scroll3.setSize(415, 101);
		scroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll3);
		
		JLabel lblNewLabel_2 = new JLabel("Picture");
		lblNewLabel_2.setBounds(591, 192, 75, 54);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Picture");
		lblNewLabel_3.setBounds(591, 335, 75, 54);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Picture");
		lblNewLabel_4.setBounds(591, 482, 75, 54);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_1 = new JLabel("Select Department :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(12, 82, 131, 16);
		add(lblNewLabel_1);
		
		JLabel lblDoctors = new JLabel("Doctors :");
		lblDoctors.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDoctors.setBounds(23, 126, 96, 16);
		add(lblDoctors);
		
		comboBoxDoctors = new JComboBox();
		comboBoxDoctors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Doctor d=(Doctor)comboBoxDoctors.getSelectedItem();
				if(d!=null)
				{
					if(d.getImgPath()!=null)
					{
						lblNewLabel_2.setIcon(ActionsWindow.ResizeImage(d.getImgPath()));
					}
					else
					{
						lblNewLabel_2.setText("No Picture");
					}
				}
			}
		});
		comboBoxDoctors.setBounds(478, 157, 288, 22);
		add(comboBoxDoctors);
		
		comboBoxNurses = new JComboBox();
		comboBoxNurses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Nurse n=(Nurse)comboBoxNurses.getSelectedItem();
				if(n!=null)
				{
					if(n.getImgPath()!=null)
					{
						lblNewLabel_3.setIcon(ActionsWindow.ResizeImage(n.getImgPath()));
					}
					else
					{
						lblNewLabel_3.setText("No Picture");
					}
				}
			}
		});
		comboBoxNurses.setBounds(478, 300, 288, 22);
		add(comboBoxNurses);
		
		comboBoxPatients = new JComboBox();
		comboBoxPatients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Patient p=(Patient)comboBoxPatients.getSelectedItem();
				if(p!=null)
				{
					if(p.getImgPath()!=null)
					{
						lblNewLabel_4.setIcon(ActionsWindow.ResizeImage(p.getImgPath()));
					}
					else
					{
						lblNewLabel_4.setText("No Picture");
					}
				}
			}
		});
		comboBoxPatients.setBounds(478, 447, 288, 22);
		add(comboBoxPatients);	
		
		JLabel lblChooseFromThe = new JLabel("Choose from the list to show his picture : ");
		lblChooseFromThe.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChooseFromThe.setBounds(487, 126, 345, 16);
		add(lblChooseFromThe);
		
		JLabel lblNurses = new JLabel("Nurses :");
		lblNurses.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNurses.setBounds(23, 269, 96, 16);
		add(lblNurses);
		
		JLabel lblPatients = new JLabel("Patients :");
		lblPatients.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPatients.setBounds(23, 416, 96, 16);
		add(lblPatients);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnBack.setBounds(622, 601, 97, 25);
		add(btnBack);
		ActionsWindow.addDepartments(comboBox);
	}
}
