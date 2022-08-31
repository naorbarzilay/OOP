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

import Model.Hospital;
import Model.Patient;
import Model.SubDepartment;

public class BestStatusSubDep extends JPanel {

	private JTextArea textPane;

	public BestStatusSubDep() {
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
		btnNewButton.setBounds(176, 240, 97, 25);
		add(btnNewButton);

		JLabel BestStatusSubDep = new JLabel("Best Status SubDep :");
		BestStatusSubDep.setFont(new Font("Tahoma", Font.BOLD, 18));
		BestStatusSubDep.setBounds(99, 29, 270, 25);
		add(BestStatusSubDep);

		textPane = new JTextArea(5,20);
		textPane.setFont(new Font("Tahoma", Font.BOLD, 15));
		textPane.setBounds(45, 149, 264, 99);

		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setLocation(23, 97);
		scroll.setSize(391, 100);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);

		TreeSet<SubDepartment> set=Hospital.getInstance().getBestStatusSubDepartments(); // check the best subdep
		String details="";
		while(!set.isEmpty())
		{
			SubDepartment s=set.pollFirst();
			if(s!=null)
				details+= s.toString() + "\n"; // add them to the text pane 
		}
		textPane.setText(details);
	}
}