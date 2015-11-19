package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import database.Appointment;

public class Student {
	int id;
	String Name;
	String email;
	static final Logger logger = Logger.getLogger("Student_Log");

	public Student(int id, String name, String email){
		this.id = id;
		this.Name = name;
		this.email = email;
                
                FileHandler fh;  
                try {  
                    fh = new FileHandler("C:/Homework/Student_Log.log");  
                    logger.addHandler(fh);
                    SimpleFormatter formatter = new SimpleFormatter();  
                    fh.setFormatter(formatter);                          
                    } catch (SecurityException | IOException e) {
                    }
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getName(){
		return this.Name;
	}
        
	public Appointment[] getAppointments() throws SQLException {
		return Appointment.studentGetAppointments(this);
	}
	
	public void reserveSeat(Test exam, Date start, Date end, int seatNumber){
		Appointment.addAppointment(seatNumber, start, end, exam, this); //Test Class
	}
	
	public void cancel(Test exam){
		 Appointment.removeAppointment(this,exam);
	}

	public static Student getStudent(int num) {
		String query = "Select * FROM student WHERE id="+num;
		ResultSet result = Connection.ExecQuery(query);
		ArrayList<Student> stulist = Parse(result);
		return stulist.get(0);
	}
	
	public static ArrayList<Student> Parse(ResultSet result){
        ArrayList<Student> list = new ArrayList<>();
        try {
            while(result.next())
            {
            	int id = result.getInt("Id");
            	String name = result.getString("Name");
            	String email = result.getString("Email");
            	list.add(new Student(id, name, email));
            }
            logger.log(Level.FINE, "Student result set parsed");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return list;
	}

	public Test[] getTests() {
		String query = "Select T.* FROM studenttests S, test T WHERE StudentId="+this.getID()+" AND "
                        + "S.TestId = T.Id;";
		ResultSet result = Connection.ExecQuery(query);
		ArrayList<Test> list = new ArrayList<>();
                try {
                    while(result.next()){
			
				int Id = result.getInt("Id");
				String classId = result.getString("classId");
				Date startDate = result.getDate("startDate");
				Date endDate =  result.getDate("endDate");
				int duration = result.getInt("duration");
				int instructorId = result.getInt("instructorId");
				boolean approved = result.getBoolean("approved");
				Test Exam = new Test(Id, classId,startDate,endDate,duration,instructorId,approved);
				list.add(Exam);
                                logger.log(Level.FINE, "List of Test Made");
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, ex.getMessage(),ex);
	            
	        }
		return list.toArray(new Test[0]);
	}
	
	
}
