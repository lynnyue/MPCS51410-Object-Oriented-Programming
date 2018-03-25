import java.util.List;

//List all of the students in the system
public class StudentListing {

    public int year;
    public String quarter;
    public List<Student> currentStudent;

    public StudentListing(int year, String quarter, List<Student> currentStudent) {
        this.year = year;
        this.quarter = quarter;
        this.currentStudent = currentStudent;
    }
}