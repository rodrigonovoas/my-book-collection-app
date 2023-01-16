package app.rodrigonovoa.mybookcollection.api

import app.rodrigonovoa.mybookcollection.data.api.QueryResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GoogleBooksApiImpl {
    fun getBooksFromApiByName(name: String): Flow<Response<QueryResult?>>
}