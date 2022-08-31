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

import Model.Hospital;
import Model.Patient;

public class SendToHotel extends JPanel {


	JComboBox comboBox;

	public SendToHotel() {
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

		JLabel lblRemoveRecoverPatient = new JLabel("Send patient to hotel :");
		lblRemoveRecoverPatient.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoveRecoverPatient.setBounds(101, 23, 270, 37);
		add(lblRemoveRecoverPatient);

		JLabel lblPleaseSelectA = new JLabel("Please select a patinet from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(22, 87, 270, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Patient p=(Patient)comboBox.getSelectedItem(); // select patients from the list 
				if(p!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to transfer " +p.getFname() + " " + p.getLname(), "Send to hotel", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						String msg=Hospital.getInstance().removeRecoverPatient(p);
						if(msg.equals("Success")) // check if he got the current note
						{
							JOptionPane.showMessageDialog(null,"The Patient "+p.getFname() + " " + p.getLname() + " move to hotel successfully","Message", JOptionPane.INFORMATION_MESSAGE);
							addPatients();
						}
						else
						{
							JOptionPane.showMessageDialog(null,msg,"Message", JOptionPane.ERROR_MESSAGE); // msg get the expection msg 
						}
					}
					else {
						remove(dialogButton);
					}  
				}
			}
		});
		comboBox.setToolTipText("Patient List");
		comboBox.setBounds(44, 126, 218, 22);
		add(comboBox);
		addPatients();

	}
	private void addPatients() {	
		comboBox.removeAllItems();
		comboBox.addItem(null);
		if(MainWindow.getInstance().getKindofuser()!=0) // if not a doctor
		{
		for(Patient p : Hospital.getInstance().getPatientsById().values()) // add all patients 
			comboBox.addItem(p);
		}
		else { // if the user is doctor
		for(Patient p : MainWindow.getInstance().getUser().getsDepartment().getPatients()) // add only patients from his dep
			comboBox.addItem(p);
		}
	}
}