import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.Serializable;

// USER CLASS ONLY HAS VARIABLES/METHODS COMMON TO ADMIN AND STUDENT

// establish User parent class
public class User implements Serializable {
	protected String username;
	protected String password;
	protected String first;
	protected String last;
	
	protected List<Course> fullArray = new ArrayList<Course>();

	// default constructor
	public User() {
		this.username = "DefaultUser";
		this.password = "DefaultPassword";
		this.first = "FirstName";
		this.last = "LastName";
	}
	
	// METHOD OVERLOADING?
	// constructor
	public User(String first, String last, String user, String pass) {
		this.first = first;
		this.last = last;
		this.username = user;
		this.password = pass;
	}

	public void viewAllCourses(ArrayList<Course> registry) {
		for (Course c : registry) {
			printCourseInfo(c);
		}
		System.out.println();
	}
	
	// view all possible courses
	// gets overridden in Admin class b/c Admin has more outputs
	// student class uses this method
	public void printCourseInfo(Course c) {
		System.out.println("Course Name: " + c.getCourseName());
		System.out.println("Course ID: " + c.getCourseID());
		System.out.println("Section: " + c.getSection());
		System.out.println("Professor " + c.getProfName());
		System.out.println("Location: " + c.getLocat());
		
		System.out.println("------------------------------------");
	}
	
	public void displayMenu(ArrayList<Course> courses) {
		
	}
}
