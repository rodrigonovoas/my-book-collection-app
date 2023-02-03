package app.rodrigonovoa.mybookcollection.ui.addRecord

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity
import app.rodrigonovoa.mybookcollection.db.BookCollectionRepository
import app.rodrigonovoa.mybookcollection.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class AddRecordViewModel(private val bookCollectionRepository: BookCollectionRepository) :
    ViewModel() {

    private val _localDbBooks = MutableLiveData<List<BookEntity>>().apply { postValue(listOf()) }
    val localDbBooks: LiveData<List<BookEntity>> get() = _localDbBooks

    init {
        getBooksFromDatabase()
    }

    fun insertNewRecord(spentTime: Long) {
        val record =
            RecordEntity(
                null, DateUtils.getCurrentDateTimeAsTimeStamp(),
                spentTime, 1, null
            )

        viewModelScope.launch(Dispatchers.IO) {
            val addedRecord = bookCollectionRepository.insertRecord(record)
            Timber.i("DEBUG-- ADDED RECORD $addedRecord")
        }
    }

    private fun getBooksFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val books = bookCollectionRepository.getAllBooks()
            _localDbBooks.postValue(books)
        }
    }

}