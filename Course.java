import java.io.Serializable;
import java.util.*;
public class Course implements Comparable<Course>, Serializable {
	  
	private static final long serialVersionUID = 8015302461025214737L;
	
	// member variables
	private String courseName;
	private String courseID;
	private int maxStudents;
	private int currentStud;
	private String[] studsRegistered;
	// setting section as a string for ease in edit method
	private String section;
	private String profName;
	private String locat;
	
	// default values
	public Course() {
		this.courseName = "defaultCourse";
		this.courseID = "defaultID";
		this.maxStudents = 10;
		this.currentStud = 0;
		this.studsRegistered = new String[maxStudents];
		this.section = "defaultSection";
		this.profName = "defaultProfessor";
		this.locat = "defaultLocation";	
	}
	
	public Course(String course, String id, int max, int current, String[] students, String prof, String sec, String loc)  {
		this.courseName = course;
		this.courseID = id;
		this.maxStudents = max;
		this.currentStud = current;
		this.studsRegistered = students;
		this.section = sec;
		this.profName = prof;
		this.locat = loc;
		
		for (int i = 0; i < studsRegistered.length; i++) {
			studsRegistered[i] = "";
		}
	}
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}

	public int getCurrentStud() {
		return currentStud;
	}

	public void setCurrentStud(int currentStud) {
		this.currentStud = currentStud;
	}

	public String[] getStudsRegistered() {
		return studsRegistered;
	}

	public void setStudsRegistered(String[] studsRegistered) {
		this.studsRegistered = studsRegistered;
	}

	public String getProfName() {
		return profName;
	}

	public void setProfName(String profName) {
		this.profName = profName;
	}

	public String getLocat() {
		return locat;
	}

	public void setLocat(String locat) {
		this.locat = locat;
	}
	
	public boolean isFull() {
		if (this.currentStud == this.maxStudents)
			return true;
		return false;
	}
	
	// add student to the studsRegistered array
	public void addStudent(String first, String last) {
		if (this.isFull() == false) {
			for (int i = 0; i < this.maxStudents; i++) {
				if (this.studsRegistered[i].equals("")) {
					this.studsRegistered[i] = (first + " " + last);
					this.setCurrentStud(this.getCurrentStud() + 1);
					break; 
				}
			}
		}	
	}
	
	// remove student from studsRegistered array
	public void removeStudent(String first, String last) {
		for (int i = 0; i < this.maxStudents; i++) {
			if (this.studsRegistered[i].equals(first + " " + last))
				studsRegistered[i] = "";
				this.setCurrentStud(this.getCurrentStud() - 1);
				break;
		}
	}

	// implement compareTo method from Comparable
	@Override
	public int compareTo(Course c){ 
		return ((Course)c).getCurrentStud() - this.getCurrentStud(); 
	}
	
}