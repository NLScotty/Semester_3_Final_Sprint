const dal = require("./pdb");

// The database access functions for books.
// I have all the crud methods implemented.

const getBooks = function() {
    if(DEBUG) console.log("book-dal getBooks()");
    return new Promise(function(resolve, reject) {
        const sql = "SELECT * FROM BOOK WHERE archived != true ORDER BY book_id;"
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

const getBookById = function(bookId){
    if(DEBUG) console.log("book-dal getBookById()");
    return new Promise(function(resolve, reject) {
        const sql = "SELECT * FROM BOOK WHERE book_id = $1 AND archived != true"
        dal.query(sql, [bookId], (err, result) => {
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

const addNewBook = function(isbn, title, year, author_id, genre_id){
    if(DEBUG) console.log("book-dal addNewBook(params)");
    return new Promise(function(resolve, reject) {
        const sql = "INSERT INTO BOOK (isbn, title, year, author_ID, genre_ID) VALUES ($1, $2, $3, $4, $5)";
        dal.query(sql, [isbn, title, year, author_id, genre_id], (err, result) => {
            if (err) {
                if(DEBUG) console.log(err);
                reject(err);
            } else {
                if(DEBUG) console.log(result);
                resolve("Operation Complete - Book added to database");
            }
        }); 
    }); 
}

const editBook = function(book_id, isbn, title, year, author_id, genre_id){
    if(DEBUG) console.log("book-dal editBook(params)");
    return new Promise(function(resolve, reject) {
        const sql = "UPDATE BOOK SET isbn = $1, title = $2, year = $3, author_ID = $4, genre_ID = $5 WHERE book_id = $6;";
        dal.query(sql, [isbn, title, year, author_id, genre_id, book_id], (err, result) => {
            if (err) {
                if(DEBUG) console.log(err);
                reject(err);
            } else {
                if(DEBUG) console.log(result);
                resolve("Operation Complete - Book has been edited");
            }
        }); 
    }); 
}

const deleteBook = function(bookId){
    if(DEBUG) console.log("book-dal deleteBook(bookId)");
    return new Promise(function(resolve, reject) {
        const sql = "UPDATE BOOK SET archived = true WHERE book_id = $1"
        dal.query(sql, [bookId], (err, result) => {
            if (err) {
                if(DEBUG) console.log(err);
                reject(err);
            } else {
                if(DEBUG) console.log(result);
                resolve("Operation Complete - Book has been archived/deleted");
            }
        }); 
    }); 
};

module.exports = {
    getBooks,
    getBookById,
    addNewBook,
    editBook,
    deleteBook,
}