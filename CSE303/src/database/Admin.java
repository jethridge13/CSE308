package database;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Admin {
	
	
	//APPOINTMENT SECTION FOR ADMINS
	
	//Remove Appointment
	public static void removeAppointment(Student stud, Test exam){
		Appointment.removeAppointment(stud, exam);
	}
	
	//Returns All Appointments
	public Appointment[] getAllAppointments(Test[] exam){
		Appointment[] all = null;
		Collection<Appointment> collection = new ArrayList<Appointment>();
		for (Test test : exam) {
			Appointment[] holder = all.clone();
			all = Appointment.instructorGetAppointments(test);
			collection.addAll(Arrays.asList(holder));
			collection.addAll(Arrays.asList(all));
			all = (Appointment[]) collection.toArray();
		}
		return all;
	}
	
	//Edit/Adds Appointments
	public static void addEditAppointment(int seatNumber, Date start, Date end,
			Test exam, Student stud) {
		Appointment.addAppointment(seatNumber, start, end, exam, stud);
	}
	//APPOINTMENTS SECTION FOR ADMIN ENDS
	
	public static void ApproveDenyTest(boolean state, Test exam){
		String query = "Update Test Set Approved=" +state + " WHERE Id="+ exam.getId()+";";
		Connection.ExecQuery(query);
	}
	
	
	
}
