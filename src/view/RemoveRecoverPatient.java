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

import Exceptions.CannotReleasePatientException;
import Model.Hospital;
import Model.Patient;
import Utils.ReleaseNote;

public class RemoveRecoverPatient extends JPanel {

	JComboBox comboBox;

	public RemoveRecoverPatient() {
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

		JLabel lblRemoveRecoverPatient = new JLabel("Remove Recover Patient :");
		lblRemoveRecoverPatient.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoveRecoverPatient.setBounds(115, 33, 312, 16);
		add(lblRemoveRecoverPatient);

		JLabel lblPleaseSelectA = new JLabel("Please select a patinet from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(32, 82, 270, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Patient p=(Patient)comboBox.getSelectedItem(); // select one patient
				if(p!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to delete " +p.getFname() + " " + p.getLname(), "Delete RecoverPatient", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						try {
							if(Hospital.getInstance().removeRecoverPatient(p).equals("Success")) // check if he got the current note to get out from the hotel 
							{
								JOptionPane.showMessageDialog(null,"The Patient "+p.getFname() + " " + p.getLname() + " recover and delete successfully","Message", JOptionPane.INFORMATION_MESSAGE);
								addRecoverPatients();
							}
							else
							{
								throw new CannotReleasePatientException(p.checkCondition(),ReleaseNote.CAN_GO_HOME);
							}
						}
						catch(CannotReleasePatientException e)
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
		comboBox.setToolTipText("RecoverPatient List");
		comboBox.setBounds(60, 118, 218, 22);
		add(comboBox);
		addRecoverPatients();

	}
	/**
	 * review all the recover patients on combobox
	 */
	private void addRecoverPatients() {	
		comboBox.removeAllItems();
		comboBox.addItem(null);
		if(MainWindow.getInstance().getKindofuser()!=0)
		{
			for(Patient p : Hospital.getInstance().getPatientsById().values())
				comboBox.addItem(p);
		}
		else {
			for(Patient p : MainWindow.getInstance().getUser().getsDepartment().getPatients())
				comboBox.addItem(p);
		}
	}
}