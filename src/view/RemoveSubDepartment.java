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
import Model.SubDepartment;
import Model.Department;
import Model.Hospital;

public class RemoveSubDepartment extends JPanel {

	JComboBox comboBox;
	JComboBox comboBox2;

	public RemoveSubDepartment() {
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

		JLabel lblRemoveSubDepartment = new JLabel("Remove SubDepartment :");
		lblRemoveSubDepartment.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoveSubDepartment.setBounds(116, 33, 242, 16);
		add(lblRemoveSubDepartment);

		JLabel lblPleaseSelectA = new JLabel("Please select a department from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(22, 95, 303, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Department d=(Department)comboBox.getSelectedItem(); // first select dep
				if(d!=null)
				{
					addSubDepartments(d.getId());
				}
			}
		});
		comboBox.setToolTipText("SubDepartment List");
		comboBox.setBounds(25, 136, 242, 22);
		add(comboBox);

		JLabel lblPleaseSelectA_2 = new JLabel("Please select a sub department from the list : ");
		lblPleaseSelectA_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA_2.setBounds(25, 175, 330, 16);
		add(lblPleaseSelectA_2);

		comboBox2 = new JComboBox();
		comboBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubDepartment sd=(SubDepartment)comboBox2.getSelectedItem(); // second select subdep from the selected dep
				if(sd!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to delete sd number : " + sd.getId(), "Delete Department", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						try {
							if(Hospital.getInstance().removeSubDepartment(sd))
							{
								JOptionPane.showMessageDialog(null,"The Sub Department "+sd.getId() + " delete successfully","Message", JOptionPane.INFORMATION_MESSAGE);
								addSubDepartments(sd.getDepartment().getId());
							}
							else
							{
								throw new CannotRemoveException("The Sub Department "+sd.getId() + " delete unsuccessfully");
							}
						}
						catch(CannotRemoveException ex)
						{
							JOptionPane.showMessageDialog(null,ex.getMessage(),"Message", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						remove(dialogButton);
					}  
				}
			}
		});
		comboBox2.setToolTipText("SubDepartment List");
		comboBox2.setBounds(25, 204, 242, 22);
		add(comboBox2);
		ActionsWindow.addDepartments(comboBox);

	}
	private void addSubDepartments(int depId) {	
		comboBox2.removeAllItems();
		comboBox2.addItem(null);
		if(Hospital.getInstance().getDepartmentsById().containsKey(depId))
		{
			for(SubDepartment sd : Hospital.getInstance().getDepartmentsById().get(depId).getSdepts())
				comboBox2.addItem(sd);
		}
	}
}