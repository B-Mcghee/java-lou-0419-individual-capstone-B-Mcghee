
//
//$(".headingTitle").mouseover(function(){
//    $(".caret").css("display","inline-block");
//});
//
//$(".headingTitle").mouseout(function(){
//    $(".caret").css("display","none");
//});
//
//$(".home-sighting").mouseover(function(){
//    $('#homeBody').css("background-image", "url(../img/SuperHero2.jpg)");
//});
//$(".home-sighting").mouseout(function(){
//    $('#homeBody').css("background-image", "url(../img/SuperHero3.jpg)");
//})

$(".headingTitle").hover(function(){
    var number = Math.floor((Math.random() * 12) + 1);
    $('#homeBody').css("background-image", "url(../img/SuperHero"+number+".jpg)");

});


//$(".caret").mouseover(function(){
//  $(".caret").css("background-color", "yellow");
//});
$(".editing").hide();

$(".location-title").hover(function(){
   $(this).find(".editing").show();

});




//function opacity(num){
//num = 0;
//for(i = 0; i <20; i++){
//        num+= 0.05;
// }
//return num
//}
$(".power").click(function(){
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
$('select[multiple]').multiselect({
    columns: 3,
    placeholder: 'Select options'
});





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
//<form>
//                    <h4 class="newPower">Edit Super Power</h4>
//                    <div class="input-group">
//
//                        <input type="text" class="form-control" id="addPower" placeholder="Description">
//                        <div class="input-group-append">
//                            <button class="btn btn-primary" type="button">Update</button>
//                        </div>
//                    </div>
//                </form>

//$("#delete").click(function(){
//        var id = $(this).attr("value");
//        $.get({
//                url: '/HeroSighting/superpower/delete/id/'+id,
//                success: function(heroes, status, xhr){
//                        console.log(heroes);
//                     },
//                     error: function( ){
//                        console.log("something went wrong");
//                     }
//            });
//    })

//$.get({
//        url: '/HeroSighting/editsuperpower/update,
//        success: function(heroes, status, xhr){
//                console.log(heroes);
//             },
//             error: function( ){
//                console.log("something went wrong");
//             }
//    });



