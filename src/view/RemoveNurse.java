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

import Exceptions.CannotRemoveException;
import Model.Nurse;
import Model.Patient;
import Model.SubDepartment;
import Model.Hospital;

public class RemoveNurse extends JPanel {
	JComboBox comboBox;

	public RemoveNurse() {
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
		btnNewButton.setBounds(314, 250, 97, 25);
		add(btnNewButton);

		JLabel lblRemoveNurse = new JLabel("Remove Nurse :");
		lblRemoveNurse.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoveNurse.setBounds(88, 33, 182, 16);
		add(lblRemoveNurse);

		JLabel lblPleaseSelectA = new JLabel("Please select a Nurse from the list : ");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseSelectA.setBounds(34, 86, 270, 16);
		add(lblPleaseSelectA);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Nurse n=(Nurse)comboBox.getSelectedItem(); // select nurse from the list
				if(n!=null)
				{
					int dialogButton =  JOptionPane.showConfirmDialog(null,  "You want to delete " +n.getFname() + " " + n.getLname(), "Delete Nurse", JOptionPane.YES_NO_OPTION);
					if(dialogButton == JOptionPane.YES_OPTION) {
						try {
							if(Hospital.getInstance().removeNurse(n))
							{
								JOptionPane.showMessageDialog(null,"The Nurse "+n.getFname() + " " + n.getLname() + " delete successfully","Message", JOptionPane.INFORMATION_MESSAGE);
								addNurses();
							}
							else
							{
								throw new CannotRemoveException("The Nurse "+n.getFname() + " " + n.getLname() + " delete unsuccessfully");
							}
						}
						catch(CannotRemoveException e)
						{
							JOptionPane.showMessageDialog(null,e.getMessage(),"Message", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						remove(dialogButton);
					}  
				}
			}
		});
		comboBox.setToolTipText("Nurse List");
		comboBox.setBounds(52, 118, 218, 22);
		add(comboBox);
		addNurses();

	}
	private void addNurses() {	
		comboBox.removeAllItems();
		comboBox.addItem(null);
		if(MainWindow.getInstance().getKindofuser()==2) // if admin , so review all the nurse on the hospital
		{
			for(Nurse n : Hospital.getInstance().getNursesById().values())
				comboBox.addItem(n);
		}
		else if(MainWindow.getInstance().getKindofuser()==0){ // if doctor , so review all the nurse on his department
			for(SubDepartment sd: MainWindow.getInstance().getUser().getsDepartment().getDepartment().getSdepts())
			{
				for(Nurse n: sd.getNurses())
				{
					comboBox.addItem(n);	
				}
			}
		}
		else // if nurse , so review all the nurse on his subdepartment
		{
			for(Nurse n: MainWindow.getInstance().getUser().getsDepartment().getNurses())
			{
				comboBox.addItem(n);	
			}

		}
	}
}

