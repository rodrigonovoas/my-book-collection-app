package app.rodrigonovoa.mybookcollection.api

import app.rodrigonovoa.mybookcollection.data.Book

interface GoogleBooksApiInterface {
    fun getBooksFromApi(): List<Book>
}