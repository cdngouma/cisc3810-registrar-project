package edu.cuny.brooklyn.cisc3810.ngoum.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private final int MILLENIUM = 2000;
    private Connection con;
    private Statement st;

    public DBConnect(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "cisc3810", "3810");
            LOGGER.log(Level.FINE, "Connection to database successful");
            st = con.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Connection to database failed");
            e.printStackTrace();
        }
    }

    public void disconnectDB(){
        try {
            con.close();
            LOGGER.log(Level.FINE, "Database disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void  addClass(int studentno, int classno){
        try{
            String query = "insert into studentenrolledclasses values ("+studentno+","+classno+","+
                    "(select periodno from classes where classno="+classno+"))";
            st.executeQuery(query);
            //LOGGER.log(Level.INFO, "class added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student getStudent(int studentno){
        try{
            String query = "SELECT firstname, lastname, degreeabv, major, division, transfCr, earnedCr, attemptcr, gpa\n" +
                    "FROM students WHERE studentno=" + studentno;
            ResultSet res = st.executeQuery(query);
            while(res.next()){
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                String degree = res.getString("degreeabv");
                String major = res.getString("major");
                double transfCr = res.getDouble("transfcr");
                double earnedCr = res.getDouble("earnedcr");
                double attemptCr = res.getDouble("attemptcr");
                double gpa = res.getDouble("gpa");
                char division = (res.getString("division")).charAt(0);

                return new Student(studentno, firstName, lastName, degree, major, division, earnedCr, attemptCr, transfCr, gpa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void listCourseHistory(int studentno){
        try{
            String query = "select coursesubject, courselevel, coursename, semester, EXTRACT(YEAR FROM TO_DATE(startDate)) AS \"year\", grade\n" +
                    "from studentcompletedcourses\n" +
                    "join courses on studentcompletedcourses.courseno=courses.courseno\n" +
                    "join enrollmentperiods on studentcompletedcourses.periodno=enrollmentperiods.periodno\n" +
                    "where studentno=" + studentno + "\n" +
                    "order by \"year\" desc, enrollmentperiods.semester, coursesubject, courses.courselevel";
            ResultSet res = st.executeQuery(query);

            System.out.println("COURSE HISTORY:");
            System.out.println();
            System.out.println("Course   \tSemester\tGrade");
            System.out.println("---------\t--------\t-----");
            while(res.next()){
                System.out.print(res.getString("coursesubject") + " ");
                System.out.print(res.getInt("courselevel") + "\t");
                System.out.print(res.getString("semester") + " ");
                System.out.print((res.getInt("year") % MILLENIUM) + "   \t");
                System.out.println(res.getString("grade"));
            }

            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Semester> getEnrollementPeriods(int studentno){
        try{
            ArrayList<Semester> semesters = new ArrayList<>();
            String query = "select * from enrollmentperiods where startDate >= SYSDATE";
            ResultSet res = st.executeQuery(query);
            while(res.next()){
                int periodno = res.getInt("periodno");
                String sem = res.getString("semester");
                Date start = res.getDate("startdate");
                Date end = res.getDate("enddate");
                semesters.add(new Semester(periodno, sem, start, end));
            }

            return semesters;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* TODO: implement this */
    public void listEnrolledClasses(int studentno){

    }

    /* TODO: implement this */
    /* private ArrayList<int[]> getClassDayTime(int classNo){
        try{
            String[] days = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
            ArrayList<int[]> daytimes = new ArrayList<>();
            String query = "select classday, startTime, endTime from classdaytime where classno=" + classNo;
            ResultSet res = st.executeQuery(query);
            while(res.next()){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public void listAvailableClasses(String semester, int year){
        try{
            String query = "select classes.classno, courses.courseno, coursesubject, courselevel, coursename, building, room, firstname, lastname, instructMode\n" +
                    "from classes\n" +
                    "join courses on classes.courseno=courses.courseno\n" +
                    "left join instructors on classes.instructorno=instructors.instructorno\n" +
                    "join enrollmentperiods on classes.periodno=enrollmentperiods.periodno\n" +
                    "where roomavailable > 0 AND semester='" + semester + "' AND EXTRACT(YEAR FROM TO_DATE(SYSDATE))=" + year + "\n" +
                    "order by coursesubject, courselevel";

            ResultSet res = st.executeQuery(query);

            System.out.println("AVAILABLE CLASSES:");
            while(res.next()){
            //    ArrayList<int[]> daytimes = getClassDayTime();
                System.out.println(res.getString("coursesubject") + " " + res.getString("courselevel") + " - " + res.getString("coursename"));
                System.out.println("ClassNo\t Room  \t  Instructor");
                System.out.println("-------\t-------\t--------------");
                System.out.print(res.getInt("classno") + "  \t");

                if(res.getString("building") == null || res.getInt("room") == 0) System.out.print("TBA    \t");
                else System.out.print(res.getString("building") + " " + res.getInt("room") + "\t");

                if(res.getString("firstname") == null || res.getString("lastname") == null) System.out.println("TBA           \t");
                else System.out.println((res.getString("firstname")).charAt(0) + ". " + res.getString("lastname") + "\t");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printClasses(ResultSet res) throws SQLException{
        while(res.next()){
            //    ArrayList<int[]> daytimes = getClassDayTime();
            System.out.println(res.getString("coursesubject") + " " + res.getString("courselevel") + " - " + res.getString("coursename"));
            System.out.println("ClassNo\t Room  \t  Instructor");
            System.out.println("-------\t-------\t--------------");
            System.out.print(res.getInt("classno") + "  \t");

            if(res.getString("building") == null || res.getInt("room") == 0) System.out.print("TBA    \t");
            else System.out.print(res.getString("building") + " " + res.getInt("room") + "\t");

            if(res.getString("firstname") == null || res.getString("lastname") == null) System.out.println("TBA           \t");
            else System.out.println((res.getString("firstname")).charAt(0) + ". " + res.getString("lastname") + "\t");
            System.out.println();
        }
    }

    public boolean verifyClassNo(int classno){
        try{
            String query = "select 1 from classes where classno=" + classno;
            ResultSet res = st.executeQuery(query);
            if(res.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // check if student is already enrolled in this class
    private boolean isClassEnrolled(int studentno, int classno){
        try{
            String query = "select 1 from studentenrolledclasses\n" +
                    "where studentno=" + studentno + " AND classno=" + classno;
            ResultSet res = st.executeQuery(query);
            if(res.next()){
                System.out.println("Error: student already enrolled in this class");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // check is student completed this course
    private boolean isCourseCompleted(int studentno, int classno){
        try{
            String query = "select 1 from studentcompletedcourses\n" +
                    "where studentno=" + studentno + " AND courseno=" +
                    "(select courseno from classes where classno=" + classno + ")";
            ResultSet res = st.executeQuery(query);
            if(res.next()){
                System.out.println("Error: student already completed this class");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // check if course comflicts with another course
    private boolean doesClassComflicts(int classno){
        try{
            String query = "select 1 from courseincompatible where courseno=(select courseno from classes where classno=" + classno + ")";
            ResultSet res = st.executeQuery(query);
            if(res.next()){
                System.out.println("Error: course conflicting");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkForComflicts(int studentno, int classno){
        return isClassEnrolled(studentno, classno) || doesClassComflicts(classno) || isCourseCompleted(studentno, classno);
    }

    /* TODO: Implement prerequisits verification */
    public boolean checkPrerequisits(int studentno, int classno){
        return true;
    }
}
