import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

// test creation of courseOffering class
public class testCourseCreation{
    @Test
    public void testCourseCreation() {
        Course testCourse = new Course("MPCS", 51410,"Object Oriented Programming", 3);
        List<Student> studentList = new ArrayList<>();
        CourseOffering testOffering = new CourseOffering(testCourse, 1, "Mark", 2018, "Winter", 30,false);
        testOffering.startRegistration(studentList);


        assertEquals(testOffering.course.courseNumber, 51410);
    }
}