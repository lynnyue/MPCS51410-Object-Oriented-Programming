import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

// test modify consent function in Instructor class
public class testModifyConsent{
    @Test
    public void testModifyConsent() {
        // create a new instructor
        Instructor testInstructor = new Instructor(001, "Mark", "Shacklette", "mark@uchicago.edu", "123");

        //create a new courseOffering
        Course testCourse = new Course("MPCS", 51410,"Object Oriented Programming", 3);
        List<Student> studentList = new ArrayList<>();
        CourseOffering testOffering = new CourseOffering(testCourse, 1, "Mark", 2018, "Winter", 30,false);
        testOffering.startRegistration(studentList);


        // test updating modification
        boolean update = true;
        testInstructor.modifyConsent(testOffering, update);
        assertEquals(testOffering.needConsent, update);
    }
}