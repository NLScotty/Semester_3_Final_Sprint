const dal = require("./pdb");
// The database access functions for author.
// I focus on books, so author and genres only
// consist of some get/select functions

const getAuthors = function() {
    if(DEBUG) console.log("author-dal getAuthors()");
    return new Promise(function(resolve, reject) {
        const sql = "SELECT * FROM AUTHOR ORDER BY author_id;"
        dal.query(sql, [], (err, result) => {
            if (err) {
                if(DEBUG) console.log(err);
                reject(err);
            } else {
                if(DEBUG) console.log(result.rows);
                resolve(result.rows);
            }
        }); 
    }); 
};

const getAuthorById = function(authorId){
    if(DEBUG) console.log("author-dal getAuthorById()");
    return new Promise(function(resolve, reject) {
        const sql = "SELECT * FROM AUTHOR WHERE Author_id = $1"
        dal.query(sql, [authorId], (err, result) => {
            if (err) {
                if(DEBUG) console.log(err);
                reject(err);
            } else {
                if(DEBUG) console.log(result.rows);
                resolve(result.rows);
            }
        }); 
    }); 
};


module.exports = {
    getAuthors,
    getAuthorById,
}