package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Utils.Specialization;
import javax.swing.JTextField;

import Exceptions.NotACharExecption;

public class DiseaseByRange extends JPanel {

	private JTextArea textPane;
	private JTextField startField;
	private JTextField endField;

	public DiseaseByRange() {
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
		btnNewButton.setBounds(190, 339, 97, 25);
		add(btnNewButton);

		JLabel DoctorBySpec = new JLabel("Disease By Range :");
		DoctorBySpec.setFont(new Font("Tahoma", Font.BOLD, 18));
		DoctorBySpec.setBounds(118, 28, 355, 27);
		add(DoctorBySpec);
		
		textPane = new JTextArea(5,20);
		textPane.setFont(new Font("Tahoma", Font.BOLD, 15));
		textPane.setBounds(45, 149, 264, 99);
		
		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setLocation(28, 211);
		scroll.setSize(415, 100);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
				
		JLabel lblSelectSpecilztion = new JLabel("Enter a chars please : ");
		lblSelectSpecilztion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSelectSpecilztion.setBounds(22, 92, 174, 16);
		add(lblSelectSpecilztion);
		
		JLabel lblCharBegin = new JLabel("char begin :");
		lblCharBegin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCharBegin.setBounds(33, 121, 87, 25);
		add(lblCharBegin);
		
		JLabel lblCharEnded = new JLabel("char ended:");
		lblCharEnded.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCharEnded.setBounds(215, 121, 87, 25);
		add(lblCharEnded);
		
		startField = new JTextField();
		startField.setBounds(132, 122, 47, 25);
		add(startField);
		startField.setColumns(10);
		
		endField = new JTextField();
		endField.setColumns(10);
		endField.setBounds(314, 122, 47, 25);
		add(endField);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if((startField.getText().length()>1)||(endField.getText().length()>1)) // length of the string 
				{
					throw new NotACharExecption("You entered more than one char");
				}
				if((startField.getText().isEmpty())||(endField.getText().isEmpty())) // one field is empty
				{
					throw new NotACharExecption("Please enter a char");
				}
				char start=(char)startField.getText().toLowerCase().toCharArray()[0];
				char end= (char)endField.getText().toLowerCase().toCharArray()[0];
				if((start<'a')||(start>'z')) // check if the user enterd a letter
				{
					throw new NotACharExecption("Enter a letter on start field please"); 
				}
				if((end<'a')||(end>'z'))
				{
					throw new NotACharExecption("Enter a letter on end field please");
				}
				
				TreeSet<Disease> set=Hospital.getInstance().getDiseasesByRange(start, end);
				String details=" ";
				while(!set.isEmpty())
				{
				   details+= set.pollFirst().toString() + '\n';
				}
				textPane.setText(details);
			}
				catch(NotACharExecption ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnShow.setBounds(190, 173, 97, 25);
		add(btnShow);
		
	}
}