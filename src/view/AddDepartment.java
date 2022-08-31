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
import Model.Doctor;
import Model.Hospital;
import Model.Main;
import Model.SubDepartment;
import Utils.Specialization;

public class AddDepartment extends JPanel {
	private JTextField textField;
	private Specialization [] specials=new Specialization[]{Specialization.UROLOGY,Specialization.PSYCHIATRY,
			Specialization.OPHTHALMOLOGY,Specialization.GYNECOLOGY,Specialization.NEUROLOGY,Specialization.FAMILY,Specialization.EMERGENCY};


	public AddDepartment() {
		setBackground(Color.ORANGE);
		setForeground(Color.LIGHT_GRAY);
		setLayout(null);

		textField = new JTextField();
		textField.setBounds(45, 121, 116, 22);
		add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Picture");
		lblNewLabel.setBounds(12, 16, 75, 54);
		lblNewLabel.setIcon(ActionsWindow.getProfPic().getIcon());
		add(lblNewLabel);

		JLabel lblName = new JLabel("Department Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblName.setBounds(37, 92, 140, 16);
		add(lblName);


		JComboBox comboBox = new JComboBox(specials);
		comboBox.setBounds(281, 121, 98, 22);
		add(comboBox);

		JLabel lblSpcialtizon = new JLabel("Specialzation:");
		lblSpcialtizon.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSpcialtizon.setBounds(271, 92, 116, 16);
		add(lblSpcialtizon);

		JLabel lblAddDepartment = new JLabel("Add Department: ");
		lblAddDepartment.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblAddDepartment.setBounds(115, 21, 205, 38);
		add(lblAddDepartment);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name=textField.getText(); // name of department
				Specialization special=(Specialization)comboBox.getSelectedItem(); // Specialization of department 
				try {
					if(name.isEmpty())
						throw new EmptyField("Please complete all fields");

					if(checkNameandSpe(name,special))
					{
						int result = JOptionPane.showConfirmDialog(null, "You already got dep with the same name and special , you want to cancel ",null, JOptionPane.YES_NO_OPTION);
						if(result == JOptionPane.YES_OPTION) 
							return;	
					}
					Department d=new Department(name,special);
					if(Hospital.getInstance().addDepartment(d))
					{
						JOptionPane.showMessageDialog(null, d.toString() + " Added ", "Message", JOptionPane.INFORMATION_MESSAGE);
						textField.setText("");
					}
					else
						throw new Exception("Department doesn't added"); // something wrong 
				}
				catch(EmptyField e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setBounds(162, 191, 116, 37);
		add(btnSave);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnBack.setBounds(319, 262, 97, 25);
		add(btnBack);
	}
	private boolean checkNameandSpe(String name,Specialization special)
	{
		for(Department d : Hospital.getInstance().getDepartmentsById().values())
		{
			if(d.getDeptName().equals(name)&&(d.getSpec()==special))
			{
				return true;
			}
		}
		return false;
	}
}
