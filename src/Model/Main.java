package Model;
import autopilot.AutoUtils;
import autopilot.OutputDocument;
import autopilot.Section;
import Model.*;
import Utils.CSVExporter;
import Utils.ReleaseNote;
import Utils.Specialization;
import Utils.Symptoms;
import Utils.Treatments;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;



public class Main {

	public static Hospital hospital = Hospital.getInstance();

	private static OutputDocument document = new OutputDocument();
	private static Map<String,Command> commands = new HashMap<>();
	private static Map<String,Section> sections = new HashMap<>();
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static final String PIPELINE = Pattern.quote("|");
	private static final String OUTPUT_FILE = "output.txt";
	private static final String AP_FILE = "ap_test_output.xml";

	static {
		//non binary sections
		Section departmentsSection = document.nextSection();
		Section doctorsSection = document.nextSection();
		Section nursesSection = document.nextSection();
		Section patientsSection = document.nextSection();
		Section diseasesSection = document.nextSection();
		Section patientReportsSection = document.nextSection();
		Section deletePatientsSection = document.nextSection();
		Section printAllPatients = document.nextSection();
		Section printAllDoctors = document.nextSection();
		Section printAllNurses = document.nextSection();
		Section removeRecoverPatient = document.nextSection();
		Section removeSubDepartment = document.nextSection();

		//binary section
		Section getDiseasesByRange = document.nextSection();
		Section getAllBadConditionPatients = document.nextSection();
		Section findHardestWorkingNurse = document.nextSection();
		Section getCriticalSteroidsNeuPatients = document.nextSection();
		Section getBestStatusSubDepartments = document.nextSection();
		Section getDoctorBySpec = document.nextSection();
		Section getAllDifficultBreathingPatients = document.nextSection();
		Section treatDiseases = document.nextSection();
		Section treatPatients = document.nextSection();

		sections.put("addDepartment",departmentsSection);
		sections.put("addDoctor",doctorsSection);
		sections.put("addNurse",nursesSection);
		sections.put("addPatient",patientsSection);
		sections.put("addDisease",diseasesSection);
		sections.put("addPatientReport",patientReportsSection);
		sections.put("removePatient",deletePatientsSection);
		sections.put("printAllPatients",printAllPatients);
		sections.put("printAllDoctors",printAllDoctors);
		sections.put("printAllNurses",printAllNurses);
		sections.put("getDiseasesByRange", getDiseasesByRange);
		sections.put("getAllBadConditionPatients", getAllBadConditionPatients);
		sections.put("findHardestWorkingNurse", findHardestWorkingNurse);
		sections.put("getCriticalSteroidsNeuPatients", getCriticalSteroidsNeuPatients);
		sections.put("getBestStatusSubDepartments", getBestStatusSubDepartments);
		sections.put("getDoctorBySpec", getDoctorBySpec);
		sections.put("getAllDifficultBreathingPatients", getAllDifficultBreathingPatients);
		sections.put("treatDiseases", treatDiseases);
		sections.put("treatPatients", treatPatients);
		sections.put("removeRecoverPatient", removeRecoverPatient);
		sections.put("removeSubDepartment", removeSubDepartment);


		//non binary args: (string name,string specialization, int numberOfDepartments)
		commands.put("addDepartment", (section, args) -> {
			Department d = new Department(args[0], Specialization.valueOf(args[1]));
			for(int i = 0; i<Integer.parseInt(args[2]);i++) {
				d.addSubDepartment(new SubDepartment(d));
			}
			boolean value = hospital.addDepartment(d);
			if(value){
				section.write("successfully added Department "+args[0]);
			}else{
				section.write("failed to add Department "+args[0]);
			}

		});

		//non binary args: (string firstName, string lastName, string specialization, int subID)
		commands.put("addDoctor", (section, args) -> {
			SubDepartment s = hospital.getRealSubDepartment(Integer.parseInt(args[3]));
			boolean value = hospital.addDoctor(new Doctor(args[0], args[1], Specialization.valueOf(args[2]), s)
					, s);
			if(value){
				section.write("successfully added Doctor "+args[0]+" "+args[1]);
			}else{
				section.write("failed to add Doctor "+args[0]+" "+args[1]);
			}
		});

		//non binary args: (string firstName, string lastName, string treatment, int subID)
		commands.put("addNurse", (section, args) -> {
			SubDepartment s = hospital.getRealSubDepartment(Integer.parseInt(args[3]));
			boolean value = hospital.addNurse(new Nurse(args[0], args[1],
					Treatments.valueOf(args[2]), s), s);
			if(value){
				section.write("successfully added Nurse "+args[0]+" "+args[1]);
			}else{
				section.write("failed to add Nurse "+args[0]+" "+args[1]);
			}
		});

		//non binary args: (string firstName, string lastName, int status, int subID)
		commands.put("addPatient", (section, args) -> {
			SubDepartment s = hospital.getRealSubDepartment(Integer.parseInt(args[3]));
			Patient p = new Patient(args[0], args[1],
					s, hospital.getRealDisease(Integer.parseInt(args[4])));
			String feedback = p.setStatus(Integer.parseInt(args[2]));
			if(!feedback.equals("Success"))
				section.write(feedback);
			boolean value = hospital.addPatient(p, s);
			if(value){
				section.write("successfully added Patient "+args[0]+" "+args[1]);
			}else{
				section.write("failed to add Patient "+args[0]+" "+args[1]);
			}
		});

		//non binary args: (string name, strings symptoms)
		commands.put("addDisease", (section, args) -> {
			ArrayList<String> symptoms = new ArrayList<>();
			if(!args[1].equals("_"))
				symptoms.add(args[1]);
			if(!args[2].equals("_"))
				symptoms.add(args[2]);
			if(!args[3].equals("_"))
				symptoms.add(args[3]);
			HashSet<Symptoms> syms = new HashSet<>();
			for(String s : symptoms) {
				syms.add(Symptoms.valueOf(s));
			}
			Disease d = new Disease(args[0]);
			String feedback = d.setSymptoms(syms);
			boolean value = false;
			if(!feedback.equals("Success"))
				section.write(feedback);
			else {
				value = hospital.addDisease(d);
			}
			if(value){
				section.write("successfully added Disease "+args[0]);
			}else{
				section.write("failed to add Disease "+args[0]);
			}
		});

		//non binary args: (int patientID, int docID, int DiseaseID, String note)
		commands.put("addPatientReport", (section, args) -> {
			Patient p = hospital.getRealPatient(Integer.parseInt(args[0]));
			Doctor d = hospital.getRealDoctor(Integer.parseInt(args[1]));
			Disease dis = hospital.getRealDisease(Integer.parseInt(args[2]));
			ReleaseNote note = ReleaseNote.valueOf(args[3]);
			Date now = new Date();
			PatientReport report = hospital.addPatientReport(p, d, now, dis, note);
			boolean value = (report != null? true : false);
			if(value){
				section.write("successfully added Patient Report of "+args[0]);
			}else{
				section.write("failed to add Patient Report of "+args[0]);
			}
		});

		//non binary args: (int patientID)
		commands.put("removePatient", (section, args) -> {
			boolean value = hospital.removePatient
					(hospital.getRealPatient(Integer.parseInt(args[0])));
			if(value){
				section.write("successfully removed Patient "+args[0]);
			}else{
				section.write("failed to remove Patient "+args[0]);
			}
		});


		commands.put("printAllPatients", (section, args) -> {
			for(Department d : hospital.getDepartmentsById().values()) {
				section.write(d.printAllPatients());
			}
		});

		commands.put("printAllDoctors", (section, args) -> {
			for(Department d : hospital.getDepartmentsById().values()) {
				section.write(d.printAllDoctors());
			}
		});

		commands.put("printAllNurses", (section, args) -> {
			for(Department d : hospital.getDepartmentsById().values()) {
				section.write(d.printAllNurses());
			}
		});

		commands.put("removeSubDepartment", (section, args) -> {
			Department d = hospital.getRealDepartment(Integer.parseInt(args[0]));
			SubDepartment moveTo = hospital.getRealSubDepartment(Integer.parseInt(args[1]));
			String feedback;
			SubDepartment toDelete = d.getSdepts().get(0);
			String toStringOfDeleted = d.getSdepts().get(0).toString();
			feedback = d.removeSubDepartment(toDelete, moveTo);
			if(!feedback.equals("Success"))
				section.write(feedback);
			else
				section.write("successfully removed SubDepartment "+toStringOfDeleted);
			
			toDelete = d.getSdepts().get(0);
			toStringOfDeleted = d.getSdepts().get(0).toString();
			feedback = d.removeSubDepartment(toDelete, moveTo);
			if(!feedback.equals("Success"))
				section.write(feedback);
			else
				section.write("successfully removed "+toStringOfDeleted);

	});

		commands.put("removeRecoverPatient", (section, args) -> {
			Patient toRelese = hospital.getRealPatient(Integer.parseInt(args[0]));
			Patient toHotel = hospital.getRealPatient(Integer.parseInt(args[1]));
			String feedback1 = hospital.removeRecoverPatient(toRelese);
			String feedback2 = hospital.removeToHotelPatient(toHotel);
			if(!feedback1.equals("Success") && !feedback1.equals("Fail"))
				section.write(feedback1);
			else
				section.write("successfully released recover Patient "+toRelese);
			if(!feedback2.equals("Success") && !feedback2.equals("Fail"))
				section.write(feedback2);
			else
				section.write("successfully released to hotel Patient "+toHotel);

		});


		commands.put("getDiseasesByRange", (section, args) -> {
			section.write("getDiseasesByRange results: ");
			TreeSet<Disease> dises = hospital.getDiseasesByRange(args[0].toCharArray()[0], args[1].toCharArray()[0]);
			for(Disease d: dises) {
				section.write(d);
			}
		});

		commands.put("getAllBadConditionPatients", (section, args) -> {
			Doctor d = hospital.getRealDoctor(Integer.parseInt(args[0]));
			for(Patient p : d.getsDepartment().getPatients()) {
				d.checkPatient(p);
			}
			section.write("getAllBadConditionPatients results: ");
			ArrayList<Patient> patients = hospital.getAllBadConditionPatients(d);
			for(Patient p : patients) {
				section.write(p.toStringLong());
			}
		});

		commands.put("findHardestWorkingNurse", (section, args) -> {
			section.write("findHardestWorkingNurse results: ");
			Nurse n = hospital.getRealNurse(Integer.parseInt(args[0]));
			for(Patient p : n.getsDepartment().getPatients()) {
				n.checkDisease(p);
			}
			section.write(hospital.findHardestWorkingNurse());
		});

		commands.put("getCriticalSteroidsNeuPatients", (section, args) -> {
			section.write("getCriticalSteroidsNeuPatients results: ");
			SubDepartment s = hospital.getRealSubDepartment(Integer.parseInt(args[0]));
			Nurse n = hospital.getRealNurse(Integer.parseInt(args[1]));
			Doctor d = hospital.getRealDoctor(Integer.parseInt(args[2]));
			for(Patient p : s.getPatients()) {
				n.checkPatient(p);
				d.checkPatient(p);
			}
			TreeSet<Patient> patients = hospital.getCriticalSteroidsNeuPatients();
			for(Patient p : patients) {
				section.write(p.toStringLong());
			}
		});


		commands.put("getBestStatusSubDepartments", (section, args) -> {
			section.write("getBestStatusSubDepartments results: ");
			TreeSet<SubDepartment> subs = hospital.getBestStatusSubDepartments();
			for(SubDepartment s : subs) {
				section.write(s+", "+"Number Of Patients is: "+s.getPatients().size());
			}
		});

		commands.put("getDoctorBySpec", (section, args) -> {
			section.write("getDoctorBySpec results: ");
			Doctor d1 = hospital.getRealDoctor(Integer.parseInt(args[1]));
			Doctor d2 = hospital.getRealDoctor(Integer.parseInt(args[2]));
			for(Patient p : d1.getsDepartment().getPatients()) {
				d1.checkPatient(p);
			}
			for(Patient p : d2.getsDepartment().getPatients()) {
				d2.checkPatient(p);
			}
			TreeSet<Doctor> doctors = hospital.getDoctorBySpec(Specialization.valueOf(args[0]));
			for(Doctor d : doctors) {
				section.write(d.toStringLong()+ ", Number Of Shifts is "+d.getShiftCounter());
			}
		});

		commands.put("getAllDifficultBreathingPatients", (section, args) -> {
			Department d = hospital.getRealDepartment(Integer.parseInt(args[0]));
			section.write("getAllDifficultBreathingPatients results: ");
			int start = Integer.parseInt(args[1]);
			int end = Integer.parseInt(args[2]);
			Nurse n = hospital.getRealNurse(Integer.parseInt(args[3]));
			LinkedList<Patient> pat = new LinkedList<>();
			for(int i = start; i <= end; i++)
				pat.add(hospital.getRealPatient(i));
			for(Patient p : pat) {
				n.checkPatient(p);
			}
			pat = hospital.getAllDifficultBreathingPatients(d);
			for(Patient p : pat) {
				section.write(p.toStringLong()+" , SubDepartment ID is: "+p.getsDepartment().getId());
			}
		});

		commands.put("treatDiseases", (section, args) -> {
			Department d = hospital.getRealDepartment(Integer.parseInt(args[0]));
			section.write("treatDiseases results: ");
			TreeSet<Patient> patients = hospital.treatDiseases(d);
			for(Patient p : patients) {
				section.write(p.toStringLong());
			}
		});

		commands.put("treatPatients", (section, args) -> {
			Department d = hospital.getRealDepartment(Integer.parseInt(args[0]));
			section.write("treatPatients results: ");
			TreeSet<Patient> patients = hospital.treatPatients(d);
			for(Patient p : patients) {
				section.write(p.toStringLong()+" ,ID is: "+p.getId());
			}
		});
}

public static void main(String[] args) throws IOException, ClassNotFoundException {


	try{
		ClassLoader c = Main.class.getClassLoader();
		InputStreamReader isr = new InputStreamReader(c.getResourceAsStream("input1.csv"));
		BufferedReader br = new BufferedReader(isr);
		List<String[]> input = CSVExporter.importCSV(br);
		for (int i = 1; i < input.size(); i++) {

			//get row
			String[] values = input.get(i);

			if(values.length == 0)
				continue;
			//get command
			String command = values[0];

			//get params
			String[] params = Arrays.copyOfRange(values,1,values.length);

			//send to func
			try {
				func(command, params);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}finally {
		// Shared s = Shared.getInstance();
		System.out.println("All commands executed. Please check \"" + OUTPUT_FILE + "\".");
	}

}

private static void func(String command,String[] args){
	//extract command
	Command c = commands.get(command);

	//check that command exists
	if (c != null){
		//get relevant section
		Section section = sections.get(command);

		//execute
		c.execute(section,args);
	}
}

@FunctionalInterface
private interface Command {
	void execute(Section section,String... args);
}


private static Date getDateFromString(String str){
	try {
		Date date = formatter.parse(str);
		return date;
	} catch (ParseException e) {
		e.printStackTrace();
		return new Date();
	}
}

}
