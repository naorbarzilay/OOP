package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Model.Hospital;
import Model.Patient;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientsOnHotel extends JPanel {

	private JTextArea patients;
	
	public PatientsOnHotel() {
		setBackground(Color.ORANGE);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Patients on the hotel :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(122, 24, 210, 36);
		add(lblNewLabel);
		
		JLabel lblNewLabel1 = new JLabel("Picture");
	    lblNewLabel1.setBounds(12, 16, 75, 54);
	    lblNewLabel1.setIcon(ActionsWindow.getProfPic().getIcon());
	    add(lblNewLabel1);
	    
	    setBackground(Color.ORANGE);
		setLayout(null);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnNewButton.setBounds(314, 250, 97, 25);
		add(btnNewButton);
	    patients = new JTextArea(5,20);
		patients.setFont(new Font("Tahoma", Font.BOLD, 15));
		patients.setBounds(45, 149, 264, 99);
		
		JScrollPane scroll2 = new JScrollPane(patients);
		scroll2.setLocation(96, 100);
		scroll2.setSize(415, 101);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll2);
		addPatients();

	}

	private void addPatients() {	
		String details="";
		for(Patient p : Hospital.getInstance().getHotelPatientsById().values())
			details+=p.toString() + "\n" ;
		patients.setText(details);
	}
}
