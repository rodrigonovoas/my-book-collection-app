package app.rodrigonovoa.mybookcollection.api

import app.rodrigonovoa.mybookcollection.data.Book

class FakeGoogleBooksApi: GoogleBooksApiInterface {
    override fun getBooksFromApi(): List<Book> {
        val book1 = Book(1, "The Dunwich Horror", "H.P. Lovecraft", "Example book numer one", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.es%2FDunwich-Horror-Classics-Go-English-ebook%2Fdp%2FB077V2V364&psig=AOvVaw3nE7R_RRVcTn1rEmLqMmab&ust=1672865124483000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCIDlxbeirPwCFQAAAAAdAAAAABAE")
        val book2 = Book(1, "Final Empire", "Brandon Sanderson","Example book numer two", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.kobo.com%2Fes%2Fes%2Faudiobook%2Fthe-final-empire-2&psig=AOvVaw0I1u-UMSWIf7y3Qrz__s6f&ust=1672865278726000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCPjCjIGjrPwCFQAAAAAdAAAAABAJ")
        val book3 = Book(1, "Misery", "Stephen King","Example book numer one", "https://m.media-amazon.com/images/I/51SSlC2WctL.jpg")

        return listOf(book1, book2, book3)
    }
}