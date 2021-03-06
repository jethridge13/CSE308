$(document).ready(function(){
    $("#create_exam_date_begin").datepicker();
    $("#create_exam_date_end").datepicker();
    
    $("#create_exam_date_begin, #create_exam_date_end, #create_exam_section").change(function(){
        if ($("#create_exam_date_begin_input").val().length > 0 &&
            $("#create_exam_date_end_input").val().length > 0 &&
            $("#create_exam_section_input").val().length > 0) {
            $("#create_exam_submit").prop("disabled", false);
        } else {
            $("#create_exam_submit").prop("disabled", true);
        }
    });
    
    var submitting = false;
    
    $("#create_exam_submit").click(function(){
        // SUBMIT DETAILS TO SERVER
        var date_begin = $("#create_exam_date_begin_input").val();
        var date_end = $("#create_exam_date_end_input").val();
        var section = $("#create_exam_section_input").val();
        
        submitting = true;
        
        $("#create_exam_submit").prop("disabled", true);
        $("#create_exam_submit").prop("class", "btn btn-default btn-warning");
        $("#create_exam_submit").html("<span id='create_exam_loading_icon' class='glyphicon glyphicon-refresh glyphicon-refresh-animate'></span> Submitting...");
    });
    
    $("#create_exam_close").click(function(){
        if (submitting) {
            $("#create_exam_submit").prop("class", "btn btn-default");
            $("#create_exam_submit").html("<span id='create_exam_loading_icon' class='glyphicon glyphicon-refresh'></span> Submit");
            
            submitting = false;
        }
        
        $("#create_exam_submit").prop("disabled", true);
        $("#create_exam_date_begin_input").val("");
        $("#create_exam_date_end_input").val("");
        $("#create_exam_section_input").val("");
    });
    
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