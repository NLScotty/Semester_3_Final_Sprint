const dal = require("./pdb");

// The database access functions for genre.
// I focus on books, so author and genres only
// consist of some get/select functions

const getGenres = function() {
    if(DEBUG) console.log("genre-dal getGenres()");
    return new Promise(function(resolve, reject) {
        const sql = "SELECT * FROM GENRE ORDER BY genre_id;"
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

const getGenreById = function(genreId){
    if(DEBUG) console.log("genre-dal getGenreById()");
    return new Promise(function(resolve, reject) {
        const sql = "SELECT * FROM GENRE WHERE genre_id = $1"
        dal.query(sql, [genreId], (err, result) => {
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
    getGenres,
    getGenreById,
}