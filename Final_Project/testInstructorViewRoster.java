import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

// test view roster function in Student class
public class testInstructorViewRoster{
    @Test
    public void testViewRoster() {
        // create a course that is offered in 2018 winter
        Course testCourse = new Course("MPCS", 51410,"Object Oriented Programming", 3);
        List<Student> studentList = new ArrayList<>();
        CourseOffering testOffering = new CourseOffering(testCourse, 1, "Mark", 2018, "Winter", 30,false);
        testOffering.startRegistration(studentList);


        // create a list of offered course
        List<CourseOffering> testCourseListing = new ArrayList<>();
        testCourseListing.add(testOffering);
        CourseListing testListing = new CourseListing(2018, "Winter", testCourseListing);

        // create a new student
        Student testStudent = new Student(001, "David", "Han", "d@uchicago.edu", "123", false,"full");

        // create a new roster
        HashMap<CourseOffering, String> studentRoster = new HashMap<>();
        Roster testRoster = new Roster(2018, "Winter", studentRoster);

        // add roster to the student
        testStudent.addRoster(testRoster);

        // register for the course
        testStudent.registerCourse(testOffering, testListing);

        // create a new instructor
        Instructor testInstructor = new Instructor(001, "David", "Han", "d@uchicago.edu", "123");

        // test view roster
        assertEquals(testInstructor.viewRoster(testStudent), true);
    }
}
