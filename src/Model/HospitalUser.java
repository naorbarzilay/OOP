package Model;

public abstract class HospitalUser {
	private int id;
	private String fname;
	private String lname;
	private SubDepartment sDepartment;
	private String imgPath; // picture of the user 
	
	public HospitalUser(int id,String fname, String lname, SubDepartment s) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		sDepartment = s;
	}
	public HospitalUser(int id) {
		super();
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public SubDepartment getsDepartment() {
		return sDepartment;
	}
	
	public void setsDepartment(SubDepartment sDepartment) {
		this.sDepartment = sDepartment;
	}
	public String toString()
	{
		return String.format("%s %s", getFname(),getLname());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HospitalUser other = (HospitalUser) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

}
