const express = require('express');
const sqlUserDal = require('../services/sql-user-dal') // PostgreSQL DAL for Users
const bcrypt = require('bcrypt');
const passport = require('passport');
const path = require('path');
const EventEmitter = require('events');
const fs = require('fs');

const router = express.Router();
const myEmitter = new EventEmitter();

// logging code
myEmitter.on('route', (url) => {
    const date = new Date();
    if(DEBUG) console.log(`Route Event on: ${url} at ${date}`);
    if(!fs.existsSync(path.join('./', 'logs'))) {
      fs.mkdirSync(path.join('./', 'logs'));
    }
    if(!fs.existsSync(path.join('./logs', 'route-logs'))) {
      fs.mkdirSync(path.join('./logs', 'route-logs'));
    }
    fs.appendFile(path.join('./logs', 'route-logs', date.getDay()+'-'+date.getMonth()+'-'+date.getFullYear()+'-route.log'), `Route Event on: ${url} at ${date}\n`, (error) => {
      if(error) throw error;
    });
});

myEmitter.on('update', (email) => {
  const date = new Date();
  if(DEBUG) console.log(`Account Creation with ${email} at ${date}`);
  if(!fs.existsSync(path.join('./', 'logs'))) {
    fs.mkdirSync(path.join('./', 'logs'));
  }
  if(!fs.existsSync(path.join('./logs', 'account-logs'))) {
    fs.mkdirSync(path.join('./logs', 'account-logs'));
  }
  fs.appendFile(path.join('./logs', 'account-logs', date.getDay()+'-'+date.getMonth()+'-'+date.getFullYear()+'-account.log'), `New account created using: ${email} on the ${date}\n`, (error) => {
    if(error) throw error;
  });
});

function checkNotAuthenticated(req, res, next) {
  if (req.isAuthenticated()) {
      res.redirect('/user/search')
  }else{
      next()
  }
}

router.use(checkNotAuthenticated)

router.get('/', (request, response) => {
  response.redirect('/login'); 
})

router.get('/login', (request, response) => {
    response.render('login'); 
    myEmitter.emit('route','/login')   
})

router.post('/login', passport.authenticate('local',{
    successRedirect: '/user/search',
    failureRedirect: '/login',
    failureFlash: true,
}))

router.get('/register', (request, response) => {
    response.render('register'); 
    myEmitter.emit('route','/register') 
})

router.post('/register', async (request, response) => {
    try {
      const hashedPassword = await bcrypt.hash(request.body.password, 10)
      let result = await sqlUserDal.addUser(request.body.fname, request.body.lname, request.body.email, hashedPassword)
      if(result === '23505'){
        response.render('register', {message : "Account with that email already exists!"})
      }else{
        response.render('accountcreated')
        myEmitter.emit('update',request.body.email)
      }
    } catch {
      response.render('register', {message : "Unkown error has occured!"})
    }
})

module.exports = router;