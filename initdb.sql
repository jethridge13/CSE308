create database TestingCenter;

use TestingCenter;

create table Student (
	Id int,
    Name char(40),
    Email char(60),
    
    primary key(Id),
    unique(Email)
    );

create table Instructor (
	Id int,
    Name char(40),
    Email char(60),
    
    primary key(Id),
    unique(Email)
    );
    
create table Administrator (
	Id int,
    Name char(40),
    Email char(60),
    
    primary key(Id),
    unique(Email)
    );
    
create table StudentLogin (
	Id int,
    Password char(30),
    
    primary key(Id),
    foreign key(Id) references Student(Id)
		on delete cascade
    );

create table InstructorLogin (
	Id int,
    Password char(30),
    
    primary key(Id),
    foreign key(Id) references Instructor(Id)
		on delete cascade
    );
    
create table AdministratorLogin (
	Id int,
    Password char(30),
    
    primary key(Id),
    foreign key(Id) references Administrator(Id)
		on delete cascade
    );
    
create table Test (
	Id int,
    classId char(6), #course identifier
    startDate DateTime,
    endDate DateTime,
    duration int, #how long the test takes in minutes
    InstructorId int, #Instructor that created the test
	Approved boolean,
    
    primary key(Id),
    foreign key(InstructorId) references Instructor(Id)
		on delete set null
	);

    
DELIMITER $$
CREATE TRIGGER UnschedulableTest BEFORE INSERT ON TestingCenter
FOR EACH ROW
BEGIN
IF( (SELECT * FROM TestingCenter) )
THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Can\'t schedule test.';
END IF;
END;
$$ DELIMITER ;

#Table relating students to the tests they have to take.
create table StudentTests (
	TestId int,
    StudentId int,
    
    primary key(TestId,StudentId),
    foreign key(TestId) references Test(Id)
		on delete cascade,
    foreign key(StudentId) references Student(Id)
		on delete cascade
    );
    
    


create table Appointment (
	Seat int,
    StartDate DateTime, #date and time of the start of the appointment
    EndDate DateTime, #date and time of the end of the appointment
    StudentId int,
    TestId int,
    Attendence boolean,
    
    primary key(StudentId, TestId),
    foreign key(StudentId) references Student(Id)
		on delete cascade,
	foreign key(TestId) references Test(Id)
		on delete cascade,
	unique (Seat, StartDate),
	unique(Seat, EndDate)
	);
    
#Trigger to avoid inserting an appointment with a conflict and send an error message.
DELIMITER $$
CREATE TRIGGER AppointmentConflict BEFORE INSERT ON Appointment
FOR EACH ROW
BEGIN
IF( 1 > (SELECT COUNT(*) FROM StudentTests T WHERE T.StudentId = NEW.StudentId) ) #Cannot have appointment if not in course/on ad hoc list
	THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Not on the list for the test.';
END IF;
IF( 0 < (SELECT COUNT(*) FROM Appointment A WHERE A.StudentId = NEW.StudentId AND A.TestId = NEW.TestId) ) #Cannot have 2 appointments for the same test
	THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Already has Appointment.';
END IF;
IF( 0 <(SELECT COUNT(*) FROM Appointment A WHERE A.StudentId = NEW.StudentId AND A.StartDate < NEW.EndDate AND A.EndDate > NEW.StartDate)) #Can't have appointments with overlapping times
	THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Overlapping Test times.';
END IF;
IF( 1 > (SELECT Seats - setAsideSeats FROM TestingCenter) ) #Can't have appointment if there is no seats at that time.
	THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No Seats.'; 
END IF;
END;
$$ DELIMITER ;

#Trigger to avoid deleting an appointment within 24 hours of the start of appointment start.
DELIMITER $$
CREATE TRIGGER AppointmentLateCancel BEFORE DELETE ON Appointment
FOR EACH ROW
BEGIN
IF( OLD.StartDate > NOW() - INTERVAL 1 DAY)
	THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot cancel apponitment within 24 hours of appointment.';
END IF;
END;
$$ DELIMITER ;

    
    
create table TestingCenter (
	Id int, #placeholder Id. Only one testing center.
	Seats int,
    setAsideSeats int,
    GapTime int,
    ReminderInterval int,
    
    primary key(Id)
    );

#Trigger to prevent multiple testing centers

DELIMITER $$
CREATE TRIGGER NoSecondCenter BEFORE INSERT ON TestingCenter
FOR EACH ROW
BEGIN
IF(1 > (SELECT COUNT(*) FROM TestingCenter))
THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Already a Testing Center.';
END IF;
END;
$$ DELIMITER ;

#Trigger to prevent testing center being deleted

DELIMITER $$
CREATE TRIGGER KeepCenter BEFORE DELETE ON TestingCenter
FOR EACH ROW
BEGIN
IF( 1 <= (SELECT COUNT(*) FROM TestingCenter))
THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot Delete Center.';
END IF;
END;
$$ DELIMITER ;


# Hours for when the testing center opens in minutes.
create table OpenHours (
	StartInterval DateTime,
    EndInterval DateTime,
    MondayOpen int,
    MondayClose int,
    TuesdayOpen int,
    TuesdayClose int,
    WednesdayOpen int,
    WenesdayClose int,
    ThursdayOpen int,
    ThursdayClose int,
    FridayOpen int,
    FridayClose int,
    
    primary key (StartInterval),
    unique (EndInterval)
    );
    


