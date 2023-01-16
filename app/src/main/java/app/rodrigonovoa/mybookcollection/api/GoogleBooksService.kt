package app.rodrigonovoa.mybookcollection.api

import app.rodrigonovoa.mybookcollection.data.api.QueryResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksService {
    @GET("volumes")
    fun getBooksByName(@Query("q") bookName: String): Call<QueryResult>
}