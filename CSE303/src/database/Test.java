package database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
	int Id;
	String classId;
	Date startDate;
	Date endDate;
	int duration;
	int instructorId;
	boolean approved;
	
	public Test(int Id, String classId, Date startDate, Date endDate, int duration, int instructorId, boolean approved){
		this.Id = Id;
		this.classId = classId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = duration;
		this.instructorId = instructorId;
		this.approved = approved;
	}
	
	public int getId(){
		return this.Id;
	}
	
	public String getClassId(){
		return this.classId;
	}
	
	public Date getStartDate(){
		return this.startDate;
	}
	
	public Date getEndDate(){
		return this.endDate;
	}
	
	public int getDuration(){
		return this.duration;
	}
	
	public int instructorId(){
		return this.instructorId;
	}
	
	public boolean approved(){
		return this.approved;
	}
	

	public static Test getTest(int id) {
		Test exam = null;
		String query = "SELECT * FROM Test WHERE Id =" + id + ";";
		ResultSet result = Connection.ExecQuery(query);
		try {
                        result.next();
			int Id = result.getInt("Id");
			String classId = result.getString("classId");
			Date startDate = result.getDate("startDate");
			Date endDate =  result.getDate("endDate");
			int duration = result.getInt("duration");
			int instructorId = result.getInt("instructorId");
			boolean approved = result.getBoolean("approved");
			exam = new Test(Id, classId,startDate,endDate,duration,instructorId,approved);
                        Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, "Test " + id + " recieved.");
		}catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
		return exam;	
	}

}
