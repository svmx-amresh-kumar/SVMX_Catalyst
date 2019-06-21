cordova.define("phonegap-plugin-LaunchExternalApp.LaunchExternalApp", function(require, exports, module) {
var exec = require("cordova/exec");
var LaunchExternalApp = function() { };

LaunchExternalApp.prototype.start = function(params, success, fail) {
    success = success ? success : function() {};
    fail = fail ? fail : function() {};
    exec(success, fail, 'LaunchExternalApp', 'launchExternalApp', [params.url,params.param]);
};

var launchExternalApp = new LaunchExternalApp();
module.exports = launchExternalApp;

});
