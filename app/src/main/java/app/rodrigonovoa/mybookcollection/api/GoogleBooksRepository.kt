package app.rodrigonovoa.mybookcollection.api

import app.rodrigonovoa.mybookcollection.data.api.QueryResult
import app.rodrigonovoa.mybookcollection.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class GoogleBooksRepository : GoogleBooksApiImpl {
    override fun getBooksFromApiByName(name: String): Flow<Response<QueryResult?>> = flow {
        val books = RetrofitHelper.getClient().getBooksByName(name).execute()
        emit(books)
    }.flowOn(Dispatchers.IO)
}