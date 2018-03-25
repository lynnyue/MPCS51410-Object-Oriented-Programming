import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

// test browse by subject function in CourseListing class
public class testBrowsebySubject{
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

        String testSubject = "MPCS";

        // test browse by subject
        assertEquals(testListing.browseBySubject(testSubject).contains(testOffering), true);
    }
}
