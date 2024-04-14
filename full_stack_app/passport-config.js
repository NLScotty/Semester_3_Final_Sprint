const bcrypt = require('bcrypt')
const LocalStrategy = require("passport-local").Strategy

const path = require('path');
const EventEmitter = require('events');
const fs = require('fs');

const myEmitter = new EventEmitter();

// The "Settings" of passport. Here you may have more code if your login systems
// Accomodates for multiple login strategies.

// Used to log login attempts
myEmitter.on('login', (email, success) => {
    const date = new Date();
    if(DEBUG) console.log(`Login attempt with ${email} at ${date}`);
    if(!fs.existsSync(path.join('./', 'logs'))) {
      fs.mkdirSync(path.join('./', 'logs'));
    }
    if(!fs.existsSync(path.join('./logs', 'login-logs'))) {
      fs.mkdirSync(path.join('./logs', 'login-logs'));
    }
    if(success){
        fs.appendFile(path.join('./logs', 'login-logs', date.getDay()+'-'+date.getMonth()+'-'+date.getFullYear()+'-login.log'), `${email} successfully logged in on the ${date}\n`, (error) => {
            if(error) throw error;
        });
    }else{
        fs.appendFile(path.join('./logs', 'login-logs', date.getDay()+'-'+date.getMonth()+'-'+date.getFullYear()+'-login.log'), `Failed login attempt using ${email} on the ${date}\n`, (error) => {
            if(error) throw error;
        });
    }
});

// I setup the strategy and variables of passport here. 
function initialize(passport, getUserByEmail, getUserById){
    const authenticateUser = async (email, password, done) =>{
        // I fetch user by email
        const user = await getUserByEmail(email)
        if(DEBUG) console.log(user)
        //If user does not exist, I return a failed login attempt
        if(user == null){
            myEmitter.emit('login', email, false)
            return done(null, false, {message: 'Invalid login, please try again!'})
        }
        // Else, I compare passwords, only creating a session if passwords match
        try{
            if(await bcrypt.compare(password, user.password)){
                myEmitter.emit('login', email, true)
                return done(null, user)
            }else{
                myEmitter.emit('login', email, false)
                return done(null, false, {message: 'Invalid login, please try again!'})
            }
        } catch (e) {
            return done(e)
        }
    }
    // I set the strategy of my passport to be local. This compares the information
    // to what I have set up localy, which in this case would be the database.
    // Used by passport to handle authorization
    passport.use(new LocalStrategy({usernameField: 'email'}, authenticateUser))
    // Code for serializeing and deserializeing users.
    // Used by passport to handle authorization
    passport.serializeUser((user, done) => done(null, user.id))
    passport.deserializeUser(async (id, done) => {
        const user = await getUserById(id);
        done(null, user);
    })
}

module.exports = initialize