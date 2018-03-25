import java.util.Map;
import java.util.Set;

public class Student extends User {

    public boolean restriction; // true when there is restiction on the student's account
    public String type; // classify the type of the student: full or part time
    public Roster roster;

    public Student(int ID, String firstName, String lastName, String email, String passWord, boolean restriction, String type) {
        super(ID, firstName, lastName, email, passWord);
        this.restriction = restriction;
    }

    public void addRoster(Roster studentRoster) {
        this.roster = studentRoster;
    }

    //register for course
    public boolean registerCourse(CourseOffering desiredCourse, CourseListing allCourse) {
        // check restriction
        if (this.restriction == true) {
            System.out.println("You have restrictions on your account. Registration denied. Please contact the admin.");
            return false;
        }
        // check if the course need consent
        if (desiredCourse.needConsent == true) {
            System.out.println("This course need consent. Please contact the course instructor.");
            return false;
        }
        // check room availability
        // If the current section if full, put the student in the next available section
        /*if (desiredCourse.registeredStudent.size() >= desiredCourse.maxRoomVol) {
            for (CourseOffering i : allCourse.offeredCourse) {
                if(i.course.name == desiredCourse.course.name) {
                    if (i.sectionNumber != desiredCourse.sectionNumber && i.registeredStudent.size() < i.maxRoomVol) {
                        desiredCourse = i;
                        break;
                    }
                }
            }
        }*/

        // check if the student is already registered for three courses
        // check the student's type first
        /*if (this.type == "full") {
            if (this.roster.studentRoster.size() == 3) {
                System.out.println("You already registered for 3 courses");
                return false;
            }
        } else {
            if (this.roster.studentRoster.size() == 1) {
                System.out.println("You already registered for 1 course");
                return false;
            }
        }*/

        // student passed all of the checks
        // student can register for the course
        // put the course in the student's roster
        // put the student in the course' student list
        if(this.roster.studentRoster.containsKey(desiredCourse)) {
            System.out.println("You already registered for this class!");
            return false;
        } else {
            this.roster.studentRoster.put(desiredCourse, "X"); // put X as default grade
            //desiredCourse.registeredStudent.add(this);
            System.out.println("You have successfully registered for the class!");
            return true;
        }
    }

    // drop a course
    public boolean dropCourse(int courseNum) {
        // check if this course is registered
        for (Map.Entry<CourseOffering, String> entry : this.roster.studentRoster.entrySet()) {
            if (entry.getKey().course.courseNumber == courseNum) {
                this.roster.studentRoster.remove(entry.getKey());
                System.out.println("You have successfully dropped the class!");
                return true;
            }
        }
        System.out.println("You didn't register for the class!");
        return false;
    }

    // view/print roster
    public boolean viewRoster() {
        // check if there is any courses in the roster
        if (roster.studentRoster.isEmpty()) {
            System.out.println("You haven't registered for any class.");
            return false;
        } else {
            Set<CourseOffering> courseList = roster.studentRoster.keySet();
            for (CourseOffering item : courseList) {
                System.out.println(item.course.department + item.course.courseNumber + item.course.name + " section " +item.sectionNumber);
            }
            return true;
        }
    }

    // view restrictions
    public boolean viewRestrictions() {
        if (this.restriction) {
            System.out.println("There is restriction on your account.");
            return true;
        } else {
            System.out.println("There is no restriction on your account.");
            return false;
        }
    }
}
