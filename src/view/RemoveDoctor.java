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

import Exceptions.CannotRemoveException;
import Model.Doctor;
import Model.Hospital;
import Model.Patient;

public class RemoveDoctor extends JPanel {

	JComboBox comboBox;

	public RemoveDoctor() {
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

		JLabel lblRemoveDoctor = new JLabel("Remove Doctor :");
		lblRemoveDoctor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoveDoctor.setBounds(99, 33, 182, 16);
		add(lblRemoveDoctor);

		JLabel lblPleaseSelectA = new JLabel("Please select a doctor from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(33, 85, 270, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Doctor d=(Doctor)comboBox.getSelectedItem();//select doctor from the list
				if(d!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to delete " +d.getFname() + " " + d.getLname(), "Delete Doctor", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						try {
							if(Hospital.getInstance().removeDoctor(d))
							{
								JOptionPane.showMessageDialog(null,"The Doctor "+d.getFname() + " " + d.getLname() + " delete successfully","Message", JOptionPane.INFORMATION_MESSAGE);
								ActionsWindow.addDoctors(comboBox); // refersh 
							}
							else
							{
								throw new CannotRemoveException("The Doctor "+d.getFname() + " " + d.getLname() + " delete unsuccessfully");
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
		comboBox.setToolTipText("Doctor List");
		comboBox.setBounds(57, 120, 218, 22);
		add(comboBox);
		ActionsWindow.addDoctors(comboBox);

	}
}