
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:


Parse.Cloud.beforeSave("Appointment", function(request, response) {


var appointment = new Parse.Object.extend("Appointment");

var queryAppointment = new Parse.Query(appointment);

queryAppointment.greaterThan("date", request.object.get("date"));

var tomorrow = request.object.get("date");

tomorrow.setDate(tomorrow.getDate() + 1);

queryAppointment.lessThan("date", tomorrow);

queryAppointment.find().then(function(results) {

var slot = new Parse.Object.extend("Slot");

var querySlot = new Parse.Query(slot);

querySlot.equalTo("startHour", request.object.get("date").getHours());

querySlot.lessThan("startMinute", request.object.get("date").getMinutes());






}
                                      if (request.object.get("stars") < 1) {
                                        response.error("you cannot give less than one star");
                                      } else if (request.object.get("stars") > 5) {
                                        response.error("you cannot give more than five stars");
                                      } else {
                                        response.success();
                                      }
                                    });

Parse.Cloud.job("lockReview", function(request, status) {

function addMinutes(date, minutes) {

    return new Date(date.getTime() + minutes*60000);
}

var slot = new Parse.Object.extend("Slot");

var query = new Parse.Query(slot);

Parse.Cloud.useMasterKey();

query.find().then(function(results) {

         var acl = new Parse.ACL();

         acl.setPublicReadAccess(true);

         acl.setPublicWriteAccess(true);

        for(var i = 0; i < results.length; i++){

        var isPublic = results[i].getACL().getPublicReadAccess();

        var date = results[i].updatedAt;

        date = addMinutes(date, 5)

        var currentDate = new Date();

            if(! isPublic && currentDate > date){

            results[i].setACL(acl);

            results[i].save();

            console.log("the slot: " + results[i].id + " has been unlocked")
            }
        }

    }

 ).then(function() {

 status.success("Slots processed.");

 });

});
