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
            document.write(data.coord.lon+"</br>");
            document.write(data.weather[0].description+"</br>");
            document.write(data.main.temp+"</br>");
            document.write(data.main.temp+"</br>");
            document.write(data.main.pressure+"</br>");
            document.write(data.main.humidity+"</br>");
            document.write(data.main.temp_min+"</br>");
            document.write(data.main.temp_max+"</br>");
            document.write(data.visibility+"</br>");
            document.write(data.wind.speed+"</br>");
            document.write(data.wind.deg+"</br>");
            document.write(data.clouds.all+"</br>");


            // console.log(data.main.temp);
            // console.log(data.pressure);


        }
    });
}
