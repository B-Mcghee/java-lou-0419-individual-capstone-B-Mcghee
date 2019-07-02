

$(".power").click(function(){

    var message = $(this).text();
    $("#message-input").val(message).css('color','black').css('font-weight','normal');


    $.get({
        url: 'http://localhost:8080/HeroSighting/superpower/display/id/9',
        success: function(heroes, status, xhr){
            console.log(status);
        },
        error: function(){
           console.log("something went wrong");
        }
    });
})