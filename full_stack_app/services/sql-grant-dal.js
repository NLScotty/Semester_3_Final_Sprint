const dal = require("./pdb");

async function getGrants() {
    if(DEBUG) console.log("Grants.pg.dal.getGrants()");
    const sql = `SELECT * FROM Grants;`
    try {
      let results = await dal.query(sql);
      return results.rows;
    } catch (error) {
      console.log(error);
      throw error; 
    }
};

module.exports = {
    getGrants,
}