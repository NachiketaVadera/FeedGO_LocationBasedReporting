"use strict";
const functions = require('firebase-functions');
const {
  dialogflow,Permission
} = require("actions-on-google");
const app = dialogflow();

app.intent('Default Welcome Intent', conv => {
    conv.ask(
        new Permission({
          context: "for reporting please allow me to know your location",
          permissions: ["NAME","DEVICE_PRECISE_LOCATION"]
        })
    );
});

app.intent("actions_intent_PERMISSION", (conv, params, granted) => {
    if (!granted) {
      conv.close("No worries, Thank you for your time");
    //   conv.ask(new Suggestions("Blue", "Red", "Green"));
    } else {
				const {location} =conv.device; 
				const {name} = conv.user;
      	// conv.data.userName = conv.user.name.display;
				// conv.ask(`Thanks, I see you're at ${location.formattedAddress}. What would like to report?`);
				conv.ask(`Okay ${name.display}, I see you're at ` +
      `${location.formattedAddress} lat=${location.coordinates.latitude} lng=${location.coordinates.longitude} What would you like to report?`);
    }
  });

app.intent('AddReport', conv => {
	conv.ask(`Got it! Reporting ${conv.body.queryResult.parameters["danger"]}....`);
	conv.ask(`Thanks for reporting it to us!`);
})
exports.dialogflowFirebaseFulfillment = functions.https.onRequest(app);