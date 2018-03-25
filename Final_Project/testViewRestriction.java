import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.AssertJUnit.assertEquals;

// test vew restriction function in Student class
public class testViewRestriction{
    @Test
    public void testViewRestriction() {
        // create a new student roster
        HashMap<CourseOffering, String> studentRoster = new HashMap<>();
        Roster testRoster = new Roster(2018, "Winter", studentRoster);

        // create a new student
        Student testStudent = new Student(001, "David", "Han", "d@uchicago.edu", "123", false,"full");

        // test viewing restriction
        assertEquals(testStudent.viewRestrictions(), false);
    }
}