package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.Doctor;
import Model.Hospital;
import Model.Patient;

import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BadConditionPatients extends JPanel {

	private JTextArea textPane;
	private JComboBox comboBox;

	public BadConditionPatients() {
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
		btnNewButton.setBounds(306, 261, 97, 25);
		add(btnNewButton);

		JLabel lblCondition = new JLabel("All Bad Condition Patients :");
		lblCondition.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCondition.setBounds(109, 33, 270, 16);
		add(lblCondition);

		JLabel lblPleaseSelectA = new JLabel("Please select a doctor from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(22, 78, 270, 16);
		add(lblPleaseSelectA);
		
		textPane = new JTextArea(5,20);
		textPane.setFont(new Font("Tahoma", Font.BOLD, 15));
		textPane.setBounds(45, 149, 264, 99);
		
		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setLocation(23, 148);
		scroll.setSize(333, 100);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Patient> patients = new ArrayList<>();
				Doctor d=(Doctor)comboBox.getSelectedItem();
				patients=Hospital.getInstance().getAllBadConditionPatients(d); // get all bad condition patients 
				String details=" ";
				for(Patient p : patients) // add patients to view 
				{
					details+=p.toString() + '\n';
				}	
				textPane.setText(details);	
			}
		});

		comboBox.setToolTipText("Doctor List");
		comboBox.setBounds(23, 107, 218, 22);
		add(comboBox);
		ActionsWindow.addDoctors(comboBox);

	}
}