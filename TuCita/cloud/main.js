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

Parse.Cloud.afterSave("Venue", function(request) {

var venue = request.object;

var slots = venue.relation("slots");

var openingHours = venue.relation("openingHours");

slots.query().find({

  success: function(slotList) {

        openingHours.query().find({

            success: function(openingHoursList) {

                if(slotList.length > 0 && openingHoursList.length > 0) {

                var duration = slotList[0].get("durationMinutes");

                Parse.Object.destroyAll(slotList);

                    var Slot = Parse.Object.extend("Slot");

                    var day = "day";

                         venue.fetch({

                    success: function(myObject) {

                    var mySlots = [];

                    for(var i = 0; i < openingHoursList.length; i++) {

                          var startHour = openingHoursList[i].get("startHour");

                          var startMinute = openingHoursList[i].get("startMinute");

                          var endHour = openingHoursList[i].get("endHour");

                          var endMinute = openingHoursList[i].get("endMinute");

                              while(startHour < endHour || (startHour == endHour && startMinute <= endMinute)) {

                                                        var slot = new Slot();

                                                        slot.set(day, openingHoursList[i].get(day));

                                                        slot.set("durationMinutes", duration);

                                                        slot.set("startHour", startHour);

                                                        slot.set("startMinute", startMinute);

                                                        slot.set("amount", 1);

                                                        mySlots.push(slot);

                                                        var minutes = startMinute + duration;

                                                        var hours = startHour;

                                                        if(minutes >= 60) {

                                                        hours += minutes / 60;

                                                        minutes = minutes % 60;

                                                        }

                                                        startHour = hours;

                                                        startMinute = minutes;

                                                    }
                           }

                          Parse.Object.saveAll(mySlots, {

                          success: function(mySlots) {

                            slots.add(mySlots);

                            venue.save(null, {

                            success: function(venue) {

                            alert('object updated with objectId: ' + venue.id);

                            },

                            error: function(venue, error) {

                            alert('Failed to update object, with error code: ' + error.message);

                            }

                          });
                           },
                           error: function(myObject, error) {
                             // The object was not refreshed successfully.
                             // error is a Parse.Error with an error code and message.
                           }
                         });

                    }

                }

        );

  }

} });
}});
});

Parse.Cloud.beforeSave(Parse.User, function(request, response) {

var user = new Parse.Object.extend("User");

var queryUser = new Parse.Query(user);

queryUser.equalTo("email", request.object.get("email"));

    queryUser.find( {

        success : function(results) {

            if(results.length != 0){

                if(results[0].id == request.object.id){

                    response.success();

                }else {

                response.error("email exists" + results[0].id +" "+ request.object.id);

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
