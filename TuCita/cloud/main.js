
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:


Parse.Cloud.afterSave("Review", function(request) {

var venue = request.object.get("venue");

var review = new Parse.Object.extend("Review");

var queryReview = new Parse.Query(review);

queryReview.equalTo("venue", venue);

queryReview.find({
                   success: function(results) {

var sum = 0;

 for(var i = 0; i < results.length; i++){

 sum = sum + results[i].get("rating");

 }

var rating = sum / results.length;

venue.set("rating", rating);

venue.save(null, {
            success: function(venue) {
              // Execute any logic that should take place after the object is saved.
              alert('New object created with objectId: ' + venue.id);
            },
            error: function(venue, error) {
              // Execute any logic that should take place if the save fails.
              // error is a Parse.Error with an error code and message.
              alert('Failed to create new object, with error code: ' + error.message);
            }
          });


                   }
                 });


});
