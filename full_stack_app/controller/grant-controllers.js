const mongoGrantDal = require('../services/mon-grant-dal') // MongoDB DAL for Grants
const sqlGrantDal = require('../services/sql-grant-dal') // PostgreSQL DAL for Grants

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
        return null;
    }
}

async function supplierContains(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    data.forEach(grant => {
        if(grant.supplier_name.includes(string)){
            filteredData.push(grant);
        }
    });
    return filteredData;
}

async function exactMatch(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    data.forEach(grant => {
        if(grant.supplier_name === string){
            filteredData.push(grant);
        }
    });
    return filteredData;
}

async function amountLower(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    data.forEach(grant => {
        if(grant.amount < parseInt(string, 10)){
            filteredData.push(grant);
        }
    });
    return filteredData;
}

async function amountHigher(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    data.forEach(grant => {
        if(grant.amount > parseInt(string, 10)){
            filteredData.push(grant);
        }
    });
    return filteredData;
}

async function amountEqual(database, string){
    let data = await getGrants(database);
    let filteredData = [];
    data.forEach(grant => {
        if(grant.amount == parseInt(string, 10)){
            filteredData.push(grant);
        }
    });
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