package app.rodrigonovoa.mybookcollection.koin

import app.rodrigonovoa.mybookcollection.ui.bookBrowser.BookBrowserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { BookBrowserViewModel(get(), get()) }
}
