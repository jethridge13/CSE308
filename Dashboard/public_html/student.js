$(document).ready(function(){
    $("#calendar")[0].addEventListener("day_click", function(e){
        var element = e.element;
        
        if (element.hasEvent) {
            // CLEAR MODAL BODY OF PREVIOUS CONTENT
            var view_exam_body = $("#view_exam_body");
            
            view_exam_body[0].innerHTML = "";
            
            // INITIALIZE MODAL BODY WITH EXAM INFO
            
            // SHOW MODAL
            $("#view_exam").modal("show");
        }
    });
});