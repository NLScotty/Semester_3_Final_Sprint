const express =require('express');
const grantController = require('../controller/grant-controllers');
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

myEmitter.on('search', (user) => {
    const date = new Date();
    if(DEBUG) console.log(`Search Event from ${user} at ${date}`);
    if(!fs.existsSync(path.join('./', 'logs'))) {
      fs.mkdirSync(path.join('./', 'logs'));
    }
    if(!fs.existsSync(path.join('./logs', 'search-logs'))) {
      fs.mkdirSync(path.join('./logs', 'search-logs'));
    }
    fs.appendFile(path.join('./logs', 'search-logs', date.getDay()+'-'+date.getMonth()+'-'+date.getFullYear()+'-search.log'), `${user} queried the data base at ${date}\n`, (error) => {
      if(error) throw error;
    });
});

myEmitter.on('logout', (email) => {
    const date = new Date();
    if(DEBUG) console.log(`${email} logout attempt at ${date}`);
    if(!fs.existsSync(path.join('./', 'logs'))) {
      fs.mkdirSync(path.join('./', 'logs'));
    }
    if(!fs.existsSync(path.join('./logs', 'login-logs'))) {
      fs.mkdirSync(path.join('./logs', 'login-logs'));
    }
    fs.appendFile(path.join('./logs', 'login-logs', date.getDay()+'-'+date.getMonth()+'-'+date.getFullYear()+'-login.log'), `${email} has logged out on the ${date}\n`, (error) => {
        if(error) throw error;
    });
});

function checkAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
        return next()
    }
    else{
        res.redirect('/login')
    }
}

router.use(checkAuthenticated)

router.get('/search', (request, response) =>{
    response.render('searchpage', {fname: request.user.fname, lname: request.user.lname})
    myEmitter.emit('route','GET /search') 
});

router.post('/search', async (request, response) => {
    myEmitter.emit('search',request.user.email)
    try{
        let result = null;
        if(request.body.search == 'amount'){
            if(request.body.amount_option == 'less'){
                result = await grantController.amountLower(request.body.database,request.body.input.toUpperCase());
            }
            else if(request.body.amount_option == 'more'){
                result = await grantController.amountHigher(request.body.database,request.body.input.toUpperCase());
            }
            else if(request.body.amount_option == 'equal'){
                result = await grantController.amountEqual(request.body.database,request.body.input.toUpperCase());
            }
        }
        else if(request.body.search == 'name'){
            if(request.body.name_option == 'containing'){
                result = await grantController.supplierContains(request.body.database,request.body.input.toUpperCase());
            }
            else if(request.body.name_option == 'exact'){
                result = await grantController.exactMatch(request.body.database,request.body.input.toUpperCase());
            }
        }
        if(DEBUG) console.log(result);
        response.render('resultpage', {grants : result});
        myEmitter.emit('route','POST /search') 
    } catch (err){
        console.log(err.message);
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.delete('/logout', (req, res, next) => {
    if(DEBUG) console.log("logout route");
    myEmitter.emit('logout',req.user.email) 
    req.logOut((err) => {
      if (err) {
        return next(err);
      }
      res.redirect('/login');
    });
});

module.exports = router;