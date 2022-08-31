package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Model.Department;
import Model.Hospital;
import Model.Patient;

public class TreatPatients extends JPanel {


	private JComboBox comboBox;
	private JTextArea textPane;

	public TreatPatients() {
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
		btnNewButton.setBounds(341, 262, 97, 25);
		add(btnNewButton);

		JLabel TreatPatients = new JLabel("Treast all patients :");
		TreatPatients.setFont(new Font("Tahoma", Font.BOLD, 18));
		TreatPatients.setBounds(99, 28, 355, 27);
		add(TreatPatients);

		textPane = new JTextArea(5,20);
		textPane.setFont(new Font("Tahoma", Font.BOLD, 15));
		textPane.setBounds(45, 149, 264, 99);

		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setLocation(23, 155);
		scroll.setSize(415, 101);
		add(scroll);

		JLabel lblSelectSpecilztion = new JLabel("Enter select a department :");
		lblSelectSpecilztion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSelectSpecilztion.setBounds(22, 77, 291, 16);
		add(lblSelectSpecilztion);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreeSet<Patient> list;
				String details="";
				Department d=(Department)comboBox.getSelectedItem();
				if(d!=null)
				{
					list=Hospital.getInstance().treatPatients(d);
					for(Patient p : list)
					{
						details+= p.toString() + "\n";
					}
					textPane.setText(details);
				}
			}
		});
		comboBox.setBounds(12, 106, 388, 22);
		add(comboBox);
		ActionsWindow.addDepartments(comboBox);
	}	
}