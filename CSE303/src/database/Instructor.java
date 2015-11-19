package database;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Instructor {
	int id;
	String name;
	String email;
	
	public Instructor(int id, String name, String email){
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public Appointment[] viewAppointments(Test exam){
		return Appointment.instructorGetAppointments(exam);
	}
	
	public void requestTest(int Id, String classID, Date startDate, Date endDate, int duration){
		String Query = "Insert into Test (Id, classId,startDate,endDate,duration,InstructorId,Approved) VALUES ("+Id+",'"+classID+"','"+startDate+"','"+endDate+"',"+duration+","+this.id+",false);";
		if(isValidTest()){
			Connection.ExecUpdateQuery(Query);
			Logger.getLogger(Instructor.class.getName()).log(Level.SEVERE, ": Insert into Test success");
		}else{
			Logger.getLogger(Instructor.class.getName()).log(Level.SEVERE,": Insert into Test failed");
		}
	}
	
	
	//needs to be implemented
	private boolean isValidTest(){
		return true;
	}
	
	public void cancelTestRequest(int id){
		String Query = "DELETE FROM Test WHERE Id ="+id +";";
		if(Connection.ExecUpdateQuery(Query) != 0)
                    Logger.getLogger(Instructor.class.getName()).log(Level.SEVERE, "Could not delete exam " + id + " from Instructor ");
                else Logger.getLogger(Instructor.class.getName()).log(Level.SEVERE, "Successfully deleted exam " + id);
	}
}
