package view;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Model.Patient;
import Model.PatientReport;
import Model.SubDepartment;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import Exceptions.EmptyField;
import Exceptions.PatientWithoutDocExpetion;

public class AddReport extends JPanel {
	private JComboBox<Patient> comboBox;
	private JComboBox<Doctor> comboBox_1 ;
	private Patient pat=null;
	private Doctor doc=null;
	private PatientReport pr=null;

	public AddReport() {
		setBackground(Color.ORANGE);
		setForeground(Color.LIGHT_GRAY);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Picture");
		lblNewLabel.setBounds(12, 16, 75, 54);
		lblNewLabel.setIcon(ActionsWindow.getProfPic().getIcon());
		add(lblNewLabel);

		JLabel lblFirstName = new JLabel("Choose Patient :");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstName.setBounds(37, 82, 132, 16);
		add(lblFirstName);

		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.BOLD, 13));
		textPane.setBounds(12, 241, 348, 101);
		add(textPane);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pat=(Patient)comboBox.getSelectedItem();
					if(!Hospital.getInstance().getDoctorsByPatient().containsKey(pat)) // check if the patients exist 
					{
						throw new PatientWithoutDocExpetion("This Patients doesn't exist on the treatments");
					}
					if(!Hospital.getInstance().getDoctorsByPatient().get(pat).isEmpty()) // check if the doctor exist on patient doctors
						ActionsWindow.addDoctors(pat,comboBox_1); // view the doctors of this patient
					else
					{
						throw new PatientWithoutDocExpetion("This Patients doesn't got a doctor yet");
					}
				}

				catch(PatientWithoutDocExpetion ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		comboBox.setBounds(27, 111, 238, 22);
		add(comboBox);


		JLabel lblAddPatient = new JLabel("Add Report :");
		lblAddPatient.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblAddPatient.setBounds(143, 21, 158, 39);
		add(lblAddPatient);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(pr==null)
					{
						throw new EmptyField("Please complete all fields");
					}
					if(Hospital.getInstance().addPatientReport(pr.getPatient(), pr.getDoctor(), pr.getDate(), pr.getDisease(), pr.getNote())!=null)
					{
						JOptionPane.showMessageDialog(null, "Report Added", "Message", JOptionPane.INFORMATION_MESSAGE);
						comboBox.setSelectedIndex(0);
						comboBox_1.setSelectedIndex(0);	
						return;
					}
					throw new Exception("Something Wrong");
				}
				catch(EmptyField ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				finally 
				{
					textPane.setText("");
				}
			}
		});

		btnSave.setBounds(382, 257, 97, 37);
		add(btnSave);
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnBack.setBounds(382, 307, 97, 25);
		add(btnBack);

		JLabel lblChooseDoctor = new JLabel("Choose Doctor:");
		lblChooseDoctor.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChooseDoctor.setBounds(37, 146, 132, 16);
		add(lblChooseDoctor);

		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					doc=(Doctor)comboBox_1.getSelectedItem();
					pr=new PatientReport(pat,doc,Calendar.getInstance().getTime(),pat.getDis(),pat.getsDepartment(),pat.checkCondition());
					textPane.setText(pr.toString());
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		comboBox_1.setBounds(27, 175, 238, 22);
		add(comboBox_1);


		JLabel lblTheReport = new JLabel("The Report:");
		lblTheReport.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTheReport.setBounds(12, 212, 87, 16);
		add(lblTheReport);
		ActionsWindow.addPatients(comboBox);
	}
	
}
