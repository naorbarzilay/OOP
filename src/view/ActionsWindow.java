package view;

import javax.swing.JPanel;

import Model.Department;
import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Model.Nurse;
import Model.Patient;
import Model.PatientReport;
import Model.SubDepartment;

import javax.swing.JMenuBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class ActionsWindow extends JPanel {

	private JMenuItem mntmRemoveDoctor;
	private JMenuItem mntmAddDepartment;
	private JMenuItem mntmAddSubDepartment;
	private JMenuItem mntmAddDisease;
	private JMenuItem mntmAddReport;
	private JMenuItem mntmRemoveRecoverPatinet;
	private JMenuItem mntmSendToHotel;
	private JMenuItem mntmRemoveSubDepartment;
	private JMenuItem mntmRemoveReport;
	private JMenuItem mntmRemoveDisease;
	private JMenuItem mntmAddNurse;
	private JMenuItem mntmRemoveNurse;
	private JMenuItem mntmNewMenuItem_2;
	private JLabel welcomeLabel;
	private static JLabel profpic; // the litte picture of the user 

	public ActionsWindow(int kindofuser) // 2 admin , 1 nurse , 0 doctor {
	{
		setBackground(Color.ORANGE);    
		setLayout(null);
		setBounds(100, 100, 637, 365);
		welcomeLabel = new JLabel();
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		welcomeLabel.setBounds(136, 45, 501, 54);
		add(welcomeLabel);
		profpic = new JLabel("Picture");
		profpic.setBounds(27, 45, 75, 54);
		switch(kindofuser) // change the picture according the user that login
		{
		case -1:
			break;
		case 2: // if admin , change to defult pic of admin 
			Image img = new ImageIcon(this.getClass().getResource("/admin.jpg")).getImage();
			profpic.setIcon((this.ResizeImage(img)));
			welcomeLabel.setText("You are conncted us : Admin");
		case 1: // if nurse , change to the pic of the nurse 
			if(MainWindow.getInstance().getUser()!=null) {
				profpic.setIcon(this.ResizeImage(MainWindow.getInstance().getUser().getImgPath()));
				welcomeLabel.setText("You are conncted us : " + MainWindow.getInstance().getUser().getFname() +" "+MainWindow.getInstance().getUser().getLname());
			}
		case 0: // if doctor , change to the pic of the doctor 
			if(MainWindow.getInstance().getUser()!=null) {
				profpic.setIcon(this.ResizeImage(MainWindow.getInstance().getUser().getImgPath()));
				welcomeLabel.setText("You are conncted us : " + MainWindow.getInstance().getUser().getFname() +" "+MainWindow.getInstance().getUser().getLname());

			}
		}
		add(profpic);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0,(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() , 26);
		add(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Save");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					serialize(Hospital.getInstance());
					JOptionPane.showMessageDialog(null,"Save Hospital","Message", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Logout");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int dialogButton = JOptionPane.YES_NO_OPTION;
				JOptionPane.showConfirmDialog (null, "Are you sure?","WARNING", dialogButton);
				if(dialogButton == JOptionPane.YES_OPTION) {
					MainWindow.changeWindow(new LoginWindow());
					JOptionPane.showMessageDialog(null,"You are logout from the system","Message", JOptionPane.INFORMATION_MESSAGE);
				}
				if(dialogButton == JOptionPane.NO_OPTION) {
					remove(dialogButton);
				}  
			}
		});

		JMenuItem mntmExport = new JMenuItem("Export");
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					deserialize();
					JOptionPane.showMessageDialog(null, "The Hospital was deserialized successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu.add(mntmExport);
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenu mnNewMenu_1 = new JMenu("Add");
		menuBar.add(mnNewMenu_1);

		mntmAddNurse = new JMenuItem("Add Nurse");
		mntmAddNurse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new AddNurse());
			}
		});
		mnNewMenu_1.add(mntmAddNurse);

		JMenuItem mntmAddPatient = new JMenuItem("Add Patient");
		mntmAddPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.changeWindow(new AddPatient());
			}
		});
		mnNewMenu_1.add(mntmAddPatient);

		mntmNewMenuItem_2 = new JMenuItem("Add Doctor");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new AddDoctor());
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);

		mntmAddDepartment = new JMenuItem("Add Department");
		mntmAddDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new AddDepartment());
			}
		});
		mnNewMenu_1.add(mntmAddDepartment);

		mntmAddSubDepartment = new JMenuItem("Add Sub Department");
		mntmAddSubDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new AddSubDepartment());
			}
		});
		mnNewMenu_1.add(mntmAddSubDepartment);

		mntmAddDisease = new JMenuItem("Add Disease");
		mntmAddDisease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.changeWindow(new AddDisease());
			}
		});
		mnNewMenu_1.add(mntmAddDisease);

		mntmAddReport = new JMenuItem("Add Report");
		mntmAddReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new AddReport());
			}
		});
		mnNewMenu_1.add(mntmAddReport);

		JMenu mnRemove = new JMenu("Remove");
		menuBar.add(mnRemove);

		mntmRemoveNurse = new JMenuItem("Remove Nurse");
		mntmRemoveNurse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new RemoveNurse());
			}
		});

		JMenu mnRemovePatinet = new JMenu("Remove Patinet");
		mnRemove.add(mnRemovePatinet);

		JMenuItem mntmRemovePatient = new JMenuItem("Remove Patient");
		mnRemovePatinet.add(mntmRemovePatient);

		mntmRemoveRecoverPatinet = new JMenuItem("Remove Recover Patinet");
		mntmRemoveRecoverPatinet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new RemoveRecoverPatient());
			}	
		});
		mnRemovePatinet.add(mntmRemoveRecoverPatinet);

		mntmSendToHotel = new JMenuItem("Send To Hotel");
		mntmSendToHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new SendToHotel());
			}
		});
		mnRemovePatinet.add(mntmSendToHotel);
		mntmRemovePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.changeWindow(new RemovePatient());
			}
		});
		mnRemove.add(mntmRemoveNurse);

		mntmRemoveDoctor = new JMenuItem("Remove Doctor");
		mntmRemoveDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new RemoveDoctor());
			}
		});
		mnRemove.add(mntmRemoveDoctor);

		JMenuItem mntmRemoveDepartment = new JMenuItem("Remove Department");
		mntmRemoveDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new RemoveDepartment());
			}
		});
		mnRemove.add(mntmRemoveDepartment);

		mntmRemoveSubDepartment = new JMenuItem("Remove Sub Department");
		mntmRemoveSubDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new RemoveSubDepartment());
			}
		});
		mnRemove.add(mntmRemoveSubDepartment);

		mntmRemoveReport = new JMenuItem("Remove Report");
		mntmRemoveReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.changeWindow(new RemoveReport());
			}
		});
		mnRemove.add(mntmRemoveReport);

		mntmRemoveDisease = new JMenuItem("Remove Disease");
		mntmRemoveDisease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new RemoveDisease());
			}
		});
		mnRemove.add(mntmRemoveDisease);

		JMenu mnFuctions = new JMenu("Fuctions");
		menuBar.add(mnFuctions);

		JMenuItem mntmBadConditionPatients = new JMenuItem("Bad condition patients");
		mntmBadConditionPatients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new BadConditionPatients());
			}
		});
		mnFuctions.add(mntmBadConditionPatients);

		JMenuItem mntmHardestWorkingNurse = new JMenuItem("Hardest working nurse");
		mntmHardestWorkingNurse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new HardestNurse());
			}
		});
		mnFuctions.add(mntmHardestWorkingNurse);

		JMenuItem mntmCriticalSteroidsPatents = new JMenuItem("Critical steroids patents");
		mntmCriticalSteroidsPatents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new CriticalSteroidsPatients());
			}
		});
		mnFuctions.add(mntmCriticalSteroidsPatents);

		JMenuItem mntmBestStatusSubdep = new JMenuItem("Best status SubDep");
		mntmBestStatusSubdep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new BestStatusSubDep());
			}
		});
		mnFuctions.add(mntmBestStatusSubdep);

		JMenuItem mntmDoctorBySpec = new JMenuItem("Doctor by spec");
		mntmDoctorBySpec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new DoctorBySpec());
			}
		});
		mnFuctions.add(mntmDoctorBySpec);

		JMenuItem mntmDiseaseByRange = new JMenuItem("Disease by range");
		mntmDiseaseByRange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new DiseaseByRange());
			}
		});
		mnFuctions.add(mntmDiseaseByRange);

		JMenuItem mntmAllDifficultBreathing = new JMenuItem("All difficult breathing patients");
		mntmAllDifficultBreathing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new AllDifficultBreathing());
			}
		});
		mnFuctions.add(mntmAllDifficultBreathing);

		JMenuItem mntmTreatDisease = new JMenuItem("Treat disease");
		mntmTreatDisease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new TreatDisease());
			}
		});
		mnFuctions.add(mntmTreatDisease);

		JMenuItem mntmTreatPatients = new JMenuItem("Treat patients");
		mntmTreatPatients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new TreatPatients());
			}
		});
		mnFuctions.add(mntmTreatPatients);

		JMenu mnDetails = new JMenu("Details");
		menuBar.add(mnDetails);

		JMenuItem mntmShowDetails = new JMenuItem("Show Details");
		mntmShowDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.changeWindow(new DetailsWindow());
			}
		});
		mnDetails.add(mntmShowDetails);

		JMenuItem mntmHotelDetails = new JMenuItem("Hotel Details");
		mntmHotelDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.changeWindow(new PatientsOnHotel());
			}
		});
		mnDetails.add(mntmHotelDetails);

		JMenu mnCheck = new JMenu("Check");
		menuBar.add(mnCheck);

		JMenuItem mntmCheckPatient = new JMenuItem("Check Patient");
		mntmCheckPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.changeWindow(new CheckPatient());

			}
		});
		mnCheck.add(mntmCheckPatient);

		JMenuItem mntmCheckDisease = new JMenuItem("Check Disease");
		mntmCheckDisease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.changeWindow(new CheckDisease());
			}
		});
		mnCheck.add(mntmCheckDisease);
		JLabel profpic = new JLabel("New label");
		profpic.setForeground(Color.ORANGE);
		ImageIcon img = new ImageIcon(this.getClass().getResource("/HospitalLogo.png"));
		profpic.setIcon(img);
		profpic.setBounds(173, 104, 285, 248);
		add(profpic);	

		if(MainWindow.getKindofuser()==1) // get the option of doctor/nurse
		{
			nurseInstall();
		}
		else if(MainWindow.getKindofuser()==0)
		{
			doctorInstall();
		}
	}
	/**
	 * The function resize the image to the her size
	 * @param ImagePath the path of the image
	 * @return the icon to set on label
	 */
	public static ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(profpic.getWidth(), profpic.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	/**
	 * The function resize the image to the her size
	 * @param img the image that we want resize to the icon
	 * @return the icon to set on label
	 */
	public static ImageIcon ResizeImage(Image img)
	{
		Image newImg = img.getScaledInstance(profpic.getWidth(), profpic.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public static JLabel getProfPic() {
		return profpic;
	}
	/**
	 * The function initializes the screen according to the permissions of the nurse
	 */
	private void nurseInstall()
	{
		mntmRemoveDoctor.setEnabled(false);
		mntmAddDepartment.setEnabled(false);
		mntmAddSubDepartment.setEnabled(false);
		mntmAddDisease.setEnabled(false);
		mntmAddReport.setEnabled(false);
		mntmRemoveRecoverPatinet.setEnabled(false);
		mntmSendToHotel.setEnabled(false);
		mntmRemoveSubDepartment.setEnabled(false);
		mntmRemoveReport.setEnabled(false);
		mntmRemoveDisease.setEnabled(false);
		mntmNewMenuItem_2.setEnabled(false);
	}
	/**
	 * The function initializes the screen according to the permissions of the doctor
	 */
	private void doctorInstall()
	{
		mntmRemoveDoctor.setEnabled(false);
		mntmAddDepartment.setEnabled(false);
		mntmAddSubDepartment.setEnabled(false);
		mntmAddReport.setEnabled(false);;
		mntmRemoveRecoverPatinet.setEnabled(false);
		mntmRemoveSubDepartment.setEnabled(false);
		mntmRemoveReport.setEnabled(false);
		mntmNewMenuItem_2.setEnabled(false);
	}
	public static void serialize(Hospital h)
	{
		try {
		FileOutputStream file=new FileOutputStream("hospital.ser");
		ObjectOutputStream out=new ObjectOutputStream(file);
		out.writeObject(h);
		out.close();
		file.close();
		}
		catch ( IOException e)
		{
			e.printStackTrace();
		}
	}
	public void deserialize()
	{
		try {
		FileInputStream file=new FileInputStream("hospital.ser");
		ObjectInputStream in=new ObjectInputStream(file);
		Hospital.setTheHospital((Hospital)in.readObject());
		in.close();
		file.close();
		}
		catch ( IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * the function get combobox and review the details
	 * @param comboBox put on the combobox the detials
	 */
	static void addDepartments(JComboBox comboBox) {	
		comboBox.removeAllItems();
		comboBox.addItem(null);
		for(Department d : Hospital.getInstance().getDepartmentsById().values())
			comboBox.addItem(d);
	}
	static void addPatients(JComboBox comboBoxPatients) {	
		comboBoxPatients.removeAllItems();
		comboBoxPatients.addItem(null);
		for(Patient p : Hospital.getInstance().getPatientsById().values())
			comboBoxPatients.addItem(p);
	}
	static void addDoctors(JComboBox comboBoxDoctors) {	
		comboBoxDoctors.removeAllItems();
		comboBoxDoctors.addItem(null);
		for(Doctor d : Hospital.getInstance().getDoctorsById().values())
			comboBoxDoctors.addItem(d);
	}
	static void addDoctors(Patient p,JComboBox comboBoxDoctors) { // add list of patients to view 
		comboBoxDoctors.removeAllItems();
		comboBoxDoctors.addItem(null);
		for(Doctor d : Hospital.getInstance().getDoctorsByPatient().get(p))
			comboBoxDoctors.addItem(d);
	}
	static void addNurses(JComboBox comboBoxNurses) {	
		comboBoxNurses.removeAllItems();
		comboBoxNurses.addItem(null);
		for(Nurse n : Hospital.getInstance().getNursesById().values())
			comboBoxNurses.addItem(n);
	}
	static void addDisease(JComboBox comboBoxDisease) { // add diseases to the list 
		for(Disease d : Hospital.getInstance().getDiseasesById().values())
			comboBoxDisease.addItem(d);
	}
	/**
	 * the function check if the user exist on the system 
	 * @param user get the user 
	 * @return true if the user doesn't exist 
	 */
	static boolean userExist(String user)
	{
		for(String str : Hospital.getInstance().getDoctorUsers().keySet())
		{
			int i=str.indexOf(" ");
			if(str.subSequence(0, i).equals(user))
				return true;
		}
		for(String str : Hospital.getInstance().getNurseUsers().keySet())
		{
			int i=str.indexOf(" ");
			if(str.subSequence(0, i).equals(user))
				return true;
		}
		return false;
	}
}
