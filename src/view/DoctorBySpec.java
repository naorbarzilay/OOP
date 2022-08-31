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

import Model.Doctor;
import Model.Hospital;
import Model.Patient;
import Utils.Specialization;

public class DoctorBySpec extends JPanel {
	private JComboBox comboBox;
	private JTextArea textPane;
	private Specialization [] specials=new Specialization[]{null ,Specialization.UROLOGY,Specialization.PSYCHIATRY,
			Specialization.OPHTHALMOLOGY,Specialization.GYNECOLOGY,Specialization.NEUROLOGY,Specialization.FAMILY,Specialization.EMERGENCY};

	public DoctorBySpec() {
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
		btnNewButton.setBounds(327, 262, 97, 25);
		add(btnNewButton);

		JLabel DoctorBySpec = new JLabel("All Doctor with selection Speciealztion :");
		DoctorBySpec.setFont(new Font("Tahoma", Font.BOLD, 18));
		DoctorBySpec.setBounds(115, 33, 355, 16);
		add(DoctorBySpec);
		
		textPane = new JTextArea(5,20);
		textPane.setFont(new Font("Tahoma", Font.BOLD, 15));
		textPane.setBounds(45, 149, 264, 99);
		
		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setLocation(82, 134);
		scroll.setSize(342, 101);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		
		comboBox = new JComboBox(specials);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Specialization special=(Specialization)comboBox.getSelectedItem();
				TreeSet<Doctor> set=Hospital.getInstance().getDoctorBySpec(special);
						String details=" ";
						while(!set.isEmpty())
						{
							Doctor d=set.pollFirst();
						   details+= d.toString() + " Number of shift: " + d.getShiftCounter() + '\n'; // get the details from the function and put them on string 
						}
						textPane.setText(details);
			}
		});
		comboBox.setBounds(182, 85, 98, 22);
		add(comboBox);
		
		JLabel lblSelectSpecilztion = new JLabel("Select Specilztion :");
		lblSelectSpecilztion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSelectSpecilztion.setBounds(22, 87, 143, 16);
		add(lblSelectSpecilztion);
		
	}
}