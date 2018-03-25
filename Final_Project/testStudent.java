import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.AssertJUnit.assertEquals;

// test change password function in Account class
public class testStudent{
    @Test
    public void testChangePassword() {
        // create a new student roster
        HashMap<CourseOffering, String> studentRoster = new HashMap<>();
        Roster testRoster = new Roster(2018, "Winter", studentRoster);

        // create a new student
        Student testStudent = new Student(001, "David", "Han", "d@uchicago.edu", "123", false,"full");

        // test changing password
        String newPassWord = "456";
        testStudent.changePassword(newPassWord);
        assertEquals(testStudent.passWord, newPassWord);
    }
}
