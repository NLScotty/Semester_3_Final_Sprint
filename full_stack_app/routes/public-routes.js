const express = require('express');
const sqlUserDal = require('../services/sql-user-dal') // PostgreSQL DAL for Users
const bcrypt = require('bcrypt');
const passport = require('passport');

const router = express.Router();

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
})

router.post('/login', passport.authenticate('local',{
    successRedirect: '/user/search',
    failureRedirect: '/login',
    failureFlash: true
}))

router.get('/register', (request, response) => {
    response.render('register'); 
})

router.post('/register', async (request, response) => {
    try {
      const hashedPassword = await bcrypt.hash(request.body.password, 10)
      let result = await sqlUserDal.addUser(request.body.fname, request.body.lname, request.body.email, hashedPassword)
      response.redirect('/login')
    } catch {
      response.redirect('/register')
    }
})

module.exports = router;