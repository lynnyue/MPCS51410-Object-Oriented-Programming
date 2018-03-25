import java.util.List;

// CourseOffering representing a specific offering of a course
// Combines the general idea of a course with a specific instance of it being offered
public class CourseOffering {

    // an instance of the Course class
    public Course course;
    // a section number for the offering
    public int sectionNumber;
    public String instructor;
    public int year;
    public String quarter;
    // maximum number of student that can fit in the classsroom
    public int maxRoomVol;
    // true indicates that registration needs instructor consent
    public boolean needConsent;
    // a list of name of students who registered for the class
    public List<Student> registeredStudent;

    public CourseOffering(Course course, int sectionNumber, String instructor, int year, String quarter, int maxRoomVol, boolean needConsent) {
        this.course = course;
        this.sectionNumber = sectionNumber;
        this.instructor = instructor;
        this.year = year;
        this.quarter = quarter;
        this.maxRoomVol = maxRoomVol;
        this.needConsent = needConsent;

    }

    public void startRegistration(List<Student> registeredStudent) {
        this.registeredStudent = registeredStudent;
    }
}
