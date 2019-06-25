/* created by Chrys Ngouma
/* and Fatematuz Zohora
/* CISC 3810
*/

/* TODO: implement input validation */

package edu.cuny.brooklyn.cisc3810.ngoum.ui;

import edu.cuny.brooklyn.cisc3810.ngoum.db.DBConnect;
import edu.cuny.brooklyn.cisc3810.ngoum.db.Semester;
import edu.cuny.brooklyn.cisc3810.ngoum.db.Student;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private DBConnect db;
    private Scanner sc = new Scanner(System.in);
    private Student student;

    public MainApp() {
        //LOGGER.log(Level.INFO, "MainApp created");
        db = new DBConnect();
    }

    public void start(){
        boolean exit = false;

        while(!exit){
            // get student
            while(!validStudentno()){
                System.out.println();
                System.err.println("Error: student not found");
                System.out.println();
            }
            System.out.println();

            while(!exit){
                printMainMenu();
                String select = sc.next();

                if(select.compareToIgnoreCase("5") == 0 || select.compareToIgnoreCase("exit") == 0){
                    exit = true;
                }
                // service another student
                else if(select.compareToIgnoreCase("4") == 0 || select.compareToIgnoreCase("new") == 0){
                    student = null;
                    break;
                }
                // show student transcript
                else if(select.compareToIgnoreCase("3") == 0 || select.compareToIgnoreCase("transcript") == 0){
                    viewStudentAcademics();
                }
                // show student enrolled classes
                else if(select.compareToIgnoreCase("2") == 0 || select.compareToIgnoreCase("enrolled") == 0){
                    //viewEnrolledCourses();
                }
                // list available classes
                else if(select.compareToIgnoreCase("1") == 0 || select.compareToIgnoreCase("register") == 0){
                    registerForClasses();
                }
                else{
                    System.out.println("unknown command \"" + select + "\"");
                }
                System.out.println();
            }
        }

        sc.close();
        db.disconnectDB();
        LOGGER.log(Level.INFO, "MainApp stopped");
    }

    private boolean validStudentno(){
        System.out.println("Enter student number to start: ");
        int studentno = sc.nextInt();
        student = db.getStudent(studentno);
        return student != null;
    }

    private void printMainMenu(){
        System.out.println("Main Menu");
        System.out.println("(1) Register for Classes");
        System.out.println("(2) View Enrolled Classes");
        System.out.println("(3) View Course History");
        System.out.println("(4) Change Student");
        System.out.println("(5) Exit");
    }

    private void viewStudentAcademics(){
        System.out.println(student.getFullName() + "\t\t[ID: " + student.getStudentno() + "]");
        System.out.println("MAJOR:   " + student.getDegree() + ". " + student.getMajor());
        System.out.println("CREDITS: " + student.getTotalCredits());
        System.out.println("GPA:     " + student.getGpa());
        System.out.println();
        db.listCourseHistory(student.getStudentno());
    }

    private void registerForClasses(){
        /* list enrollment periods */
        ArrayList<Semester> semesters = db.getEnrollementPeriods(student.getStudentno());
        int select = 0;
        //LOGGER.log(Level.INFO, "semesters size: " + semesters.size());
        while(select < 1 || select > semesters.size()){
            System.out.println("ENROLLMENT PERIODS:");
            for(int i=0; i<semesters.size()-1; i++){
                System.out.println("("+(i+1)+") " + semesters.get(i).getSemester() + " " + semesters.get(i).getYear());
            }
            System.out.println();
            System.out.println("Select a semester:");
            select = sc.nextInt();
        }

        int year = semesters.get(select-1).getYear();
        String semester = semesters.get(select-1).getSemesterAbv();

        /* list available classes */
        int classno;
        while(true){
            db.listAvailableClasses(semester, year);
            System.out.println("enter a class number: ");
            classno = sc.nextInt();
            if(db.verifyClassNo(classno)) break;
            else{
                System.out.println("Error: class number [" + classno +"] NOT RECOGNIZED");
                System.out.println();
            }
        }

        /* check if course was already completed */
        if(db.checkForComflicts(student.getStudentno(), classno) || !db.checkPrerequisits(student.getStudentno(), classno)){
            //LOGGER.log(Level.WARNING, "Error: could not register for this class");
        }
        else{
            db.addClass(student.getStudentno(), classno);
            System.out.println("Class added");
            System.out.println();
        }
    }

    public void viewEnrolledClasses(int studentno){
        db.listEnrolledClasses(studentno);
    }
}