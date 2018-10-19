
import java.io.*;
import java.util.*;

public class RegistryApp {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, EOFException {
		
		Scanner input = new Scanner(System.in);
		
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<StudentUser> students = new ArrayList<StudentUser>();
		
		AdminUser admin = new AdminUser();
		StudentUser student = new StudentUser();
		
		String csv = "MyUniversityCourses.csv";
		
		File fCourse = new File("courses.ser");
		File fStudents = new File("students.ser");

		// DESERIALIZATION
		// TESTS IF THE FILES EXIST
		// IF THEY DO, DESERIALIZATION
		
		if (fCourse.exists() && fStudents.exists()) {
			try {
				
				// FileInputStream receives bytes from the file
				FileInputStream coursesFile = new FileInputStream("courses.ser");
				FileInputStream studentsFile = new FileInputStream("students.ser");
				// OIS does deserialization 
				ObjectInputStream inFile = new ObjectInputStream(coursesFile);
				ObjectInputStream inStudent = new ObjectInputStream(studentsFile);
				
				
				courses = (ArrayList<Course>)inFile.readObject();
				students = (ArrayList<StudentUser>)inStudent.readObject();
				inFile.close();
				coursesFile.close();
				inStudent.close();
				studentsFile.close();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			
		}
		// IF FILES DON'T EXIST, LOAD THE CSV INTO ARRAYLIST OF COURSES
		else
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(csv)));
				String line;
				while ((line = br.readLine()) != null) {

					String[] entries = line.split(",");
					int max = Integer.parseInt(entries[2]);
					String[] studentsRegistered = new String[max];
					for (int i = 0; i < max; i++) {
						for (String e : entries[4].split("\n")) 
							studentsRegistered[i] = e; 
						}
					Course course = new Course(entries[0], entries[1], Integer.parseInt(entries[2]), Integer.parseInt(entries[3]), studentsRegistered, entries[5], entries[6], entries[7]);

					courses.add(course);
				}
			} catch(Exception e){
				e.printStackTrace(); 
			}

		System.out.println("Welcome to the Course Registration System.\n\n");
		System.out.println("Are you an Admin or a Student? Enter 'a' or 's'");
		
		String userType = input.nextLine();
		
		// username and password default to
		// Admin and Admin001
		
		boolean loop = true;
		if (userType.equals("a")) {
			
			System.out.println("Enter Admin Username: ");
			String usernameAd = input.next();
			System.out.println("Enter Admin Password: ");
			String passwordAd = input.next();
			
			while (!usernameAd.equals(admin.username) || !passwordAd.equals(admin.password)) {
				System.out.println("Username and/or Password combination not found. Try again.");
				System.out.println("Enter Admin Username: ");
				usernameAd = input.next();
				System.out.println("Enter Admin Password: ");
				passwordAd = input.next();
			}
			if (usernameAd.equals(admin.username) && passwordAd.equals(admin.password)) {
				
				System.out.println("\nHello Admin!");
				
				admin.displayMenu(courses, students);
			}
		}
		
		// need to create student user
		else if (userType.equals("s")) {
			// if no students registered student cant log in
			// admin must register a student
			if (students.size() == 0) {
				System.out.println("No students are registered. The Admin must register a student.");
			}
			else {
				while (loop) {
					System.out.println("Enter Your Username: ");
					String usernameStud = input.next();
					System.out.println("Enter Your Password: ");
					String passwordStud = input.next();
					
					for (StudentUser s : students) {
						if (s.username.equals(usernameStud) && s.password.equals(passwordStud)) {
							System.out.println("\nHello " + s.first + "!\n");
							student = s;
							loop = false;
						}
					}
				}
				// populate studentCourses array with empty Course objects
				for (int i = 0; i < 4; i++) {
					if (student.studentCourses[i] == null) {
						student.studentCourses[i] = new Course();
					}
				}
				student.displayMenu(courses, students);
				}
			}

	 //Serializiation
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
