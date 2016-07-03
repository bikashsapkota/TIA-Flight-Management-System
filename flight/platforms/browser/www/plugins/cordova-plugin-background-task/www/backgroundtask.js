cordova.define("cordova-plugin-background-task.BackgroundTask", function(require, exports, module) { 
var exec = require("cordova/exec");

var BackgroundTask = function () {
    this.name = "BackgroundTask";
};

BackgroundTask.prototype.start = function (task) {
    exec(task, null, "BackgroundTask", "start", []);
};

module.exports = new BackgroundTask();

});
