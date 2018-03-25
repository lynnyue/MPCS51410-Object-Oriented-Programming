import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this class is used to test the functionality of the system
public class SystemTest {
    public static void main(String[] args) {
        // connect to database via JDBC
        JDBC data = new JDBC();
        CourseListing winterCourses = null;

        // get a list of courses that are offered in the current quarter
        winterCourses = data.getCourses(2018, "Winter");

        // retrieve student information from database
        /*// create new students
        Student s1 = data.selectStudent(1710001, 12345);
        Student s2 = data.selectStudent(1710002, 12345);
        Student s3 = data.selectStudent(1710003, 12345);
        Student s4 = data.selectStudent(1710004, 12345);
        Student s5 = data.selectStudent(1710005, 12345);

        // retrieve instructor information from database
        // create new instructors
        Instructor i1 = data.selectInstructor(1720001, 12345);
        Instructor i2 = data.selectInstructor(1720002, 12345);
        Instructor i3 = data.selectInstructor(1720003, 12345);
        Instructor i4 = data.selectInstructor(1720004, 12345);
        Instructor i5 = data.selectInstructor(1720005, 12345);
        */

        SystemTest test = new SystemTest();

        int year = 2018;
        String quarter = "Winter";

        // change student password
        Student testStudent = data.selectStudent(1710001);
        test.studentChangePassword(testStudent, "111", data);
        System.out.println(testStudent.firstName + testStudent.lastName);
        System.out.println("This is your new password: " + testStudent.passWord);

        // change instructor password
        Instructor testInstructor = data.selectInstructor(1720001);
        test.instructorChangePassword(testInstructor, "111", data);
        System.out.println(testInstructor.firstName + testInstructor.lastName);
        System.out.println("This is your new password: " + testInstructor.passWord);

        // open registration
        test.systemOpenRegistration(year, quarter, data);
        System.out.println("Registration opened.");

        // register for a course
        test.registerCourse(year, quarter, testStudent, 54001, winterCourses, data);

        // instructor assign grade to student
        test.instructorAssignGrade(year, quarter, testInstructor, testStudent, 54001, "B+", winterCourses, data );

        // drop a course
        test.dropCourse(year, quarter, testStudent, 54001, winterCourses, data);

        // view roster
        test.viewRoster(year, quarter, testStudent, data);

        // view restrictions
        testStudent.viewRestrictions();

        // instructor assign grade to student
        test.instructorAssignGrade(year, quarter, testInstructor, testStudent, 54001, "B+", winterCourses, data );

        // modify consent
        test.instructorModifyConsent(testInstructor, 54001, false, data);

        // instructor view roster
        test.instructorViewRoster(year, quarter, testInstructor, testStudent, data);
        System.out.println("Instructor " + testInstructor.firstName + testInstructor.lastName + " viewd " + testStudent.firstName + testStudent.lastName + "'s Roster");

        // browse course by subject
        List<CourseOffering> list1 = winterCourses.browseBySubject("MPCS");
        for (CourseOffering item : list1) {
            System.out.println(item.course.department + item.course.courseNumber + item.course.name + item.sectionNumber);
        }

        // browse course by subject and name
        List<CourseOffering> list2 = winterCourses.browseBySubName("MPCS", "Networks");
        for (CourseOffering item : list2) {
            System.out.println(item.course.department + item.course.courseNumber + item.course.name + item.sectionNumber);
        }

        // browse course by instructor
        List<CourseOffering> list3 = winterCourses.browseByInstructor("Will");
        for (CourseOffering item : list3) {
            System.out.println(item.course.department + item.course.courseNumber + item.course.name + item.sectionNumber);
        }
    }


    // implement change password function for student
    // take a student object, a new passsword as input
    public void studentChangePassword(Student currentStudent, String newPassword, JDBC db) {
        int studentID = currentStudent.ID;
        String oldPassword = currentStudent.passWord;
        currentStudent.changePassword(newPassword);
        db.updateStudent(currentStudent);
    }

    // implement change password function for instructor
    public void instructorChangePassword(Instructor currentInstructor, String newPassword, JDBC db) {
        int instructorID = currentInstructor.ID;
        String oldPassword = currentInstructor.passWord;
        currentInstructor.changePassword(newPassword);
        db.updateInstructor(currentInstructor);
    }

    // implement open registration process for all of the courses
    // add student list to all of the courses
    // add roster to students
    public CourseListing systemOpenRegistration(int year, String quarter, JDBC db) {
        CourseListing currentCourses = db.getCourses(year, quarter);

        for (CourseOffering item : currentCourses.offeredCourse) {
            List<Student> studentList = new ArrayList<>();
            item.startRegistration(studentList);
        }
        return currentCourses;
    }

    /*public StudentListing studentOpenRegistration(int year, String quarter, JDBC db) {
        StudentListing currentStudents = db.getStudents(year, quarter);

        for (Student item : currentStudents.currentStudent) {
            HashMap<CourseOffering, String> studentRoster = new HashMap<>();
            Roster roster= new Roster(year, quarter, studentRoster);
            item.addRoster(roster);
        }
        return currentStudents;
    }*/

    // implement register for course
    public void registerCourse(int year, String quarter, Student student, int courseNumber, CourseListing currentCourses, JDBC db) {
        CourseOffering course = null;
        HashMap<Integer, String> grades = db.selectRoster(student.ID, year, quarter);
        HashMap<CourseOffering, String> studentRoster = new HashMap<>();
        for (Map.Entry<Integer, String> entry : grades.entrySet()) {
            if (entry.getKey() != 0) {
                course = db.selectCourse(entry.getKey());
                studentRoster.put(course, entry.getValue());
            }
        }

        Roster currentRoster = new Roster(year, quarter, studentRoster);
        student.roster = currentRoster;

        CourseOffering desiredCourse = null;
        for (CourseOffering item: currentCourses.offeredCourse) {
            if (item.course.courseNumber == courseNumber) {
                desiredCourse = item;
            }
        }
        if (desiredCourse == null) {
            System.out.println("Course not found.");
            return;
        }
        boolean success = student.registerCourse(desiredCourse, currentCourses);
        HashMap<Integer, String> update = new HashMap<>();
        for (Map.Entry<CourseOffering, String> entry : studentRoster.entrySet()) {
            update.put(entry.getKey().course.courseNumber, entry.getValue());
        }
        db.updateRoster(student.ID, update);
    }

    // implement drop course
    public void dropCourse(int year, String quarter, Student student, int courseNumber, CourseListing currentCourses, JDBC db) {
        CourseOffering course = null;
        HashMap<Integer, String> grades = db.selectRoster(student.ID, year, quarter);
        HashMap<CourseOffering, String> studentRoster = new HashMap<>();
        for (Map.Entry<Integer, String> entry : grades.entrySet()) {
            if (entry.getKey() != 0) {
                course = db.selectCourse(entry.getKey());
                studentRoster.put(course, entry.getValue());
            }
        }

        Roster currentRoster = new Roster(year, quarter, studentRoster);
        student.roster = currentRoster;

        CourseOffering desiredCourse = null;
        boolean success = student.dropCourse(courseNumber);
        HashMap<Integer, String> update = new HashMap<>();
        for (Map.Entry<CourseOffering, String> entry : studentRoster.entrySet()) {
            update.put(entry.getKey().course.courseNumber, entry.getValue());
        }
        db.updateRoster(student.ID, update);
    }

    // implement view roster
    public void viewRoster(int year, String quarter, Student student, JDBC db) {
        CourseOffering course = null;
        HashMap<Integer, String> grades = db.selectRoster(student.ID, year, quarter);
        HashMap<CourseOffering, String> studentRoster = new HashMap<>();
        for (Map.Entry<Integer, String> entry : grades.entrySet()) {
            if (entry.getKey() != 0) {
                course = db.selectCourse(entry.getKey());
                studentRoster.put(course, entry.getValue());
            }
        }

        Roster currentRoster = new Roster(year, quarter, studentRoster);
        student.roster = currentRoster;
        student.viewRoster();
    }

    // implement instructor assign grade
    public void instructorAssignGrade(int year, String quarter, Instructor instructor, Student student, int courseNum, String newGrade, CourseListing currentCourses, JDBC db) {
        CourseOffering course = null;
        HashMap<Integer, String> grades = db.selectRoster(student.ID, year, quarter);
        HashMap<CourseOffering, String> studentRoster = new HashMap<>();
        for (Map.Entry<Integer, String> entry : grades.entrySet()) {
            if (entry.getKey() != 0) {
                course = db.selectCourse(entry.getKey());
                studentRoster.put(course, entry.getValue());
            }
        }

        Roster currentRoster = new Roster(year, quarter, studentRoster);
        student.roster = currentRoster;

        instructor.assignGrade(student, courseNum, newGrade);


        HashMap<Integer, String> update = new HashMap<>();
        for (Map.Entry<CourseOffering, String> entry : studentRoster.entrySet()) {
            update.put(entry.getKey().course.courseNumber, entry.getValue());
        }
        db.updateRoster(student.ID, update);
    }

    // implement instructor modify consent
    public void instructorModifyConsent(Instructor instructor, int courseNum, boolean update, JDBC db) {
        CourseOffering course = db.selectCourse(courseNum);

        instructor.modifyConsent(course, update);
        if (update == false) {
            db.updateCourse(1, course.course.courseNumber);
        } else {
            db.updateCourse(0, course.course.courseNumber);
        }
    }

    // implement instructor view roster
    public void instructorViewRoster(int year, String quarter, Instructor instructor, Student student, JDBC db) {
        CourseOffering course = null;
        HashMap<Integer, String> grades = db.selectRoster(student.ID, year, quarter);
        HashMap<CourseOffering, String> studentRoster = new HashMap<>();
        for (Map.Entry<Integer, String> entry : grades.entrySet()) {
            if (entry.getKey() != 0) {
                course = db.selectCourse(entry.getKey());
                studentRoster.put(course, entry.getValue());
            }
        }

        Roster currentRoster = new Roster(year, quarter, studentRoster);
        student.roster = currentRoster;
        boolean success = instructor.viewRoster(student);
    }

}
