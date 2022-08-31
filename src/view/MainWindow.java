package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Model.Department;
import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Model.HospitalUser;
import Model.Main;
import Model.Nurse;
import Model.Patient;
import Model.PatientReport;
import Model.SubDepartment;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private static MainWindow theFrame = null;
	private static int kindofuser = -1; // get the kind of user : 2-admin , 1-nurse , 0 - doctor
	private HospitalUser user=null; // the Hospital object

	/**
	 * Launch the application.
	 */
	public static MainWindow getInstance()
	{
		if(theFrame==null)
			theFrame = new MainWindow();
		return theFrame;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = MainWindow.getInstance();
					frame.setVisible(true);
					Model.Main.main(args); // run the last hospital targil
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initComponents();
	}
	 private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane = new LoginWindow();
		setContentPane(contentPane);
	}	
	 /**
	  * The function help me to change the panel everytime in needed
	  * @param panel get the panel that i want to change
	  */
	 public static void changeWindow(Container panel)
	 {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension d = toolkit.getScreenSize();
		int height = d.height;
		int width = d.width;
		MainWindow.getInstance().setSize(width, height);
		MainWindow.getInstance().setPreferredSize(d);
		MainWindow.getInstance().setExtendedState(JFrame.MAXIMIZED_BOTH);
		MainWindow.getInstance().getContentPane().setVisible(false);
		MainWindow.getInstance().setContentPane(panel);
	 }
	 
	 public HospitalUser getUser() {
			return user;
		}

		public void setUser(HospitalUser user) {
			this.user = user;
		}

		public static int getKindofuser() {
			return kindofuser;
		}
		public static void setKindofuser(int kindofuser) {
			MainWindow.kindofuser = kindofuser;
		}
}

