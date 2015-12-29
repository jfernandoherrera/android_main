
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
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
