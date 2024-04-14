global.DEBUG = true;
/*
    This is the driver application/file of my project. Running this file through
    node will host the server that allows one to 
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
app.use(methodOverride('_method'))
// we set a port number
const PORT =  3000;





// debug stuff

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

app.use('/test', testRoutes);
 
app.use('/user', userRoutes);
 
app.use('/', publicRoutes)

// If no route is found, give a 404
app.use((request, response) => {
    if(DEBUG) console.log('404 - route not found.');
    response.status(404).write('404 - route not found.');
    response.end();
}) 

app.listen(PORT, () => {
    console.log(`Simple app running on port ${PORT}.`)
});