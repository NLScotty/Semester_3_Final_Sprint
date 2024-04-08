const mongoGrantDal = require('../../services/mon-grant-dal') // MongoDB DAL for Grants
const sqlGrantDal = require('../../services/sql-grant-dal') // PostgreSQL DAL for Grants

async function getGrants(database){
    if(database == "mongo"){
        let grants = await mongoGrantDalDal.getGrants();
        return grants;
    }
    else if(database = "sql"){
        let grants = await sqlGrantDalDal.getGrants();
        return grants;
    }
    else{
        return null;
    }
}