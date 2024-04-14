// Some test routes I used to test the api before I have constructed the
// UI. I invoked through postman, though one could do it on a web browser.

const express =require('express');

const router = express.Router();
const grantController = require('../controller/grant-controllers')


router.get('/sql', async (request, response) => {
    if(DEBUG) console.log("test route")
    try{
        let grants = await grantController.getGrants("sql");
        response.write(JSON.stringify(grants));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.get('/mongo', async (request, response) => {
    if(DEBUG) console.log("test route")
    try{
        let grants = await grantController.getGrants("mongo");
        response.write(JSON.stringify(grants));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.post('/contains', async (request, response) => {
    if(DEBUG) console.log("test route")
    try{
        let grants = await grantController.supplierContains(request.body.database,request.body.input.toUpperCase());
        response.write(JSON.stringify(grants));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.post('/exact', async (request, response) => {
    if(DEBUG) console.log("test route")
    try{
        let grants = await grantController.exactMatch(request.body.database,request.body.input.toUpperCase());
        response.write(JSON.stringify(grants));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.post('/less', async (request, response) => {
    if(DEBUG) console.log("test route")
    try{
        let grants = await grantController.amountLower(request.body.database,request.body.input.toUpperCase());
        response.write(JSON.stringify(grants));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.post('/more', async (request, response) => {
    if(DEBUG) console.log("test route")
    try{
        let grants = await grantController.amountHigher(request.body.database,request.body.input.toUpperCase());
        response.write(JSON.stringify(grants));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.post('/equal', async (request, response) => {
    if(DEBUG) console.log("test route")
    try{
        let grants = await grantController.amountEqual(request.body.database,request.body.input.toUpperCase());
        response.write(JSON.stringify(grants));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.post('/search', async (request, response) => {
    if(DEBUG) console.log("test route")
    try{
        let result = null;
        if(request.body.search == 'amount'){
            if(request.body.option == 'less'){
                result = await grantController.amountLower(request.body.database,request.body.input.toUpperCase());
            }
            else if(request.body.option == 'more'){
                result = await grantController.amountHigher(request.body.database,request.body.input.toUpperCase());
            }
            else if(request.body.option == 'equal'){
                result = await grantController.amountEqual(request.body.database,request.body.input.toUpperCase());
            }
        }
        else if(request.body.search == 'name'){
            if(request.body.option == 'containing'){
                result = await grantController.supplierContains(request.body.database,request.body.input.toUpperCase());
            }
            else if(request.body.option == 'exact'){
                result = await grantController.exactMatch(request.body.database,request.body.input.toUpperCase());
            }
        }
        response.write(JSON.stringify(result));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})


module.exports = router;