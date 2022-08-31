package view;

import javax.swing.JPanel;

import Exceptions.CannotRemoveException;
import Model.Hospital;
import Model.Patient;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class RemovePatient extends JPanel {

	private JComboBox comboBox;

	public RemovePatient() {
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
		btnNewButton.setBounds(314, 250, 97, 25);
		add(btnNewButton);

		JLabel lblRemovePatient = new JLabel("Remove Patient :");
		lblRemovePatient.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemovePatient.setBounds(106, 33, 182, 16);
		add(lblRemovePatient);

		JLabel lblPleaseSelectA = new JLabel("Please select a patient from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(32, 80, 270, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Patient p=(Patient)comboBox.getSelectedItem(); // select patinet from the list
				if(p!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to delete " +p.getFname() + " " + p.getLname(), "Delete Patient", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						try {
							if(Hospital.getInstance().removePatient(p))
							{
								JOptionPane.showMessageDialog(null,"The patient "+p.getFname() + " " + p.getLname() + " delete successfully","Message", JOptionPane.INFORMATION_MESSAGE);
								addPatients();
							}
							else
							{
								throw new CannotRemoveException("The patient "+p.getFname() + " " + p.getLname() + " delete unsuccessfully");
							}
						}
						catch(CannotRemoveException e)
						{
							JOptionPane.showMessageDialog(null,e.getMessage(),"Message", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						remove(dialogButton);
					}
				}
			}
		});
		comboBox.setToolTipText("Patient List");
		comboBox.setBounds(57, 117, 218, 22);
		add(comboBox);
		addPatients();

	}
	private void addPatients() {
		comboBox.removeAllItems();
		comboBox.addItem(null);
		if(MainWindow.getInstance().getKindofuser()!=0) // if not a doctor , add all patients 
		{
			for(Patient p : Hospital.getInstance().getPatientsById().values())
				comboBox.addItem(p);
		}
		else { // if a doctor , add all patients on his department
			for(Patient p : MainWindow.getInstance().getUser().getsDepartment().getPatients())
				comboBox.addItem(p);
		}
	}
}
