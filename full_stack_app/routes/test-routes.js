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

module.exports = router;