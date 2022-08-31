package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Exceptions.EmptyField;
import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Model.SubDepartment;
import Utils.Specialization;
import Utils.Symptoms;
import javax.swing.JList;
import javax.swing.JCheckBox;

public class AddDisease extends JPanel {

	private JTextField txtDiseaseName;
	private HashSet<Symptoms> syms=new HashSet<Symptoms>(); // contian the sypmtoms of the new disease
	
	public AddDisease() {
		setBackground(Color.ORANGE);
		setForeground(Color.LIGHT_GRAY);
		setLayout(null);

		txtDiseaseName = new JTextField();
		txtDiseaseName.setText("Disease Name");
		txtDiseaseName.setBounds(154, 80, 116, 22);
		add(txtDiseaseName);
		txtDiseaseName.setColumns(10);


		JLabel lblNewLabel3 = new JLabel("Picture");
		lblNewLabel3.setBounds(13, 13, 75, 54);
		lblNewLabel3.setIcon(ActionsWindow.getProfPic().getIcon());
		add(lblNewLabel3);

		JLabel lblSpcialtizon = new JLabel("Symptoms:");
		lblSpcialtizon.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSpcialtizon.setBounds(13, 137, 112, 16);
		add(lblSpcialtizon);

		JLabel lblAddDisease = new JLabel("Add Disease :");
		lblAddDisease.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblAddDisease.setBounds(112, 29, 158, 16);
		add(lblAddDisease);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String dname=txtDiseaseName.getText();
				try {
					if(dname.isEmpty())
						throw new EmptyField("Please complete all fields");
					Disease d=new Disease(dname); // create new disease 
					if(Hospital.getInstance().addDisease(d))
					{
						d.setSymptoms(syms);
						JOptionPane.showMessageDialog(null, d.toString()+" Added", "Message", JOptionPane.INFORMATION_MESSAGE);					
					}
					else
						throw new Exception("Disease not added");
				}
				catch(EmptyField e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnSave.setBounds(330, 217, 97, 32);
		add(btnSave);


		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new ActionsWindow(MainWindow.getKindofuser()));
			}
		});
		btnBack.setBounds(330, 262, 97, 25);
		add(btnBack);

		JLabel lblNewLabel = new JLabel("Disease Name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(12, 83, 130, 16);
		add(lblNewLabel);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Fever");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxNewCheckBox.isSelected())
				{
					syms.add(Symptoms.FEVER);

				}
				else if(syms.contains(Symptoms.FEVER))
				{
					syms.remove(Symptoms.FEVER);
				}
			}
		});
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.BOLD, 15));
		chckbxNewCheckBox.setBackground(Color.ORANGE);
		chckbxNewCheckBox.setBounds(12, 198, 113, 25);
		add(chckbxNewCheckBox);

		JCheckBox chckbxBackhaches = new JCheckBox("Backhaches");
		chckbxBackhaches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxBackhaches.isSelected())
				{
					syms.add(Symptoms.BACKHACHES);
				}
				else if(syms.contains(Symptoms.BACKHACHES))
				{
					syms.remove(Symptoms.BACKHACHES);
				}
			}
		});
		chckbxBackhaches.setFont(new Font("Tahoma", Font.BOLD, 15));
		chckbxBackhaches.setBackground(Color.ORANGE);
		chckbxBackhaches.setBounds(12, 228, 130, 25);
		add(chckbxBackhaches);

		JCheckBox chckbxDiffubreathing = new JCheckBox("Difficulty Breathing");
		chckbxDiffubreathing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxDiffubreathing.isSelected())
				{
					syms.add(Symptoms.DIFFICULTY_BREATHING);
				}
				else if(syms.contains(Symptoms.DIFFICULTY_BREATHING))
				{
					syms.remove(Symptoms.DIFFICULTY_BREATHING);
				}
			}
		});
		chckbxDiffubreathing.setFont(new Font("Tahoma", Font.BOLD, 14));
		chckbxDiffubreathing.setBackground(Color.ORANGE);
		chckbxDiffubreathing.setBounds(142, 168, 174, 25);
		add(chckbxDiffubreathing);

		JCheckBox chckbxFainting = new JCheckBox("Fainting");
		chckbxFainting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxFainting.isSelected())
				{
					syms.add(Symptoms.FAINTING);
				}
				else if(syms.contains(Symptoms.FAINTING))
				{
					syms.remove(Symptoms.FAINTING);
				}
			}
		});
		chckbxFainting.setBackground(Color.ORANGE);
		chckbxFainting.setFont(new Font("Tahoma", Font.BOLD, 15));
		chckbxFainting.setBounds(142, 198, 113, 25);
		add(chckbxFainting);

		JCheckBox chckbxTiredness = new JCheckBox("Tiredness");
		chckbxTiredness.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxTiredness.isSelected())
				{
					syms.add(Symptoms.TIREDNESS);
				}
				else if(syms.contains(Symptoms.TIREDNESS))
				{
					syms.remove(Symptoms.TIREDNESS);
				}
			}
		});
		chckbxTiredness.setFont(new Font("Tahoma", Font.BOLD, 15));
		chckbxTiredness.setBackground(Color.ORANGE);
		chckbxTiredness.setBounds(142, 228, 113, 25);
		add(chckbxTiredness);

		JCheckBox chckbxCough = new JCheckBox("Cough");
		chckbxCough.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxCough.isSelected())
				{
					syms.add(Symptoms.COUGH);
				}
				else if(syms.contains(Symptoms.COUGH))
				{
					syms.remove(Symptoms.COUGH);
				}
			}
		});
		chckbxCough.setFont(new Font("Tahoma", Font.BOLD, 15));
		chckbxCough.setBackground(Color.ORANGE);
		chckbxCough.setBounds(12, 168, 113, 25);
		add(chckbxCough);

	}
}


