import java.util.Map;
import java.util.Set;

public class Instructor extends User {

    public Instructor(int ID, String firstName, String lastName, String email, String passWord){
        super(ID, firstName, lastName, email, passWord);
    }

    // modify course consent
    public void modifyConsent(CourseOffering selectedCourse, boolean update) {
        // check if the instructor is eligible for changing the feature
        if (!this.firstName.equals(selectedCourse.instructor)) {
            System.out.println("You are not teaching this course.");
            return;
        }
        selectedCourse.needConsent = update;
        System.out.println("Modification successful.");

    }

    // assign grade
    public void assignGrade(Student student, int courseNum, String grade) {
        for (Map.Entry<CourseOffering, String> entry :student.roster.studentRoster.entrySet()) {
            if (entry.getKey().course.courseNumber == courseNum) {
                student.roster.studentRoster.put(entry.getKey(), grade);
                System.out.println("You assigned: " + grade + " to " + student.firstName + " " + student.lastName);
            }
        }
        System.out.println("The student is not registered for the class.");
    }

    // view roster of registered student
    public boolean viewRoster(Student requiredStudent){
        // check if there is any courses in the roster
        if (requiredStudent.roster.studentRoster.isEmpty()) {
            System.out.println("The student haven't registered for any class.");
                return false;
        } else {
            Set<CourseOffering> courseList = requiredStudent.roster.studentRoster.keySet();
            for (CourseOffering item : courseList) {
                System.out.println(item.course.department + item.course.courseNumber + item.course.name + " section " +item.sectionNumber);
            }
            return true;
        }
    }
}
