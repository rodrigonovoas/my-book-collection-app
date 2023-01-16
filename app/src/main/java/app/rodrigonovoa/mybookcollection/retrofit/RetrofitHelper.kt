package app.rodrigonovoa.mybookcollection.retrofit

import app.rodrigonovoa.mybookcollection.api.GoogleBooksService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val baseUrl = "https://www.googleapis.com/books/v1/"
    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }

    fun getClient(): GoogleBooksService {
        return getRetrofit().create(GoogleBooksService::class.java)
    }
}