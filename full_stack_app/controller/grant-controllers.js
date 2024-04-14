// The contorller function of the application. In a high level way,
// through this library, I merge the differences of sql and mongo
// to functions usable by the rest of the application

const mongoGrantDal = require('../services/mon-grant-dal') // MongoDB DAL for Grants
const sqlGrantDal = require('../services/sql-grant-dal') // PostgreSQL DAL for Grants

// fetches the grants from the database specified.
async function getGrants(database){
    if(database == "mongo"){
        let grants = await mongoGrantDal.getGrants();
        return grants;
    }
    else if(database == "sql"){
        let grants = await sqlGrantDal.getGrants();
        return grants;
    }
    else{
        return [];
    }
}

// Used to match words within supplier name. non case sensetive
async function supplierContains(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    try{
        data.forEach(grant => {
            if(grant.supplier_name.includes(string)){
                filteredData.push(grant);
            }
        });
    }catch{

    }
    return filteredData;
}

// Used to match supplier name. non case sensetive
async function exactMatch(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    try{
        data.forEach(grant => {
            if(grant.supplier_name === string){
                filteredData.push(grant);
            }
        });
    }catch{
        
    }
    return filteredData;
}

// Used to fetch all grants with amount lower of the string
async function amountLower(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    try {
        data.forEach(grant => {
            if(grant.amount < parseInt(string, 10)){
                filteredData.push(grant);
            }
        });
    }catch{

    }
    return filteredData;
}

// Used to fetch all grants with amount higher of the string
async function amountHigher(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    try {
        data.forEach(grant => {
            if(grant.amount > parseInt(string, 10)){
                filteredData.push(grant);
            }
        });
    }catch{

    }
    return filteredData;
}

// Used to fetch all grants with amount equal of the string
async function amountEqual(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    try {
        data.forEach(grant => {
            if(grant.amount == parseInt(string, 10)){
                filteredData.push(grant);
            }
        });
    }
    catch{

    }
    return filteredData;
}

module.exports = {
    getGrants,
    supplierContains,
    exactMatch,
    amountLower,
    amountHigher,
    amountEqual,
}