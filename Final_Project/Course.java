// course class represents a course offered (possibly repeatedly) at a university
public class Course {
    public Course(String department, int courseNumber, String name, int credits) {
        this.department = department;
        this.courseNumber = courseNumber;
        this.name = name;
        this.credits = credits;
    }

    //contains general info about a class
    public String department;
    public int courseNumber;
    public String name;
    // an integer value--the number of credits awarded for passing the course
    public int credits;

}
