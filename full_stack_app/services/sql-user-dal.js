const dal = require("./pdb");
//get all logins.
async function getUsers() {
    const sql = `SELECT * FROM Users;`
    try {
      let results = await dal.query(sql);
      return results.rows;
    } catch (error) {
      console.log(error);
      throw error; 
    }
};
  
async function getUserById(id) {
    const sql = `SELECT * FROM Users WHERE id = $1`;
    try {
      let result = await dal.query(sql, [id]);
      return result.rows[0];
    } catch (error) { 
      console.log(error);
      throw error; 
    }; 
};

async function getUserByEmail(email) {
    const sql = `SELECT * FROM Users WHERE email = $1`;
    try {
      let result = await dal.query(sql, [id]);
      return result.rows[0];
    } catch (error) { 
      console.log(error);
      throw error; 
    }; 
};
  
async function addUser(fname, lname, email, password) {
    const sql = `INSERT INTO Users (fname, lname, email, password) VALUES ($1, $2, $3, $4) RETURNING email;`;
    try {
      let result = await dal.query(sql, [fname, lname, email, password]);
      return result.rows[0].email;
    } catch (error) { 
      if(error.code === '23505') // unique_violation
        return error.code;
      console.log(error);
      throw error;
    }
};


module.exports = {
    getUsers,
    getUserById,
    getUserByEmail,
    addUser,
}