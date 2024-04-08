/*
    This is the driver application/file of my project. Running this file through
    node will host the server that allows one to 
*/

// Node modules needed for this file
const express = require("express");

// Files that contain the different routes of my application
const apiRoutes = require('./routes/api-routes');
const uiRoutes = require('./routes/ui-routes');

// we set app to express so we can call the express functions
const app = express();
// we set a port number
const PORT =  3000;

// debug stuff
global.DEBUG = true;
if(DEBUG){
    app.use((request, response, next) => {
        console.log(request.method+", "+request.url)
        next()
    })
}

// we set our view enginge, what kind of data we are expecting from our requests,
// and a folder for our public resources (images, style css.)
app.set('view engine', 'ejs');
app.use(express.urlencoded({extended:true}));
app.use(express.static('public'));

// I first have it check to see if the url is an api request. If so, check the api routes.
app.use('/api', apiRoutes);
// I then check the website/ui routes
app.use('/', uiRoutes)
// If no route is found, give a 404
app.use((request, response) => {
    if(DEBUG) console.log('404 - route not found.');
    response.status(404).write('404 - route not found.');
    response.end();
}) 

app.listen(PORT, () => {
    console.log(`Simple app running on port ${PORT}.`)
});