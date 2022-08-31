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
import Model.Department;
import Model.Hospital;

public class RemoveDepartment extends JPanel {
	JComboBox comboBox;

	public RemoveDepartment() {
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

		JLabel lblRemoveDepartment = new JLabel("Remove Department :");
		lblRemoveDepartment.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoveDepartment.setBounds(118, 33, 242, 16);
		add(lblRemoveDepartment);

		JLabel lblPleaseSelectA = new JLabel("Please select a department from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(22, 92, 303, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Department d=(Department)comboBox.getSelectedItem(); //select a dep
				if(d!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to delete " + d.getDeptName(), "Delete Department", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						try {
							if(Hospital.getInstance().removeDepartment(d))
							{
								JOptionPane.showMessageDialog(null,"The Department "+d.getDeptName() + " delete successfully","Message", JOptionPane.INFORMATION_MESSAGE);
								ActionsWindow.addDepartments(comboBox);
							}
							else
							{
								throw new CannotRemoveException("The Department "+d.getDeptName() + " delete unsuccessfully");
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
		comboBox.setToolTipText("Department List");
		comboBox.setBounds(62, 136, 218, 22);
		add(comboBox);
		ActionsWindow.addDepartments(comboBox);

	}

}