package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Model.Doctor;
import Model.Hospital;
import Model.Patient;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class HardestNurse extends JPanel {
	JTextArea textPane;

	public HardestNurse() {
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
		btnNewButton.setBounds(107, 257, 97, 25);
		add(btnNewButton);

		JLabel lblRemoveDoctor = new JLabel("Get Hardest Working Nurse :");
		lblRemoveDoctor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoveDoctor.setBounds(99, 29, 270, 25);
		add(lblRemoveDoctor);

		JLabel lblPleaseSelectA = new JLabel("The best nurse in the hospital is :");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(23, 89, 270, 16);
		add(lblPleaseSelectA);
		
		textPane = new JTextArea(5,20);
		textPane.setFont(new Font("Tahoma", Font.BOLD, 15));
		textPane.setBounds(45, 149, 264, 99);
		
		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setLocation(23, 129);
		scroll.setSize(285, 100);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		
		if(Hospital.getInstance().findHardestWorkingNurse()!=null)
		{
		textPane.setText(Hospital.getInstance().findHardestWorkingNurse().toString()); // the function that find the hardest worker nurse
		}
		JLabel lblNewLabe2 = new JLabel("IconMedal");
		lblNewLabe2.setBackground(Color.ORANGE);
		ImageIcon img = new ImageIcon(this.getClass().getResource("/MedalNurse.jpg"));
		lblNewLabe2.setIcon(img);
		lblNewLabe2.setBounds(373, 29, 228, 226);
		add(lblNewLabe2);
	}
}