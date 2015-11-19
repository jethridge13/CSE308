package database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.Student;
import database.Test;

public class Appointment {
    private int seat;
    private Date startTime;
    private Date endTime;

    private boolean attended;
    private static Connection connection;
    private static Student student;
    private Test test;
    
    private Appointment(int seatNumber, Date start, Date end, Test exam,
                     Student stud, boolean attend)
    {
        seat = seatNumber;
        startTime = start;
        endTime = end;
        test = exam;
        student = stud;
        attended = attend;
    }
    
	public static void addAppointment(int seatNumber, Date start, Date end,
			Test exam, Student stud) {
        String query = "INSERT INTO Appointment (Seat,StartDate,EndDate,StudentId,TestId,Attendence"
                + "VALUES (" + seatNumber + "," + start + "," + end + ","
                + stud.getID() + "," + exam.getId() + ",false);";
		if(connection.ExecUpdateQuery(query) != 0)
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, "Could not Insert into Appointment");
        else Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, "Successfully Inserted into Appointment");
    }
    
    public static void removeAppointment(Student stud, Test exam)
    {
        String query = "DELETE FROM Appointment WHERE StudentId = " + stud.getID() +
                "TestId = " + exam.getId();
        if(connection.ExecUpdateQuery(query) != 0)
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, "Could not delete from Appointment");
        else Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, "Successfully deleted from Appointment");
    }
    
    public static Appointment[] studentGetAppointments(Student stud) throws SQLException
    {
        String testIds = "{";
        Test[] testList = stud.getTests();
        
        for (Test testList1 : testList) {
            testIds += testList1.getId() + ",";
        }
        
        testIds = "}";
        String query = "SELECT * FROM Appointment WHERE StudentId = " + stud.getID() + 
                    "AND TestId IS IN " + testIds + ";";
        ResultSet result = connection.ExecQuery(query);
        return parseResultSet(result);
    }
    
    public static Appointment[] instructorGetAppointments(Test exam)
    {
        String query = "SELECT * FROM Appointment WHERE TestId =" + exam.getId() + ";";
        ResultSet result = connection.ExecQuery(query);
        return parseResultSet(result);
    }
    
    private static Appointment[] parseResultSet(ResultSet result)
    {
        ArrayList<Appointment> list = new ArrayList<>();
        try {
            while(result.next())
            {
                int seatNumber = result.getInt("Seat");
                Timestamp time1 = result.getTimestamp("StartDate");
                Date start;
                if(time1 != null)
                    start = new Date(time1.getTime());
                else start = null;
                Timestamp time2 = result.getTimestamp("EndDate");
                Date end;
                if(time2 != null)
                    end = new Date(time1.getTime());
                else end = null;
                Student resultStudent = student.getStudent(result.getInt("StudentId"));
                Test exam = Test.getTest(result.getInt("TestId"));
                boolean attend = result.getBoolean("Attendence");
                list.add(new Appointment(seatNumber,start,end,exam,resultStudent,attend));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list.toArray(new Appointment[1]);
    }

    /**
     * @return the seat
     */
    public int getSeat() {
        return seat;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @return the test
     */
    public Test getTest() {
        return test;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @return the attended
     */
    public boolean isAttended() {
        return attended;
    }

}
