package app.rodrigonovoa.mybookcollection.api

import app.rodrigonovoa.mybookcollection.data.Book

class FakeGoogleBooksApi: GoogleBooksApiInterface {
    override fun getBooksFromApi(): List<Book> {
        val book1 = Book(1, "The Dunwich Horror", "H.P. Lovecraft", "Example book numer one", "https://m.media-amazon.com/images/I/41mIf-FO3EL.jpg")
        val book2 = Book(1, "Final Empire", "Brandon Sanderson","Example book numer two", "https://kbimages1-a.akamaihd.net/064d08d0-4048-439c-9e88-479b0ac9ce57/353/569/90/False/the-final-empire-2.jpg")
        val book3 = Book(1, "Misery", "Stephen King","Example book numer one", "https://m.media-amazon.com/images/I/51SSlC2WctL.jpg")

        return listOf(book1, book2, book3)
    }
}