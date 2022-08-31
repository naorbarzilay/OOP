package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Exceptions.EmptyField;
import Exceptions.WrongField;
import Model.Doctor;
import Model.Hospital;
import Model.HospitalUser;
import Model.Nurse;
import Model.Patient;
import Model.SubDepartment;

public class CheckPatient extends JPanel {

	private JComboBox comboBox;
	private JComboBox comboBoxDoctor;
	private JComboBox comboBoxNurse;
	private Patient pat;
	private Doctor doc;
	private Nurse nur;


	public CheckPatient() {
		setBackground(Color.ORANGE);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Picture");
		lblNewLabel.setBounds(12, 16, 75, 54);
		lblNewLabel.setIcon(ActionsWindow.getProfPic().getIcon());
		add(lblNewLabel);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnNewButton.setBounds(362, 300, 97, 25);
		add(btnNewButton);

		JLabel lblPleaseSelectA = new JLabel("Please select a patient from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(12, 78, 270, 16);
		add(lblPleaseSelectA);

		JLabel lblCheckPatient = new JLabel("Check Patient :");
		lblCheckPatient.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCheckPatient.setBounds(115, 33, 182, 16);
		add(lblCheckPatient);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pat=(Patient)comboBox.getSelectedItem();
				if(pat!=null)
				{
					if(MainWindow.getInstance().getKindofuser()==0)
					{
						if(doc.checkPatient(pat))
						{
							JOptionPane.showMessageDialog(null, "The Patient " + pat.getFname() + " " + pat.getLname() + " check by the doctor " + doc.getFname() + " " + doc.getLname(), "Check", JOptionPane.INFORMATION_MESSAGE);

						}
					}
					else if(MainWindow.getInstance().getKindofuser()==1)
					{
						if(nur.checkPatient(pat))
						{
							JOptionPane.showMessageDialog(null, "The Patient " + pat.getFname() + " " + pat.getLname() + " check by the nurse " + nur.getFname() + " " + nur.getLname(), "Check", JOptionPane.INFORMATION_MESSAGE);

						}
					}

				}

			}
		});
		comboBox.setBounds(12, 118, 285, 22);
		add(comboBox);

		JLabel lblPleaseSelectA_2 = new JLabel("Please select a Doctor from the list : ");
		lblPleaseSelectA_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA_2.setBounds(12, 163, 270, 16);
		add(lblPleaseSelectA_2);

		comboBoxDoctor = new JComboBox();
		comboBoxDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(pat==null)
						throw new EmptyField("Plesase select a patient to check");
					doc=(Doctor)comboBoxDoctor.getSelectedItem();
					if(nur!=null)
					{
						throw new WrongField("Please don't select doctor and nurse together");
					}
					if(doc.checkPatient(pat))
					{
						JOptionPane.showMessageDialog(null, "The Patient " + pat.getFname() + " " + pat.getLname() + " check by the doctor " + doc.getFname() + " " + doc.getLname(), "Check", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		comboBoxDoctor.setBounds(12, 192, 285, 22);
		add(comboBoxDoctor);

		JLabel lblPleaseSelectA_2_1 = new JLabel("Please select a Nurse from the list : ");
		lblPleaseSelectA_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA_2_1.setBounds(12, 238, 270, 16);
		add(lblPleaseSelectA_2_1);

		comboBoxNurse = new JComboBox();
		comboBoxNurse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(pat==null)
						throw new EmptyField("Plesase select a patient to check");
					nur=(Nurse)comboBoxNurse.getSelectedItem();
					if(doc!=null)
						throw new WrongField("Please don't select doctor and nurse together");
					if(nur.checkPatient(pat))
						JOptionPane.showMessageDialog(null, "The Patient " + pat.getFname() + " " + pat.getLname() + " check by the nurse " + nur.getFname() + " " + nur.getLname(), "Check", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		comboBoxNurse.setBounds(17, 265, 285, 22);
		add(comboBoxNurse);
		if(MainWindow.getInstance().getKindofuser()==2)// admin can choose anyone
		{
			ActionsWindow.addNurses(comboBoxNurse);
			ActionsWindow.addDoctors(comboBoxDoctor);
			addPatients(2);
		}
		else if(MainWindow.getInstance().getKindofuser()==1) // if nurse so you cant choose other nurse and doctor
		{
			nur=(Nurse)MainWindow.getInstance().getUser();
			comboBoxNurse.setEnabled(false);
			comboBoxDoctor.setEnabled(false);
			addPatients(1);
		}
		else if(MainWindow.getInstance().getKindofuser()==0) // if doctor so you cant choose other nurse and doctor
		{
			doc=(Doctor)MainWindow.getInstance().getUser();
			comboBoxNurse.setEnabled(false);
			comboBoxDoctor.setEnabled(false);
			addPatients(2);
		}

	}
	private void addPatients(int kindofuser) {	// add patients to view 
		comboBox.removeAllItems();
		comboBox.addItem(null);
		if(kindofuser==2) // 2=admin - add all the patients 
		{
			for(Patient p : Hospital.getInstance().getPatientsById().values())
				comboBox.addItem(p);
		}
		else if(kindofuser==1) // 1=nurse - add all the patients in the same subdep of the nurse
		{
			for(Patient p : nur.getsDepartment().getPatients())
				comboBox.addItem(p);
		}
		else if(kindofuser==0) // 0=doctor - add all the patients in the same dep of doctor
		{
			for(SubDepartment sd : doc.getsDepartment().getDepartment().getSdepts())
			{
				for(Patient p : sd.getPatients())
				{
					comboBox.addItem(p);
				}
			}
		}
	}
}
