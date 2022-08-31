package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Model.Doctor;
import Model.Hospital;
import Model.Patient;

public class CriticalSteroidsPatients extends JPanel {

	private JTextArea textPane;

	public CriticalSteroidsPatients() {
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

		JLabel CriticalSteroidsPatients = new JLabel("All Critical Steroids Patients :");
		CriticalSteroidsPatients.setFont(new Font("Tahoma", Font.BOLD, 18));
		CriticalSteroidsPatients.setBounds(38, 94, 270, 16);
		add(CriticalSteroidsPatients);
		
		textPane = new JTextArea(5,20);
		textPane.setFont(new Font("Tahoma", Font.BOLD, 15));
		textPane.setBounds(45, 149, 264, 99);
		
		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setLocation(23, 148);
		scroll.setSize(285, 100);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		
		TreeSet<Patient> set=Hospital.getInstance().getCriticalSteroidsNeuPatients();
		String details=" ";
		while(!set.isEmpty())
		{
		   details+= set.pollFirst().toString() + '\n'; // add the patients to long string 
		}
		textPane.setText(details); // view the string 
	}
}