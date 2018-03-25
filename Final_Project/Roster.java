import java.util.HashMap;

// roster of a student in a given quarter
public class Roster {

    public int year;
    public String quarter;
    HashMap<CourseOffering, String> studentRoster; // use a hashmap to store registered course and grades

    public Roster(int year, String quarter, HashMap<CourseOffering, String> studentRoster) {
        this.year = year;
        this.quarter = quarter;
        this.studentRoster = studentRoster;
    }
}
