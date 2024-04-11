const express = require('express');
const sqlUserDal = require('../services/sql-user-dal') // PostgreSQL DAL for Users
const bcrypt = require('bcrypt')

const router = express.Router();

router.get('/login', (request, response) => {
    response.render('login');    
})

router.get('/register', (request, response) => {
    response.render('register'); 
})

router.post('/register', async (request, response) => {
    try {
      const hashedPassword = await bcrypt.hash(request.body.password, 10)
      /*
      users.push({
        id: Date.now().toString(),
        name: req.body.name,
        email: req.body.email,
        password: hashedPassword
      })
      */
      let result = await sqlUserDal.addUser(request.body.fname, request.body.lname, request.body.email, hashedPassword)
      response.redirect('/login')
    } catch {
      response.redirect('/register')
    }
})

module.exports = router;