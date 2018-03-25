import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// functions:
// select: selectCourse, getCourses, getStudents, selectInstructor, selectInstructor, selectStudent, selectRoster
// insert: insertInstructor, insertStudent
// delete: deleteStudent
// update: updateStudent, updateInstructor, updateRoster, updateCourse

public class JDBC {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://mpcs53001.cs.uchicago.edu/zlyueDB";

    //  Database credentials
    static final String USER = "zlyue";
    static final String PASS = "Eize7Pia";

    public static void main(String[] args) {
        Instructor instructor = null;
        Student student = null;
        CourseListing list = null;
        JDBC test = new JDBC();

        //test update roster
        HashMap<Integer, String> grades = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            grades.put(0, "X");
        }
        test.updateRoster(1710002, grades);


        //test get course
        list = test.getCourses(2018, "Winter");
        System.out.print(list.year + list.quarter);

        // test select instructor
        instructor = test.selectInstructor(1720001);
        System.out.print(instructor.firstName + " " + instructor.lastName);

        // test select student
        student = test.selectStudent(1710002);
        System.out.print(student.firstName + " " + student.lastName);

        // test insert instructor
        Instructor testInstructor = new Instructor(1720014, "Ava", "Han", "david@uchicago.edu", "12345");
        test.insertInstructor(testInstructor);

        // test insert student
        Student testStudent = new Student(1710023, "Sam", "Yue", "sam@uchicago.edu", "12345", false, "full");
        test.insertStudent(testStudent);



        //test.delete();

    }

    // select a course with its number
    public CourseOffering selectCourse(int courseNum) {
        Connection conn = null;
        Statement stmt = null;
        CourseOffering offeredCourse = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM course WHERE coursenumber = " + courseNum;
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                String department = rs.getString("department");
                int number = rs.getInt("coursenumber");
                String name = rs.getString("coursename");
                int credits = rs.getInt("credits");
                int section = rs.getInt("section");
                String instructor = rs.getString("instructor");
                int year = rs.getInt("offeryear");
                String quarter = rs.getString("offerquarter");
                int maxroom = rs.getInt("maxroom");
                int consent = rs.getInt("consent");
                boolean needconsent = false;
                if (consent == 0) {
                    needconsent = false;
                } else {
                    needconsent = true;
                }

                Course course = new Course(department, number, name, credits);
                offeredCourse = new CourseOffering(course, section, instructor, year, quarter, maxroom, needconsent);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return null;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return offeredCourse;
    }


    // get a list of offered course in a given year and quarter
    // return a courseListing object
    public CourseListing getCourses(int year, String quarter) {
        Connection conn = null;
        Statement stmt = null;
        List<CourseOffering> courseList = new ArrayList<>();
        CourseListing listAll = new CourseListing(year, quarter, courseList);
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM course WHERE offeryear = " + year + " AND offerquarter = " + "'" + quarter + "'";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                String department = rs.getString("department");
                int number = rs.getInt("coursenumber");
                String name = rs.getString("coursename");
                int credits = rs.getInt("credits");
                int section = rs.getInt("section");
                String instructor = rs.getString("instructor");
                int maxroom = rs.getInt("maxroom");
                int consent = rs.getInt("consent");
                boolean needconsent = false;
                if (consent == 0) {
                    needconsent = false;
                } else {
                    needconsent = true;
                }

                Course course = new Course(department, number, name, credits);
                CourseOffering offeredCourse = new CourseOffering(course, section, instructor, year, quarter, maxroom, needconsent);
                courseList.add(offeredCourse);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return null;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return listAll;
    }


    // get all current students in the system
    public StudentListing getStudents(int year, String quarter) {
        Connection conn = null;
        Statement stmt = null;
        List<Student> studentList = new ArrayList<>();
        StudentListing allStudents = new StudentListing(year, quarter, studentList);
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM student";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int studentID  = rs.getInt("ID");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("passW");
                int restriction = rs.getInt("restriction");
                boolean stuRes = false;
                if (restriction == 0) {
                    stuRes = false;
                } else {
                    stuRes = true;
                }
                String type = rs.getString("type");

                // pass the data to the java codes
                Student student = new Student(studentID, first, last, email, password, stuRes, type);
                allStudents.currentStudent.add(student);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return null;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return allStudents;
    }

    // select an instructor from the database by his ID and password
    // return an instructor instance
    // function: retrieve instructor information with id and passWord
    public Instructor selectInstructor(int id) {
        Connection conn = null;
        Statement stmt = null;
        Instructor instructor = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM instructor WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int InstructorID  = rs.getInt("ID");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("passW");

                //Display values
                System.out.print("ID: " + id);
                System.out.print("First name: " + first);
                System.out.println("Last name: " + last);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);

                // pass the data to the java codes
                instructor = new Instructor(id, first, last, email, password);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return null;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return instructor;
    }

    // select a student from the database by his ID and password
    // return a student instance
    // function: retrieve student information with id and passWord
    public Student selectStudent(int id) {
        Connection conn = null;
        Statement stmt = null;
        Student student = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM student WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int studentID  = rs.getInt("ID");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("passW");
                int restriction = rs.getInt("restriction");
                boolean stuRes = false;
                if (restriction == 0) {
                    stuRes = false;
                } else {
                    stuRes = true;
                }
                String type = rs.getString("type");

                //Display values
                System.out.print("ID: " + id);
                System.out.print("First: " + first);
                System.out.println("Last: " + last);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
                System.out.println("Restriction: " + stuRes);
                System.out.println("Student Type: " + type);

                // pass the data to the java codes
                student = new Student(id, first, last, email, password, stuRes, type);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return null;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return student;
    }

    // select roster by student id, year and quarter
    // return a Roster object
    public HashMap<Integer, String> selectRoster(int id, int year, String quarter) {
        Connection conn = null;
        Statement stmt = null;
        HashMap<Integer, String> studentRoster = new HashMap<>();
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM roster WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int studentID  = rs.getInt("ID");
                int courseone = rs.getInt("courseoneID");
                int coursetwo = rs.getInt("coursetwoID");
                int coursethree = rs.getInt("coursethreeID");
                String gradeone = rs.getString("gradeone");
                String gradetwo = rs.getString("gradetwo");
                String gradethree = rs.getString("gradethree");
                studentRoster.put(courseone, gradeone);
                studentRoster.put(coursetwo, gradetwo);
                studentRoster.put(coursethree, gradethree);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return null;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return studentRoster;
    }



    // take an instructor instance and insert it into the database
    public void insertInstructor(Instructor instructor) {
        Connection conn = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");

            String sql;
            int id = instructor.ID;
            String first = instructor.firstName;
            String last = instructor.lastName;
            String email = instructor.email;
            String password = instructor.passWord;

            sql = "INSERT INTO instructor (ID, firstName, lastName, email, passW)" + " VALUES (?, ?, ?, ?, ?) ";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, first);
            preparedStmt.setString(3, last);
            preparedStmt.setString(4, email);
            preparedStmt.setString(5, password);

            preparedStmt.execute();

            //STEP 6: Clean-up environment
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Instructor successfully inserted into the database.");
        System.out.println("Goodbye!");
    }

    // take a student instance and insert it into the database
    public void insertStudent(Student student) {
        Connection conn = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");

            String sql;
            int id = student.ID;
            String first = student.firstName;
            String last = student.lastName;
            String email = student.email;
            String password = student.passWord;
            boolean restriction = student.restriction;
            int stuRes = 0;
            if (restriction == false) {
                stuRes = 0;
            } else {
                stuRes = 1;
            }
            String type = student.type;

            sql = "INSERT INTO student (ID, firstName, lastName, email, passW, restriction, type)" + " VALUES (?, ?, ?, ?, ?, ?, ?) ";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, first);
            preparedStmt.setString(3, last);
            preparedStmt.setString(4, email);
            preparedStmt.setString(5, password);
            preparedStmt.setInt(6, stuRes);
            preparedStmt.setString(7, type);


            preparedStmt.execute();

            //STEP 6: Clean-up environment
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Student successfully inserted into the database.");
        System.out.println("Goodbye!");
    }

    // delete a student's account from the database
    public void deleteStudent() {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "DELETE FROM student WHERE ID = 1710024";
            System.out.println("Student successfully deleted from the database.");
            stmt.executeUpdate(sql);

            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    // update a student's password
    public void updateStudent(Student update) {
        Connection conn = null;
        Statement stmt = null;
        String newPassword = update.passWord;
        int id = update.ID;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE student SET passW = " + "'" + newPassword + "'" + "WHERE ID = " + id;
            stmt.executeUpdate(sql);

            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Database successfully updated.");
        System.out.println("Goodbye!");
    }

    // update an instructor's password
    public void updateInstructor(Instructor update) {
        Connection conn = null;
        Statement stmt = null;
        String newPassword = update.passWord;
        int id = update.ID;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE instructor SET passW = " + "'" + newPassword + "'" + "WHERE ID = " + id;
            stmt.executeUpdate(sql);

            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Database successfully updated.");
        System.out.println("Goodbye!");
    }

    // update a student's roster
    // input is student id
    public void updateRoster(int id, HashMap<Integer, String> update) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            int[] course = new int[3];
            String[] grade = new String[3];
            int i = 0;
            for (Map.Entry<Integer, String> entry : update.entrySet()) {
                course[i] = entry.getKey();
                grade[i] = entry.getValue();
                i++;
            }
            for (int j = i; j < 3; j++) {
                course[j] = 0;
                grade[j] = "X";
            }
            int c1 = course[0];
            int c2 = course[1];
            int c3 = course[2];
            String g1 = grade[0];
            String g2 = grade[1];
            String g3 = grade[2];
            sql = "UPDATE roster SET courseoneID = " + c1 + ", coursetwoID = " + c2 + ", coursethreeID = " + c3 + ", gradeone = " + "'" + g1 + "'" + ", gradetwo = " + "'" + g2 + "'" + ", gradethree = " + "'" + g3 + "'" + " WHERE ID = " + id;
            stmt.executeUpdate(sql);

            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Roster successfully updated.");
        System.out.println("Goodbye!");
    }

    // update course
    public void updateCourse(int update, int courseNum) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE course SET consent = " + update + " WHERE coursenumber = " + courseNum;
            stmt.executeUpdate(sql);

            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Course successfully updated.");
        System.out.println("Goodbye!");
    }




    //end main
}//end FirstExample
