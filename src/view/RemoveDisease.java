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
import Model.Disease;
import Model.Hospital;

public class RemoveDisease extends JPanel {

	JComboBox comboBox;

	public RemoveDisease() {
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

		JLabel lblRemoveDisease = new JLabel("Remove Disease :");
		lblRemoveDisease.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoveDisease.setBounds(108, 33, 242, 16);
		add(lblRemoveDisease);

		JLabel lblPleaseSelectA = new JLabel("Please select a disease from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(33, 82, 303, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Disease d=(Disease)comboBox.getSelectedItem(); // select disease from the list
				if(d!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to delete " + d.getName(), "Delete Disease", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						try {
							if(Hospital.getInstance().removeDisease(d))
							{
								JOptionPane.showMessageDialog(null,"The Disease "+d.getName() + " delete successfully","Message", JOptionPane.INFORMATION_MESSAGE);
								ActionsWindow.addDisease(comboBox); // refersh
							}
							else
							{
								throw new CannotRemoveException("The Disease "+d.getName() + " delete unsuccessfully");
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
		comboBox.setToolTipText("Disease List");
		comboBox.setBounds(57, 123, 218, 22);
		add(comboBox);
		ActionsWindow.addDisease(comboBox);

	}
}