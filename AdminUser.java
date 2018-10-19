import java.io.*;
import java.util.*;


class AdminUser extends User implements Admin, Serializable {
	// HAVE TWO SEPARATE MENUS FOR MANAGEMENT AND REPORTS
	
	// default constructor with default names
	public AdminUser() {
		username = "Admin";
		password = "Admin001";
	}
	
	// constructor
	public AdminUser(String user, String pass) {
		username = user;
		password = pass;
	}
	
	// create
	public int createCourse(ArrayList<Course> registry) {
		// ask user for all inputs
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter Course Name: ");
		String courseName = input.nextLine();
		System.out.println("Enter Course ID: ");
		String id = input.nextLine();
		System.out.println("Enter Max # of Students for the Course: ");
		int max = input.nextInt();
		input.nextLine();
		
		// current # of students when course created is 0
		// create an array of students to be registered of size maximum # students
		int current = 0;
		String[] students = new String[max];
		
		// section is a string
		System.out.println("Enter Section #: ");
		String sect = input.nextLine();
		System.out.println("Enter Professor Name: ");
		String prof = input.nextLine();
		System.out.println("Enter Course Location:");
		String loc = input.nextLine();
		
		// add course to arraylist
		registry.add(new Course(courseName, id, max, current, students, prof, sect, loc));
		
		//input.close();
		return 1;
	}
	
	// user will get asked which class and section they want to delete and answers will link back to this method
	// return 1 if succesful, will return 0 if not found for validation
	public int deleteCourse(ArrayList<Course> registry) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the course name you would like to delete: ");
		String name = input.nextLine();
		System.out.println("Enter the section of " + name + " you would like to delete: ");
		String section = input.nextLine();
		for (Course c : registry) {
			if (c.getCourseName().equals(name) && c.getSection().equals(section)) {
				registry.remove(c);
				return 1;
			}
		}
		//input.close();
		return 0;
	}
	
	// user will get asked which class and section they want to edit and answers will link back to this method
	public int editCourse(ArrayList<Course> registry) {
		Scanner input = new Scanner(System.in);
		String replaceProf;
		String replaceLoc;
		System.out.println("Enter the name of the course you would like to edit: ");
		String name = input.nextLine();
		System.out.println("Enter the section of " + name + " you would like to edit: ");
		String section = input.nextLine();
		for (int i = 0; i < registry.size(); i++) {
			if (name.equals(registry.get(i).getCourseName()) && section.equals(registry.get(i).getSection())) {
					
				// do while loop while user is editing possible attributes
				// call the chooseEdit method that lets user choose which attribute they want to change
				do {
					switch (chooseEdit()) {
					case 1:
						System.out.println("Enter New Professor: ");
						replaceProf = input.nextLine();
						registry.get(i).setProfName(replaceProf);
						System.out.println("The Professor for " + registry.get(i).getCourseName() + " Section # " + registry.get(i).getSection() + " is: " + registry.get(i).getProfName());
						break;
					case 2:
						System.out.println("Enter New Location: ");
						replaceLoc = input.nextLine();
						registry.get(i).setLocat(replaceLoc);
						System.out.println("The Location for " + registry.get(i).getCourseName() + " Section # " + registry.get(i).getSection() + " is: " + registry.get(i).getLocat());
						break;						}
					} while (chooseEdit() != 3);
					//input.close();
					return 1;
			}
		}
		//input.close();
		return 0;
	}
		
	public int chooseEdit() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the number of the attribute you would like to edit: ");
		System.out.println("1. Professor Name");
		System.out.println("2. Course Location");
		System.out.println("3. Stop Editing");
		int choice = input.nextInt();
		input.nextLine();
		//input.close();

		return choice;
	}
	
	// overrides viewAllCourses in User class
	// has different output than StudentUser class
	// CHECK HOW TO CREATE TABLE IN ECLIPSE
	// NEEDS TO PRINT ALL RELEVANT INFO
	// FIX THIS METHOD
	@Override
	public void viewAllCourses(ArrayList<Course> registry) {
		for (Course c : registry) {
			printCourseInfo(c);
		}
		System.out.println();
	}
	
	// admin can view all attributes of course
	public void viewFullCourses(ArrayList<Course> registry) {
		// keeps track of how many full courses for validation
		int count = 0;
		for (Course c : registry) {
			if (c.isFull() == true) {
				printCourseInfo(c);
				count++;
			}
		}
		if (count == 0) {
			System.out.println("There are no full courses.");
		}
	}
	
	public void writeFullCourses(ArrayList<Course> registry) {
		ArrayList<Course> fullArray = new ArrayList<Course>();
		try {
			File file = new File("fullCourses.txt");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Course c : registry) {
				if (c.isFull()) {
					fullArray.add(c);
				}
			}
			for (int i = 0; i < fullArray.size(); i++) {
				bw.write(fullArray.get(i).getCourseName());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nThe full courses have been written to a file called 'fullCourses.txt'");
	}

	// asks user for student info and adds student to student arraylist
	public int registerNewStud(ArrayList<StudentUser> students) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter First Name for the New Student: ");
		String first = input.nextLine();
		System.out.println("Enter Last Name for the New Student: ");
		String last = input.nextLine();
		System.out.println("Enter Username for the New Student: ");
		String user = input.nextLine();
		System.out.println("Enter Password for the New Student: ");
		String pass = input.nextLine();
		
		StudentUser stud = new StudentUser(first, last, user, pass);
		//for (StudentUser student : students) {
		//	if (student.equals(stud)) {
				//input.close();
		//		return 0;
		//	}
		//}
		students.add(stud);
		
		//input.close();
		
		return 1;
	}
	
	// will take name of course and section number and print all the students registered for the course from array
	public void viewStudsInCourse(ArrayList<Course> registry) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the name of the course you would like to view the registered students: ");
		String name = input.nextLine();
		System.out.println("Enter the section of " + name + " you would like to view the registered students: ");
		String section = input.nextLine();
		
		for (int i = 0; i < registry.size(); i++) {
			if (name.equals(registry.get(i).getCourseName()) && section.equals(registry.get(i).getSection())) {
				System.out.println("\nHere are the students registered for " + name + " (Section " + section + "):\n");
				for (String student : registry.get(i).getStudsRegistered()) {
					if (student.equals("")) {
						System.out.println("- Student TBD");
					}
					else
						System.out.println("- " + student);
				}
			}
		}
		
		//input.close();
	}
	
	// allows admin to enter student's name and view the courses they are registered for
	public void viewCoursesForStud(ArrayList<StudentUser> students) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the first name of the student: ");
		String first = input.nextLine();
		System.out.println("Enter the last name of the student: ");
		String last = input.nextLine();
		
		for (StudentUser student : students) {
			if (student.first.equals(first) && student.last.equals(last)) {
				System.out.println("\nThese are the courses " + first + " " + last + " is registered for: \n");
				for (int i = 0; i < student.studentCourses.length; i++) {
					if (student.studentCourses[i].getCourseName().equals("defaultCourse")) {
						System.out.println("- Course TBD");
					}
					else
						System.out.println("- " + student.studentCourses[i].getCourseName() + " (Section " + student.studentCourses[i].getSection() + ")");
				}
			}
			// if student is not found, ask if the admin wants to register a new student
			// if yes, call the registerNewStud() method
			// they will have to re-enter first and last name in the registerNewStud() method
			// ideally, they would not have to re-enter the name, but registerNewStud() method does not take parameters
			/*
			else {
				System.out.println("Student not found. Would you like to register a new student? Enter 'yes' or 'no'");
				String answer = input.next();
				if (answer.equals("yes")) {
					registerNewStud(students);
				}
			}
			*/
		}
		// input.close();
	}
	
	// sorts the registry by # of current students registered per course
	public void sortByStudents(ArrayList<Course> courses) {
		//sort list
		Collections.sort(courses);
		//print out list
		for (Course course : courses) {
			printCourseInfo(course);
			System.out.println();
		}
	}
	
	public void printCourseInfo(Course c) {
		System.out.println("Course Name: " + c.getCourseName());
		System.out.println("Course ID: " + c.getCourseID());
		System.out.println("Section: " + c.getSection());
		System.out.println("Professor: " + c.getProfName());
		System.out.println("Location: " + c.getLocat());
		System.out.println("\n" + c.getCourseName() + " has " + c.getCurrentStud() + "/" + c.getMaxStudents() + " spots filled.");
		System.out.println("\nStudents: ");
		for (String s : c.getStudsRegistered()) {
			
			if (s.equals("")) {
				System.out.println("- Student TBD");
			}
			else
				System.out.println("- " + s);
		}
		System.out.println("------------------------------------");
		
	}
	
	public void searchByID(String id, ArrayList<Course> registry) {
		for (Course course : registry) {
			if (course.getCourseID().equals(id))
				// return course once found by id
				printCourseInfo(course);
		}
		// return empty course if not found
		
	}
	
	// course management menu
	// overridden from User class
	@Override
	public void displayMenu(ArrayList<Course> courses, ArrayList<StudentUser> students) {
		Scanner input = new Scanner(System.in);
		int choice;
		
		do {
			System.out.println("\n~ADMIN COURSE MANAGEMENT MENU~");
			System.out.println("--------------------------------");
			System.out.println("Enter the number of the task you would like to perform:");
			System.out.println("1. Create a New Course");
			System.out.println("2. Delete a Course");
			System.out.println("3. Edit a Course");
			System.out.println("4. Display a Course's Info");
			System.out.println("5. Register a New Student");
			System.out.println("6. View the Reports Menu");
			System.out.println("7. Exit");
			choice = input.nextInt();
			input.nextLine();
			switch (choice) {
			case 1:
				// createCourse();
				if (createCourse(courses) == 1)
					System.out.println("Course Successfully Created!");
				break;
			case 2:
				// deleteCourse();
				// validation
				if (deleteCourse(courses) == 0)
					System.out.println("Could Not Find Course.");
				else
					System.out.println("Course Successfully Deleted.");
				break;
			case 3:
				// editCourse();
				// validation
				if (editCourse(courses) == 0)
					System.out.println("Could Not Find Course. Try Again.");
				else
					System.out.println("Course Successfully Edited!");
				break;
			case 4:
				System.out.println("Enter the ID of the course you would like to look at: ");
				String id = input.nextLine();
				searchByID(id, courses);
				break;
			case 5:
				//registerNewStud();
				// validation
				if (registerNewStud(students) == 1)
					System.out.println("Student Successfully Registered!");
				break;
			case 6:
				reportsMenu(courses, students);
				break;
			
			}
		} while (choice != 7);

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
		
		
		// input.close();
	}
	
	// just reports menu
	public void reportsMenu(ArrayList<Course> courses, ArrayList<StudentUser> students) {
		Scanner input = new Scanner(System.in);
		int choice;
		
		do {
			System.out.println("\n~REPORTS MENU~");
			System.out.println("----------------");
			System.out.println("Enter the number of the task you would like to complete: \n");
			System.out.println("1. View All Courses");
			System.out.println("2. View All Courses that are Full");
			System.out.println("3. Write to a File the List of Courses that are Full");
			System.out.println("4. View the Students Registered for a Specific Course");
			System.out.println("5. View a Student's Courses");
			System.out.println("6. Sort the Courses Based on # of Current Students Registered");
			System.out.println("7. Go Back to Course Management Menu");
			choice = input.nextInt();
			input.nextLine();
			
			switch (choice) {
			case 1:
				viewAllCourses(courses);
				break;
			case 2:
				viewFullCourses(courses);
				break;
			case 3:
				writeFullCourses(courses);
				break;
			case 4:
				viewStudsInCourse(courses);
				break;
			case 5:
				viewCoursesForStud(students);
				break;
			case 6:
				sortByStudents(courses);
				break;
			}
		} while(choice != 7);
		
		// input.close();
	}
}

// admin interface with specific methods
interface Admin {
	public int createCourse(ArrayList<Course> registry);
	public int deleteCourse(ArrayList<Course> registry);
	public int editCourse(ArrayList<Course> registry);
	public void printCourseInfo(Course c);
	public int registerNewStud(ArrayList<StudentUser> students);
	
	public void viewAllCourses(ArrayList<Course> registry);
	public void viewFullCourses(ArrayList<Course> registry);
	public void writeFullCourses(ArrayList<Course> registry);
	public void viewStudsInCourse(ArrayList<Course> registry);
	public void viewCoursesForStud(ArrayList<StudentUser> students);
	public void sortByStudents(ArrayList<Course> registry);
	
	public void displayMenu(ArrayList<Course> registry, ArrayList<StudentUser> students);
	public void reportsMenu(ArrayList<Course> registry, ArrayList<StudentUser> students);
}
