

$(".headingTitle").hover(function(){
    var number = Math.floor((Math.random() * 12) + 1);
    $('#homeBody').css("background-image", "url(../img/SuperHero"+number+".jpg)");

});


$(".headingTitle").mouseover(function(){
    $(this).find(".caret").show();
});

$(".editing").hide();

$(".location-title").click(function(){
   $(this).find(".editing").show();

});




$(".super").click(function(){
       $("#icons").html("");
    var message = $(this).text();
    $("#addPower").val(message).css('color','#fff').css('font-weight','normal');

      var id = $(this).attr("value");

    $.get({
        url: '/HeroSighting/superpower/get/id/' + id,
        success: function(heroes, status, xhr){
                var description = "Heroes with this ability:"
              var power = createName(description);

               $("#icons").append(power);
            for(var i = 0; i < heroes.length;i++){


                var icon = createIcons(heroes[i]);


                $("#icons").append(icon);
            }


        },
        error: function(){
           console.log("something went wrong");
        }
    });
})

function createIcons(hero){

    var outerDiv = $("<div>");
    outerDiv.attr({
        "class": "icon-slots col-lg-4 col-sm-5 col-md 5"
    });
    var htag = $("<h3>");
    htag.attr({"class": "text-lg-center"});
    htag.html(hero.name);
    outerDiv.append(htag);



    return outerDiv;
}
function createName(message){
    //       <h2 class="col-12">View a Super Power</h2>
        var outerHtag = $("<h2>");
        outerHtag.attr({
            "class":"col-12 abilities"
        });
        outerHtag.html(message);
        return outerHtag;
}






//function initMap() {
//  // The location of Uluru
//  var location = {lat: 39.1809, lng: 86.5256};
//  // The map, centered at Uluru
//  var map = new google.maps.Map(
//      document.getElementById('map'), {zoom: 4, center: location});
//  // The marker, positioned at Uluru
//  var marker = new google.maps.Marker({position: location, map: map});
//}

//function initMap() {
//  // The location of Uluru
//  var uluru = {lat: -25.344, lng: 131.036};
//  // The map, centered at Uluru
//  var map = new google.maps.Map(
//      document.getElementById('map'), {zoom: 4, center: uluru});
//  // The marker, positioned at Uluru
//  var marker = new google.maps.Marker({position: uluru, map: map});
//}

//




