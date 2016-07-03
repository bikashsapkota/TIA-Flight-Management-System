var tableCreated=false;
var app = {
    // Application Constructor

    initialize: function() {
        document.addEventListener('deviceready', function () {
            // Enable to debug issues.
            // window.plugins.OneSignal.setLogLevel({logLevel: 4, visualLevel: 4});

            var notificationOpenedCallback = function(jsonData) {
                alert("notification");
                //console.log('didReceiveRemoteNotificationCallBack: ' + JSON.stringify(jsonData));
            };

            window.plugins.OneSignal.init("59d9c28f-1991-414e-8d2e-fc794c941731",
                {googleProjectNumber: "385837487700"},
                notificationOpenedCallback);

            // Show an alert box if a notification comes in when the user is in your app.
            window.plugins.OneSignal.enableInAppAlertNotification(true);
        }, false);
        window.plugins.OneSignal.setSubscription(true);
        window.plugins.OneSignal.enableNotificationsWhenActive(true);
    }
};
function setLocal() {
    var flight = document.getElementById("planeNo").value;

    localStorage.setItem('flight',flight);
    var flight= localStorage.getItem('flight');

}

function getWeather() {


    var place = "Kathmandu,Nepal";
    var token = "bf7711aff0285db6ecca0bd558f89301";
    var req = "q="+place+"&appid="+token;
    $.ajax({
        type: "GET",
        url: "http://api.openweathermap.org/data/2.5/weather",
        data: req,
        dataType: 'json',


        success: function(data){
            // document.write(data.coord.lon+"</br>");
            // document.write(data.weather[0].description+"</br>");
            // document.write(data.main.temp+"</br>");
            // document.write(data.main.temp+"</br>");
            // document.write(data.main.pressure+"</br>");
            // document.write(data.main.humidity+"</br>");
            // document.write(data.main.temp_min+"</br>");
            // document.write(data.main.temp_max+"</br>");
            // document.write(data.visibility+"</br>");
            // document.write(data.wind.speed+"</br>");
            // document.write(data.wind.deg+"</br>");
            // document.write(data.clouds.all+"</br>");
            //console.log(data);
            $("#weather").append("Wheater:"+data.weather[0].description+"</br>"+"Main Temperature"+data.main.temp+"</br>"+"Pressure:"+data.main.pressure+"</br>"+"Humidity:"+data.main.humidity+"</br>"+"Visibility:"+data.visibility+"</br>"+"Wind Speed"+data.wind.speed+"</br>"+"Wind Degree:"+data.wind.deg+"</br>"+"cloud Status:"+data.clouds.all+"</br>");
            // $("#weather").append("<table data-role='table' id='movie-table' data-mode='reflow' class='ui-responsive'><thead></thead></table>");
            // navigator.notification.alert(message, alertCallback, "test", "test")




        }
    });
    console.log("weather finished");
}

function getplaneDetails() {

    //table_name=int_arrivals
    //console.log(type);
    var place = "Kathmandu,Nepal";
    //$("#flightDetails").append("");

    //$("#flightDetails").append("");
    var req = "table_name="+"int_arrivals";


    $.ajax({
        type: "GET",
        url: "https://raw.githubusercontent.com/bikashsapkota/deerthon/master/json",
        dataType: 'json',


        success: function(data){
            //alert(JSON.stringify(data));
             $.each(data, function(index, element) {
                    str = "<tr><td>"+element.flight+"</td><td>"+element.eta+"</td><td>"+element.sta+"</td><td>"+element.origin+"</td><td></tr>";
                   $("#t1").append(str);
                 console.log(str);


                });
        },error:function (er) {

            alert(JSON.stringify(er));

        }
    });
}