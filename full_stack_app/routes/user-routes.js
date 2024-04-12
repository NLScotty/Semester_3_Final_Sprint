const express =require('express');

const router = express.Router();
const grantController = require('../controller/grant-controllers');

function checkAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
      return next()
    }
    res.redirect('/login')
}

router.use(checkAuthenticated)

router.get('/search', (request, response) =>{
    response.render('searchpage', {fname: request.user.fname})
    //response.render('searchpage')
});

router.post('/search', async (request, response) => {
    if(DEBUG) console.log("test route")
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
        console.log("THE RESULT");
        console.log(result);
        response.render('resultpage', {grants : result});
        response.end()
    } catch (err){
        console.log(err.message);
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.delete('/logout', (req, res, next) => {
    req.logOut((err) => {
      if (err) {
        return next(err);
      }
      res.redirect('/login');
    });
});

module.exports = router;