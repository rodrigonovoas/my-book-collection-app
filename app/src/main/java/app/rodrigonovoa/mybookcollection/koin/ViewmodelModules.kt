package app.rodrigonovoa.mybookcollection.koin

import app.rodrigonovoa.mybookcollection.ui.addRecord.AddRecordViewModel
import app.rodrigonovoa.mybookcollection.ui.bookBrowser.BookBrowserViewModel
import app.rodrigonovoa.mybookcollection.ui.myBooks.MyBooksViewModel
import app.rodrigonovoa.mybookcollection.ui.myRecords.MyRecordsViewModel
import app.rodrigonovoa.mybookcollection.ui.myStats.MyStatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { BookBrowserViewModel(get(), get()) }
    viewModel { MyBooksViewModel(get()) }
    viewModel { AddRecordViewModel(get()) }
    viewModel { MyRecordsViewModel(get()) }
    viewModel { MyStatsViewModel(get()) }
}
