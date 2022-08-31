package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import Exceptions.CannotReleasePatientException;
import Utils.Condition;
import Utils.ReleaseNote;
import Utils.Specialization;
import Utils.Symptoms;
import Utils.Treatments;


public class Hospital implements Serializable {

	private static final long serialVersionUID = 1L;


	private static Hospital theHospital = null;

	private HashMap<Integer, Doctor> doctorsById;
	private HashMap<Integer, Nurse> nursesById;
	private HashMap<Integer, PatientReport> reportsById;
	private HashMap<Integer, Patient> patientsById;
	private HashMap<Integer, Patient> hotelPatientsById;
	private HashMap<Integer, Disease> diseasesById;
	private HashMap<Integer, Department> departmentsById;
	private HashMap<Integer, SubDepartment> subDepartmentById;
	private HashMap<Patient, HashSet<Doctor>> doctorsByPatient;
	private HashMap<Patient, HashSet<Nurse>> nursesByPatient;
	private HashMap<String,Nurse> nurseUsers; // map of nurse user to login
	private HashMap<String,Doctor> doctorUsers; // map of doctor user to login 

	public static Hospital getInstance()
	{
		if(theHospital==null)
			theHospital = new Hospital();
		return theHospital;

	}

	public static Hospital getTheHospital() {
		return theHospital;
	}

	public static void setTheHospital(Hospital theHospital) {
		Hospital.theHospital = theHospital;
	}

	public HashMap<String,Nurse> getNurseUsers() {
		return nurseUsers;
	}

	public HashMap<String,Doctor> getDoctorUsers() {
		return doctorUsers;
	}


	private Hospital()
	{
		patientsById = new HashMap<>();
		nursesById = new HashMap<>();
		reportsById = new HashMap<>();
		doctorsById = new HashMap<>();
		hotelPatientsById = new HashMap<>();
		diseasesById = new HashMap<>();
		departmentsById = new HashMap<>();
		subDepartmentById = new HashMap<>();
		doctorsByPatient = new HashMap<>();
		nursesByPatient = new HashMap<>();
		nurseUsers= new HashMap<>();
		doctorUsers= new HashMap<>();
	}

	public HashMap<Integer, Doctor> getDoctorsById() {
		return doctorsById;
	}

	public void setDoctorsById(HashMap<Integer, Doctor> doctorsById) {
		this.doctorsById = doctorsById;
	}

	public HashMap<Integer, Nurse> getNursesById() {
		return nursesById;
	}

	public void setNursesById(HashMap<Integer, Nurse> nursesById) {
		this.nursesById = nursesById;
	}

	public HashMap<Integer, PatientReport> getReportsById() {
		return reportsById;
	}

	public void setReportsById(HashMap<Integer, PatientReport> reportsById) {
		this.reportsById = reportsById;
	}

	public HashMap<Integer, Patient> getPatientsById() {
		return patientsById;
	}

	public void setPatientsById(HashMap<Integer, Patient> patientsById) {
		this.patientsById = patientsById;
	}

	public HashMap<Integer, Patient> getHotelPatientsById() {
		return hotelPatientsById;
	}

	public void setHotelPatientsById(HashMap<Integer, Patient> hotelPatientsById) {
		this.hotelPatientsById = hotelPatientsById;
	}

	public HashMap<Integer, Disease> getDiseasesById() {
		return diseasesById;
	}

	public void setDiseasesById(HashMap<Integer, Disease> diseasesById) {
		this.diseasesById = diseasesById;
	}

	public HashMap<Integer, Department> getDepartmentsById() {
		return departmentsById;
	}

	public void setDepartmentsById(HashMap<Integer, Department> departmentsById) {
		this.departmentsById = departmentsById;
	}

	public HashMap<Patient, HashSet<Doctor>> getDoctorsByPatient() {
		return doctorsByPatient;
	}

	public void setDoctorsByPatient(HashMap<Patient, HashSet<Doctor>> doctorsByPatient) {
		this.doctorsByPatient = doctorsByPatient;
	}

	public HashMap<Patient, HashSet<Nurse>> getNursesByPatient() {
		return nursesByPatient;
	}

	public void setNursesByPatient(HashMap<Patient, HashSet<Nurse>> nursesByPatient) {
		this.nursesByPatient = nursesByPatient;
	}

	public HashMap<Integer, SubDepartment> getSubDepartmentById() {
		return subDepartmentById;
	}

	public void setSubDepartmentById(HashMap<Integer, SubDepartment> subDepartmentById) {
		this.subDepartmentById = subDepartmentById;
	}

	public boolean addDoctor(Doctor doc, SubDepartment s) {
		if(doc==null || s == null)
			return false;
		if(!getDoctorsById().containsValue(doc))
			getDoctorsById().put(doc.getId(), doc);
		else
			return false;
		s.addDoctor(doc);
		return true;
	}

	public boolean addNurse(Nurse nurse, SubDepartment s) {
		if(nurse== null || s== null)
			return false;
		if (!getNursesById().containsValue(nurse)) {
			getNursesById().put(nurse.getId(), nurse);
		}
		else
			return false;
		s.addNurse(nurse);
		return true;
	}

	public boolean addPatient(Patient patient, SubDepartment s) {
		if(patient == null || s== null)
			return false;
		if(!getPatientsById().containsValue(patient))
			getPatientsById().put(patient.getId(), patient);
		else
			return false;
		s.addPatient(patient);
		return true;
	}

	public boolean addDepartment(Department d) {
		if(d == null)
			return false;
		if(!getDepartmentsById().containsValue(d))
			getDepartmentsById().put(d.getId(), d);
		else
			return false;
		return true;
	}


	public boolean removeDepartment(Department d) {
		return getDepartmentsById().remove(d.getId(),d);
	}

	public boolean addSubDepartment(Department d, SubDepartment s) {
		return (getSubDepartmentById().put(s.getId(), s) == null ? true : false);
	}

	public boolean removeSubDepartment(SubDepartment s) {
		s.getDepartment().getSdepts().remove(s);
		return getSubDepartmentById().remove(s.getId(), s);
	}


	public boolean removeDoctor(Doctor doc)
	{
		if(doc == null)
			return false;
		doc.getsDepartment().removeDoctor(doc);
		getDoctorsById().remove(doc.getId(),doc);
		for(String s : this.doctorUsers.keySet())
		{
			if(this.doctorUsers.get(s).equals(doc))
			{
				this.doctorUsers.remove(s);
				return true;
			}
		}
		return true;
	}

	public boolean removeNurse(Nurse nurse)
	{
		if(nurse==null)
			return false;
		nurse.getsDepartment().removeNurse(nurse);
		getNursesById().remove(nurse.getId(), nurse);
		for(String s : this.nurseUsers.keySet())
		{
			if(this.nurseUsers.get(s).equals(nurse))
			{
				this.nurseUsers.remove(s);
				return true;
			}
		}
		return true;

	}

	public boolean removePatient(Patient patient)
	{
		if(patient==null)
			return false;
		patient.getsDepartment().removePatient(patient);
		getPatientsById().remove(patient.getId(), patient);
		getDoctorsByPatient().remove(patient);
		getNursesByPatient().remove(patient);
		return true;

	}

	public String removeRecoverPatient(Patient patient) {
		try {
			if(patient.checkCondition().equals(ReleaseNote.CAN_GO_HOME)) {
				boolean res = removePatient(patient);
				if(res)
					return "Success";
				else
					return "Fail";
			}
			else
				throw new CannotReleasePatientException(patient.checkCondition(), ReleaseNote.CAN_GO_HOME);
		}
		catch (CannotReleasePatientException e) {
			return e.toString();
		}
	}

	public String removeToHotelPatient(Patient patient) {
		try {

			if(patient.checkCondition().equals(ReleaseNote.MOVE_TO_HOTEL)) {
				boolean res  = removePatient(patient);
				if(res) {
					getHotelPatientsById().put(patient.getId(), patient);
					return "Success";
				}
				else
					return "Fail";
			}
			else
				throw new CannotReleasePatientException(patient.checkCondition(), ReleaseNote.MOVE_TO_HOTEL);
		}
		catch (CannotReleasePatientException e) {
			return e.toString();
		}
	}



	public boolean addDisease(Disease disease) {
		if(disease == null)
			return false;
		if(!getDiseasesById().containsValue(disease)) {
			getDiseasesById().put(disease.getId(), disease);
			return true;
		}
		return false;
	}

	public boolean removeDisease(Disease disease) {
		if(disease==null)
			return false;
		getDiseasesById().remove(disease.getId(),disease);
		return true;
	}


	public PatientReport addPatientReport(Patient pat, Doctor doc, Date date, Disease des, ReleaseNote note)
	{

		if (doc==null)
			return null;
		if (pat == null)
			return null;
		if (des==null)
			return null;
		if (note == null)
			return null;
		PatientReport pr = new PatientReport(pat,doc,date,des,pat.getsDepartment(), note);
		getReportsById().put(pr.getId(), pr);
		pat.getsDepartment().addPatientReport(pr);
		return pr;

	}

	public boolean removePatientReport(PatientReport pr) {
		if(pr==null)
			return false;
		getReportsById().remove(pr.getId(),pr);
		pr.getSdept().removePatientReport(pr);
		return true;
	}



	public boolean printAllDoctors(Department dep) {
		if(dep == null)
			return false;
		dep.printAllDoctors();
		return true;

	}
	public boolean printAllNurses(Department d) {
		if(d == null)
			return false;

		d.printAllNurses();
		return true;

	}
	public boolean printAllPatients(Department dep) {
		if(dep == null)
			return false;
		dep.printAllPatients();
		return true;
	}


	public Patient getRealPatient(int pid) {
		return getPatientsById().get(pid);
	}

	public Doctor getRealDoctor(int did) {
		return getDoctorsById().get(did);
	}

	public Nurse getRealNurse(int nid) {
		return getNursesById().get(nid);
	}

	public Department getRealDepartment(int did) {
		return getDepartmentsById().get(did);
	}


	public Disease getRealDisease(int did) {
		return getDiseasesById().get(did);
	}

	public SubDepartment getRealSubDepartment(int sid)
	{
		return getSubDepartmentById().get(sid);
	}

	public PatientReport getRealPatientReport(int prid) {
		return getReportsById().get(prid);
	}

	public ArrayList<Patient> getAllBadConditionPatients(Doctor d){
		ArrayList<Patient> patients = new ArrayList<>();
		for(Patient p : getPatientsById().values()) {
			if(getDoctorsByPatient().containsKey(p))
				if(getDoctorsByPatient().get(p).contains(d) &&
						(p.getCondition().equals(Condition.CRITICAL)
								|| p.getCondition().equals(Condition.SERIOUS)))
					patients.add(p);
		}
		patients.sort(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				Integer o1Status = o1.getStatus();
				Integer o2Status = o2.getStatus();
				return o2Status.compareTo(o1Status);
			}
		});
		return patients;
	}

	public Nurse findHardestWorkingNurse() {
		HashMap<Nurse, Integer> nursesPatients = new HashMap<>();
		for(Patient p : getPatientsById().values()) {
			if(getNursesByPatient().containsKey(p))
				for(Nurse n : getNursesByPatient().get(p)) {
					if(nursesPatients.containsKey(n))
						nursesPatients.put(n, nursesPatients.get(n)+1);
					else
						nursesPatients.put(n, 1);
				}
		}
		Nurse hardWorkingNurse = null;
		int maxNumber = 0;
		for(Nurse n : nursesPatients.keySet()) {
			if(hardWorkingNurse == null)
			{
				hardWorkingNurse = n;
				maxNumber = nursesPatients.get(n);
			}
			else if(maxNumber < nursesPatients.get(n)) {
				hardWorkingNurse = n;
				maxNumber = nursesPatients.get(n);
			}
		}
		return hardWorkingNurse;
	}

	public TreeSet<Patient> getCriticalSteroidsNeuPatients(){
		TreeSet<Patient> patients = new TreeSet<>(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				if(!o1.getLname().equals(o2.getLname()))
					return o1.getLname().compareTo(o2.getLname());
				else
					return o1.getFname().compareTo(o2.getFname());
			}
		});
		for(Patient p : getPatientsById().values()) {
			if(!p.getCondition().equals(Condition.CRITICAL))
				continue;
			boolean hasNurse = false;
			boolean hasDoctor = false;
			if(getNursesByPatient().containsKey(p))
				for(Nurse n : getNursesByPatient().get(p)) {
					if(n.getTreat().equals(Treatments.STEROIDS)) {
						hasNurse = true;
						break;
					}
				}
			if(hasNurse && getDoctorsByPatient().containsKey(p))
				for(Doctor d : getDoctorsByPatient().get(p)) {
					if(d.getSpec().equals(Specialization.NEUROLOGY)) {
						hasDoctor = true;
						break;
					}
				}
			if(hasNurse && hasDoctor)
				patients.add(p);
		}
		return patients;
	}

	public TreeSet<SubDepartment> getBestStatusSubDepartments(){
		TreeSet<SubDepartment> subs = new TreeSet<>(new Comparator<SubDepartment>() {

			@Override
			public int compare(SubDepartment o1, SubDepartment o2) {
				if((o1!=null)&&(o2!=null)){
				Integer o1Size = o1.getPatients().size();
				Integer o2Size = o2.getPatients().size();
				return o2Size.compareTo(o1Size);
				}
				return 0;
			}
		});
		HashMap<SubDepartment, Integer> subCountGoodPatients = new HashMap<>();
		for(SubDepartment s : getSubDepartmentById().values()) {
			int counter = 0;
			for(Patient p : s.getPatients()) {
				if(p.getCondition().equals(Condition.GOOD))
					counter++;
			}
			subCountGoodPatients.put(s, counter);
		}
		int max = 0;
		int secondMax = 0;
		SubDepartment bestSub = null;
		SubDepartment secondBestSub = null;
		for(SubDepartment s : subCountGoodPatients.keySet()) {
			if(subCountGoodPatients.get(s) > max) {
				secondBestSub = bestSub;
				secondMax = max;
				max = subCountGoodPatients.get(s);
				bestSub = s;
				continue;
			}
			else if(subCountGoodPatients.get(s) > secondMax) {
				secondBestSub = s;
				secondMax = subCountGoodPatients.get(s);
				continue;
			}
		}
		subs.add(bestSub);
		subs.add(secondBestSub);
		return subs;
	}

	public TreeSet<Doctor> getDoctorBySpec(Specialization s){
		TreeSet<Doctor> doctors = new TreeSet<>(new Comparator<Doctor>() {

			@Override
			public int compare(Doctor o1, Doctor o2) {
				Integer o1Count = o1.getShiftCounter();
				Integer o2Count = o2.getShiftCounter();
				return o2Count.compareTo(o1Count);
			}
		});
		for(PatientReport pr : getReportsById().values()) {
			if(pr.getDoctor().getSpec().equals(s)) {
				doctors.add(pr.getDoctor());
			}
		}
		return doctors;
	}

	public TreeSet<Disease> getDiseasesByRange(char start, char end){
		TreeSet<Disease> diseases = new TreeSet<>();
		for(Disease d : getDiseasesById().values()) {
			char[] dName = d.getName().toLowerCase().toCharArray();
			if(dName[0] >= start && dName[0] <= end)
				diseases.add(d);
		}
		return diseases;
	}

	public LinkedList<Patient> getAllDifficultBreathingPatients(Department d){
		LinkedList<Patient> patients = new LinkedList<>();
		for(SubDepartment s : d.getSdepts()) {
			for(Patient p : s.getPatients()) {
				if(p.getDis().getSymptoms().contains(Symptoms.DIFFICULTY_BREATHING) 
						&& getNursesByPatient().containsKey(p))
					for(Nurse n : getNursesByPatient().get(p)) {
						if(n.getTreat().equals(Treatments.BREATHING_SUPPORT)) {
							patients.add(p);
							break;
						}
					}
			}
		}
		patients.sort(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				if(o1.getsDepartment().getId() != o2.getsDepartment().getId()) {
					Integer o1SubID = o1.getsDepartment().getId();
					Integer o2SubID = o2.getsDepartment().getId();
					return o2SubID.compareTo(o1SubID);
				}
				else
					return o2.getLname().compareTo(o1.getLname());
			}
		});
		return patients;
	}

	public TreeSet<Patient> treatDiseases(Department d){
		TreeSet<Patient> viralPatients = new TreeSet<>();
		TreeSet<Patient> chronicPatients = new TreeSet<>();
		for(SubDepartment s : d.getSdepts()) {
			Iterator<Doctor> doctorIter = s.getDoctors().iterator();
			for(Patient p : s.getPatients()) {
				if(!doctorIter.hasNext())
					doctorIter = s.getDoctors().iterator();
				doctorIter.next().checkDisease(p);
			}
		}
		for(SubDepartment s : d.getSdepts()) {
			for(Patient p : s.getPatients()) {
				if(p.getDis() instanceof ChronicDisease) {
					chronicPatients.add(p);
				}
				else if(p.getDis() instanceof ViralDisease) {
					viralPatients.add(p);
				}
			}
		}
		return (viralPatients.size() > chronicPatients.size() ? viralPatients : chronicPatients);
	}

	public TreeSet<Patient> treatPatients(Department d){
		TreeSet<Patient> patients = new TreeSet<>(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				Integer o1ID = o1.getId();
				Integer o2ID = o2.getId();
				return o1ID.compareTo(o2ID);
			}

		});
		for(SubDepartment s : d.getSdepts()) {
			Iterator<Nurse> nurseIter = s.getNurses().iterator();
			for(Patient p : s.getPatients()) {
				Condition oldCondition = p.getCondition();
				if(!nurseIter.hasNext()) 
					nurseIter = s.getNurses().iterator();
				nurseIter.next().checkPatient(p);
				if(!p.getCondition().equals(oldCondition))
					patients.add(p);
			}
		}
		return patients;
	}
}
