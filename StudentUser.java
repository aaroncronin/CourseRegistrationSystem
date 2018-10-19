import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;


// NEED ADMIN TO REGISTER A STUDENT BEFORE THE STUDENT CAN LOG IN
// HAVE MESSAGE THAT SAYS NO STUDENT IS REGISTERED 

// THIS CLASS SHOULD HAVE A LIST/ARRAY OF COURSES THEY ARE REGISTERED IN 

// DONT FORGET TO CLOSE INPUTS


// STUDENT IS ONLY ALLOWED TO REGISTER IN 4 COURSES

public class StudentUser extends User implements Student, Serializable  {
	
	// keeps track of how many courses the student is enrolled in
	// student can only enroll in maximum 4 courses
	int numEnrolled = 0;
	public Course[] studentCourses = new Course[4];
	
	public StudentUser() {
		super();
		for (int i = 0; i < 4; i++) {
			studentCourses[i] = new Course();
		}
	}
	
	public StudentUser(String first, String last, String user, String pass) {
		super(first, last, user, pass);
		for (int i = 0; i < 4; i++) {
			studentCourses[i] = new Course();
		}
	}

	@Override
	public void viewAllCourses(ArrayList<Course> registry) {
		super.viewAllCourses(registry);
	}
	
	// tells student which courses are open
	// prints out Course Name, Section Number, and Professor Name
	public void viewOpenCourses(ArrayList<Course> registry) {
		System.out.println("\nThese are all the available courses in the registry: ");
		for (Course course : registry) {
			if (course.isFull() == false)
				printCourseInfo(course);
		}
	}
	
	public void registerIn(ArrayList<Course> registry) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the name of the course you would like to register in: ");
		String courseName = input.nextLine();
		System.out.println("Enter the section of the course you would like to register in: ");
		String section = input.nextLine();
		for (Course course : registry) {
			if (course.getCourseName().equals(courseName) && course.getSection().equals(section)) {
				if (course.isFull())
					System.out.println("You cannot enroll in this course. The course is full!");
				else if (numEnrolled == studentCourses.length)
					System.out.println("You cannot enroll in this course. You are already enrolled in 4 courses.");
				else {
					for (int i = 0; i < studentCourses.length; i++) {
						if (studentCourses[i].getCourseName().equals("defaultCourse")) {
							studentCourses[i] = course;
							course.addStudent(first, last); 
							this.numEnrolled++;
							System.out.println("Succesfully Enrolled in " + courseName + "! You are enrolled in " + numEnrolled + "/4 possible courses." );
							break;
					
							// input.close();
						}
					}
					
				}
			}
		}
		//input.close();
	}
	
	// Student will not be allowed to register for two different sections of the same course
	// so, only have to ask for course name and they will be removed
	public int withdrawFrom(ArrayList<Course> registry) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the course name you would like to withdraw from: ");
		String courseName = input.nextLine();
		for (Course c : registry) {

			for (int i = 0; i < studentCourses.length; i++) {
				if (c.getCourseName().equals(courseName)) {
					if (studentCourses[i].getCourseName().equals(courseName)) {
						studentCourses[i] = new Course();
						c.removeStudent(this.first, this.last);
						this.numEnrolled--;
						// IN DISPLAY MENU DO VALIDATION IF RETURN 1 PRINT SUCCESSFULLY WITHDRAWN
						//input.close();
						return 1;
					}
				}
			}
		}
		//input.close();
		return 0;
	}
	
	public void viewRegisteredIn() {
		System.out.println("\nThese are the courses you are currently registered in. You may enroll in a total of 4 courses.");
		for (int i = 0; i < studentCourses.length; i++) {
			if (studentCourses[i].getCourseName().equals("defaultCourse")) {
				System.out.println("- Course TBD");
			}
			else
				System.out.println("- " + studentCourses[i].getCourseName() + " (Section " + studentCourses[i].getSection() + ") with Professor " + studentCourses[i].getProfName());
		}
	}
	
	// overridden from User class
	@Override
	public void displayMenu(ArrayList<Course> courses, ArrayList<StudentUser> students) {
		Scanner input = new Scanner(System.in);
		int choice;
		
		do {
			System.out.println("\n~STUDENT COURSE MANAGEMENT MENU~");
			System.out.println("----------------------------------");
			System.out.println("Enter the number of the task you would like to perform:");
			
			System.out.println("1. View All Courses");
			System.out.println("2. View All Open Courses");
			System.out.println("3. Register for a Course");
			System.out.println("4. Withdraw from a Course");
			System.out.println("5. View My Registered Courses");
			System.out.println("6. Exit");
			choice = input.nextInt();
			input.nextLine();
			switch (choice) {
			case 1:
				viewAllCourses(courses);
				break;
			case 2:
				viewOpenCourses(courses);
				break;
			case 3:
				registerIn(courses);
				break;
			case 4:
				// withdrawFrom();
				if (withdrawFrom(courses) == 1)
					System.out.println("You have successfully withdrawn from the course.");
				else
					System.out.println("The system failed to withdraw you from the course.");
				break;
			case 5:
				viewRegisteredIn();
				break;
				
			}
		} while (choice != 6);
			System.out.println("Exiting the program.");
			try {
				FileOutputStream fileCoursesIn = new FileOutputStream("courses.ser");
				FileOutputStream fileStudentsIn = new FileOutputStream("students.ser");
				ObjectOutputStream coursesIn = new ObjectOutputStream(fileCoursesIn);
				ObjectOutputStream studentsIn = new ObjectOutputStream(fileStudentsIn);
				coursesIn.writeObject(courses);
				studentsIn.writeObject(students);
				coursesIn.close();
				fileCoursesIn.close();
				studentsIn.close();
				fileStudentsIn.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException f) {
				f.printStackTrace();
			} 
			
		input.close();
	}

}

interface Student {
	public void viewAllCourses(ArrayList<Course> registry);
	public void viewOpenCourses(ArrayList<Course> registry);
	public void registerIn(ArrayList<Course> registry);
	public int withdrawFrom(ArrayList<Course> registry);
	public void viewRegisteredIn();
	public void displayMenu(ArrayList<Course> registry, ArrayList<StudentUser> students);
}
