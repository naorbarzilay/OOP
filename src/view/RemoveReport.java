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

import Model.PatientReport;
import Model.Hospital;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Exceptions.CannotRemoveException;

import javax.swing.JTextArea;

public class RemoveReport extends JPanel {

	private JComboBox comboBox;
	private JTextPane textPane;
	private PatientReport rep;

	public RemoveReport() {
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
		btnNewButton.setBounds(75, 331, 97, 25);
		add(btnNewButton);

		JLabel lblRemovePatientReport = new JLabel("Remove Patient Report :");
		lblRemovePatientReport.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemovePatientReport.setBounds(152, 27, 242, 28);
		add(lblRemovePatientReport);

		JLabel lblPleaseSelectA = new JLabel("Please select a report from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(34, 84, 303, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rep=Hospital.getInstance().getReportsById().get((Integer)comboBox.getSelectedItem()); // print the report before delete him 
				if(rep!=null)
				{
					textPane.setText(rep.toString()); 
				}
				else
				{
					textPane.setText(" ");
				}
			}
		});
		comboBox.setToolTipText("PatientReport List");
		comboBox.setBounds(75, 115, 149, 22);
		add(comboBox);

		JLabel lblTheReport = new JLabel("The Report:");
		lblTheReport.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTheReport.setBounds(34, 154, 105, 16);
		add(lblTheReport);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rep=Hospital.getInstance().getReportsById().get((Integer)comboBox.getSelectedItem()); // select report by id
				if(rep!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to delete " + rep.getId() + " report", "Delete PatientReport", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						try {
							if(Hospital.getInstance().removePatientReport(rep)) // check if we can delete him and print message according to result 
							{
								JOptionPane.showMessageDialog(null,"The PatientReport "+rep.getId() + " delete successfully","Message", JOptionPane.INFORMATION_MESSAGE);
								addPatientReports();
							}
							else
							{
								throw new CannotRemoveException("The PatientReport "+rep.getId()  + " delete unsuccessfully");
							}
						}
						catch(CannotRemoveException ex)
						{
							JOptionPane.showMessageDialog(null,ex.getMessage(),"Message", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						remove(dialogButton);
					} 
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Please select report","Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.setBounds(239, 331, 97, 25);
		add(btnDelete);

		textPane = new JTextPane();
		textPane.setBounds(34, 195, 386, 120);
		add(textPane);
		addPatientReports();

	}
	/*
	 * add the reports by id 
	 */
	private void addPatientReports() {	
		comboBox.removeAllItems();
		for(PatientReport pr : Hospital.getInstance().getReportsById().values())
			comboBox.addItem(pr.getId());
	}
}