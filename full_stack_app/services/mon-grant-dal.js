const dal = require("./mdb.js");

async function getGrants() {
  if(DEBUG) console.log("Auth.mongo.dal.getGrants()");
  try {
    // connect to the mongo database
    await dal.connect();
    const cursor = dal.db("FullStackFinal").collection("Grants").find();
    const results = await cursor.toArray();
    return results;
  } catch(error) {
    console.log(error);
    // throw error maybe;
  } finally {
    dal.close();
  }
};


module.exports = {
  getGrants,
}