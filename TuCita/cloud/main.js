Parse.Cloud.afterSave("Review", function(request) {

var venue = request.object.get("venue");

var review = new Parse.Object.extend("Review");

var queryReview = new Parse.Query(review);

queryReview.equalTo("venue", venue);

    queryReview.find( {

        success : function(results) {

        var sum = 0;

          var reviewsCount = results.length;

            for(var i = 0; i < reviewsCount; i ++){

            sum = sum + results[i].get("rating");

            }

        var rating = sum / results.length;

        venue.set("reviewsCount", reviewsCount);

        venue.set("rating", rating);

                venue.save(null, {

                    success: function(venue) {

                    alert('object updated with objectId: ' + venue.id);

                    },

                    error: function(venue, error) {

                    alert('Failed to update object, with error code: ' + error.message);

                    }

                });

        }

    });

});

Parse.Cloud.beforeSave(Parse.User, function(request, response) {

var user = new Parse.Object.extend("User");

var queryUser = new Parse.Query(user);

queryUser.equalTo("email", request.object.get("email"));

    queryUser.find( {

        success : function(results) {

            if(results.length != 0){

                if(results[0].id == request.object.get("objectId")){

                    response.success();

                }else {

                response.error("email exists" + results[0].id +" "+ request.object.get("objectId"));

                }

            }else{

                response.success();

                }

        },

        error: function(venue, error) {

        alert('Failed to find objects, with error code: ' + error.message);

        }

    });

});
