const express =require('express');
const { getBooks, getBookById, addNewBook , editBook, deleteBook} = require('../services/book-dal');
const { getAuthors, getAuthorById } = require('../services/author-dal')
const { getGenres, getGenreById } = require('../services/genre-dal')

const router = express.Router();

// A get route that fetches all books.
router.get("/books", async (request, response) => {
    if(DEBUG) console.log("get api books route.")
    try{
        let books = await getBooks();
        response.write(JSON.stringify(books));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

// A get request that takes the id param from the url
router.get("/books/id/:bookid", async (request, response) => {
    if(DEBUG) console.log("get api bookByID route.")
    try{
        let book = await getBookById(request.params.bookid);
        response.write(JSON.stringify(book));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

// A post route that returns a book by book id. The id is sent through
// the post request does the same thing as the function above.
router.post("/books/id", async (request, response) => {
    if(DEBUG) console.log("post api bookByID route.")
    try{
        let book = await getBookById(request.body.bookId);
        response.write(JSON.stringify(book));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

// A put route that adds a book to the database
router.put("/books/add", async (request, response) => {
    if(DEBUG) console.log("post api books route.")
    try{
        let message = await addNewBook(request.body.isbn, request.body.title, request.body.year, request.body.author_id, request.body.genre_id);
        response.write(message);
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

// A patch route that edits a book from the given id.
router.patch("/books/edit", async (request, response) => {
    if(DEBUG) console.log("post api books route.")
    try{
        let message = await editBook(request.body.book_id, request.body.isbn, request.body.title, request.body.year, request.body.author_id, request.body.genre_id);
        response.write(message);
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
});

// A delete route that removes/archives book from database.
// I chose to archive the entry instead of remove it to avoid
// removing information from other tables, such as receipts.
router.delete("/books/delete", async (request, response) => {
    if(DEBUG) console.log("post api books route.")
    try{
        let message = await deleteBook(request.body.book_id);
        response.write(message);
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
});

// Some get methods for some of the other database objects.

router.get("/authors", async (request, response) => {
    if(DEBUG) console.log("get api authors route.")
    try{
        let authors = await getAuthors();
        response.write(JSON.stringify(authors));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.get("/authors/id/:authid", async (request, response) => {
    if(DEBUG) console.log("get api authorByID route.")
    try{
        let author = await getAuthorById(request.params.authid);
        response.write(JSON.stringify(author));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.get("/genres", async (request, response) => {
    if(DEBUG) console.log("get api genres route.")
    try{
        let genres = await getGenres();
        response.write(JSON.stringify(genres));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

router.get("/genres/id/:genreid", async (request, response) => {
    if(DEBUG) console.log("post api genreByID route.")
    try{
        let genre = await getGenreById(request.params.genreid);
        response.write(JSON.stringify(genre));
        response.end()
    } catch{
        response.status(500).send('500 - Server error with data fetching.');
    }
})

module.exports = router;