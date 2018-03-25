import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

// test change password function in Instructor class
public class testInstructor{
    @Test
    public void testChangePassword() {
        // create a new instructor
        Instructor testInstructor = new Instructor(001, "David", "Han", "d@uchicago.edu", "123");

        // test changing password
        String newPassWord = "456";
        testInstructor.changePassword(newPassWord);
        assertEquals(testInstructor.passWord, newPassWord);
    }
}
