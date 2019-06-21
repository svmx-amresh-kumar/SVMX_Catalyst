cordova.define("uk.co.workingedge.phonegap.plugin.istablet.IsTablet", function(require, exports, module) {
/*
   Copyright 2015 Dave Alden - http://www.workingedge.co.uk/dave/

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

(function() {
    var exec = require('cordova/exec');
    (function(){
        cordova.exec(function(result){
            window.isTablet = !!result;
        }, function(){
            console.error("Error calling IsTablet plugin");
        }, 'IsTablet', 'isTabletDevice', []);
    })();
})();


});
