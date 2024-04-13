const bcrypt = require('bcrypt')
const LocalStrategy = require("passport-local").Strategy

const path = require('path');
const EventEmitter = require('events');
const fs = require('fs');

const myEmitter = new EventEmitter();

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


function initialize(passport, getUserByEmail, getUserById){
    const authenticateUser = async (email, password, done) =>{
        const user = await getUserByEmail(email)
        console.log(user)
        if(user == null){
            myEmitter.emit('login', email, false)
            return done(null, false, {message: 'Invalid login, please try again!'})
        }

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
    passport.use(new LocalStrategy({usernameField: 'email'}, authenticateUser))
    passport.serializeUser((user, done) => done(null, user.id))
    passport.deserializeUser(async (id, done) => {
        const user = await getUserById(id);
        done(null, user);
    })
}

module.exports = initialize