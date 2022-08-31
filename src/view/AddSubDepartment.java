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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Exceptions.EmptyField;
import Model.Department;
import Model.Disease;
import Model.Hospital;
import Model.SubDepartment;
import Utils.Specialization;

public class AddSubDepartment extends JPanel {
	private JComboBox<Department> comboBox;

	/**
	 * Create the panel.
	 */
	public AddSubDepartment() {
		setBackground(Color.ORANGE);
		setForeground(Color.LIGHT_GRAY);
		setLayout(null);
		JLabel lblNewLabel = new JLabel("Picture");
		lblNewLabel.setBounds(12, 16, 75, 54);
		lblNewLabel.setIcon(ActionsWindow.getProfPic().getIcon());
		add(lblNewLabel);
		JLabel lblName = new JLabel("Select Department");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblName.setBounds(22, 110, 140, 16);
		add(lblName);


		comboBox = new JComboBox();
		comboBox.setBounds(174, 108, 264, 22);
		add(comboBox);


		JLabel lblAddDepartment = new JLabel("Add Sub Department :");
		lblAddDepartment.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblAddDepartment.setBounds(128, 21, 226, 38);
		add(lblAddDepartment);

		JButton btnSave = new JButton("Add SubDepartment");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Department dep=(Department)comboBox.getSelectedItem(); // choose a dep
				try {
					if(dep==null)
						throw new EmptyField("Please complete all fields");
					SubDepartment sb=new SubDepartment(dep);
					if(dep.addSubDepartment(sb))
					{
						JOptionPane.showMessageDialog(null, sb.toString() + " Added ", "Message", JOptionPane.INFORMATION_MESSAGE);					
					}
					else
						throw new Exception("Sub Department doesn't added");
				}
				catch(EmptyField e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setBounds(229, 170, 157, 37);
		add(btnSave);
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnBack.setBounds(319, 262, 97, 25);
		add(btnBack);
		ActionsWindow.addDepartments(comboBox);
	}
}