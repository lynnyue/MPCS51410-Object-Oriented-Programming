README
Zhilin Yue
MPCS 51410 Object Oriented Programming Final Project

Part one: Introduction

In my final project, I focused on the registration process. I implemented eight classes in total.
User — a class representing the people involved in using the system. In this case, they are students and instructors. 

User class is a super class which contains the basic information of a person(ID, first name, last name, email and password). Changing password function is implemented in User class since it can apply to both student and instructor.

2. Student class is a subclass of User which inherits both the attributes and implementation of User class.  In addition to User, student has restriction(yes or no), type(full-time or part-time), roster as its unique attributes. Registering for courses, dropping courses, viewing roster and viewing restriction are implemented in student class.

3. Instructor class is another subclass of User which also inherits both the attributes and implementation of User class. It doesn’t have additional attributes besides the ones it inherits from User, it has additional implementation though including assigning grade to student and viewing student’s roster.

4. Course — a class representing a course offered (possibly repeatedly) at a university. It is a generalized version of a specific class that is offered in a certain year and quarter. 

5. CourseOffering combines the general idea of a course (e.g. Object Oriented Programming , offered on a regular basis at the school) with a specific instance of it being offered (e.g. 2018 Winter). It extends Course class by composition — it includes a course instance in it’s field and it added detailed features of an offered class by putting them in its attributes. Note that it contains a list of student but it is not initialized when constructing the class. Instead it is initialized when the registration process starts by implementing a startRegistration function in the class.

6. CourseListing: list all of the offered classes in a given year and quarter. Browsing courses by subject, browsing courses by subject and name, browsing courses by instructor are implemented in this class.

7. Roster: a class representing roster for a student. It contains year, quarter and a hash map that takes offered course as key and grade as value.

8. Student Listing: a class representing all of the registered students.

In my system design, I used Object Adapter pattern. CourseOffering class uses Course class’s interface by composition instead of inheritance.  The key idea is to adapt the interface of course class to course offering without changing it. 

Part two: Database
There are four models in my database. Student, Instructor, Course and Roster. They are connected to java codes via JDBC. I implemented select, update, delete and insert sql queries as needed. Creating and populating tables are written in .sql files. They are included in the same folder as my java codes. 

Part three: Instruction on running the code
My codes contain eight classes, thirteen Junit tests, a system test and a JDBC class. They are written in different java files. To test the functionality of each methods, run the Junit tests. To test thefunctionality of the system, run SystemTest.java which contains the functions that integrate the javacodes with database.
