global.DEBUG = false;
/*
This is the driver application/file of my project. Running this file through
node will host the server.
*/

// Node modules needed for this file
require('dotenv').config()
const express = require("express");
const bcrypt = require('bcrypt')
const flash = require('express-flash')
const session = require('express-session')
const methodOverride = require('method-override')
const passport = require('passport');

// Files, routes, and functions that are needed to run app.js
const publicRoutes = require('./routes/public-routes');
const userRoutes = require('./routes/user-routes');
const testRoutes = require('./routes/test-routes');
const initializePassport = require('./passport-config');
const { getUserByEmail, getUserById } = require('./services/sql-user-dal');

// I initialze the setting of passport and express here.
// Flash is used for logging out.
// Session is an express library that is used by passport in this application
// to store session data, such as the user
initializePassport(passport, getUserByEmail, getUserById )
const app = express();
app.use(flash())
app.use(session({
  secret: process.env.SESSION_SECRET,
  resave: false,
  saveUninitialized: false
}))
app.use(passport.initialize())
app.use(passport.session())
// Method overide is used for logging out. It allows my appliation to handle more than just
// GET and POST requests through the html user interface
app.use(methodOverride('_method'))

// we set a port number to be the portnumber in the .env file
const PORT =  process.env.PORT_NUMBER;

// More setting for express
// we set our view enginge, what kind of data we are expecting from our requests,
// and a folder for our public resources (images, style css.)

app.set('view engine', 'ejs');
app.use(express.urlencoded({extended:true}));
app.use(express.static('public'));


// debug stuff
if(DEBUG){
    app.use((request, response, next) => {
        console.log(request.method+", "+request.url)
        next()
    })
}

// test routes
if(DEBUG) app.use('/test', testRoutes);

//I segregate routes into public/guest routes, and user routes.

app.use('/user', userRoutes); 
app.use('/', publicRoutes)

// If no route is found, give a 404
app.use((request, response) => {
    if(DEBUG) console.log('404 - route not found.');
    response.status(404).write('404 - route not found.');
    response.end();
}) 

//the three lines of code that sets up the server
app.listen(PORT, () => {
    console.log(`Simple app running on port ${PORT}.`)
});