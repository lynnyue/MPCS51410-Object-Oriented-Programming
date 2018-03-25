import java.util.ArrayList;
import java.util.List;

//List all of the courses offered for a given year and quarter
public class CourseListing {

    public int year;
    public String quarter;
    public List<CourseOffering> offeredCourse;

    public CourseListing (int year, String quarter, List<CourseOffering> offeredCourse) {
        this.year = year;
        this.quarter = quarter;
        this.offeredCourse = offeredCourse;
    }

    // browse the course by subject
    public List<CourseOffering> browseBySubject(String subject) {
        // create a list to store the result
        List<CourseOffering> res = new ArrayList<>();
        // edge case
        if (subject == null) {
            return res;
        }
        // go through the list and pick the result
        for (CourseOffering item : this.offeredCourse) {
            if (item.course.department.equals(subject)) {
                res.add(item);
            }
        }
        return res;
    }

    // browse the course by subject and course
    public List<CourseOffering> browseBySubName(String subject, String name) {
        // create a list to store the result
        List<CourseOffering> res = new ArrayList<>();
        // edge case
        if (subject == null || name == null) {
            return res;
        }
        // go through the list and pick the result
        for (CourseOffering item : this.offeredCourse) {
            if (item.course.department.equals(subject) && item.course.name.equals(name)) {
                res.add(item);
            }
        }
        return res;
    }

    // browse the course by instructor name
    public List<CourseOffering> browseByInstructor(String instructorName) {
        // create a list to store the result
        List<CourseOffering> res = new ArrayList<>();
        // edge case
        if (instructorName == null) {
            return res;
        }
        // go through the list and pick the result
        for (CourseOffering item : this.offeredCourse) {
            if (item.instructor.equals(instructorName)) {
                res.add(item);
            }
        }
        return res;
    }
}

