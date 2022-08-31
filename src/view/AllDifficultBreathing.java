package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Exceptions.NotACharExecption;
import Model.Department;
import Model.Disease;
import Model.Hospital;
import Model.Patient;

public class AllDifficultBreathing extends JPanel {

	private JComboBox comboBox;
	private JTextArea textPane;

	public AllDifficultBreathing() {
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

		JLabel AllDifficultBreathing = new JLabel("Get All Difficult Breathing :");
		AllDifficultBreathing.setFont(new Font("Tahoma", Font.BOLD, 18));
		AllDifficultBreathing.setBounds(131, 28, 355, 27);
		add(AllDifficultBreathing);

		textPane = new JTextArea(5,20);
		textPane.setFont(new Font("Tahoma", Font.BOLD, 15));
		textPane.setBounds(45, 149, 264, 99);

		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setLocation(23, 155);
		scroll.setSize(415, 101);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);

		JLabel lblSelectSpecilztion = new JLabel("Enter select a department :");
		lblSelectSpecilztion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSelectSpecilztion.setBounds(22, 91, 291, 16);
		add(lblSelectSpecilztion);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LinkedList<Patient> list;
				String details="";
				Department d=(Department)comboBox.getSelectedItem();
				if(d!=null)
				{
					list=Hospital.getInstance().getAllDifficultBreathingPatients(d); // check who with Difficult Breathing 
					for(Patient p : list) // add the patients to view 
					{
						details+= p.toString() + "\n";
					}
					textPane.setText(details);
				}
			}
		});
		comboBox.setBounds(23, 120, 388, 22);
		add(comboBox);
		ActionsWindow.addDepartments(comboBox);

	}	
}