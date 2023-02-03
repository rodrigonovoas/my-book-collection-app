package app.rodrigonovoa.mybookcollection.koin

import app.rodrigonovoa.mybookcollection.api.GoogleBooksRepository
import app.rodrigonovoa.mybookcollection.api.GoogleBooksService
import app.rodrigonovoa.mybookcollection.ui.retrofit.RetrofitHelper
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModules = module {
    single { retrofitProvider() }
    single { clientProvider(get()) }
    single { GoogleBooksRepository(get()) }
}

private fun retrofitProvider(): Retrofit {
    return RetrofitHelper.getRetrofit()
}

private fun clientProvider(retrofit: Retrofit): GoogleBooksService {
    return retrofit.create(GoogleBooksService::class.java)
}
