
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.job("lockReview", function(request, status) {

var slot = new Parse.Object.extend("Slot");

var query = new Parse.Query(slot);

Parse.Cloud.useMasterKey();

query.find().then(function(results) {

        for(var i = 0; i < results.length; i++){

         var acl = new Parse.ACL();

         acl.setPublicReadAccess(true);

         acl.setPublicWriteAccess(true);

        results[i].setACL(acl);

        results[i].save();
        }

    }

 ).then(function() {

 status.success( "Slots processed.");

 });

});
