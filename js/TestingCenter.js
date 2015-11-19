var studentURL = "./Student.html";
var instructorURL = "./Instructor.html";
var adminURL = "./Admin.html";

window.onload = function() {
    $("#loginFailAlert").hide();
};

function auth() {
    console.log("Login pressed");
    var netID = document.getElementsByName("NetID")[0].value;
    var password = document.getElementsByName("password")[0].value;
    console.log(netID + " " + password);
    //alert("Login: " + netID + "\nPassword: " + password);
    testLoadPage(netID);
}
;

function testLoadPage(netID) {
    switch (netID) {
        case 'Student':
            displayStudentPage();
            break;
        case 'Instructor':
            displayInstructorPage();
            break;
        case 'Admin':
            displayAdminPage();
            break;
        default:
            $("#loginFailAlert").show();
            break;
    }
}
;

function displayStudentPage() {
    console.log("Student page loaded");
    window.location = studentURL;
}
;

function displayInstructorPage() {
    window.location = instructorURL;
    console.log("Instructor page loaded");
}
;

function displayAdminPage() {
    window.locatoin = adminURL;
    console.log("Admin page loaded");
}
;

