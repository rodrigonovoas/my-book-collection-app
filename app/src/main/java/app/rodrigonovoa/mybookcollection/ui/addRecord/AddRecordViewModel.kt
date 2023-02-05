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
import java.util.concurrent.TimeUnit

class AddRecordViewModel(private val bookCollectionRepository: BookCollectionRepository) :
    ViewModel() {

    private val _localDbBooks = MutableLiveData<List<BookEntity>>().apply { postValue(listOf()) }
    val localDbBooks: LiveData<List<BookEntity>> get() = _localDbBooks
    var selectedBookId: Int = 0

    init {
        getBooksFromDatabase()
    }

    fun insertNewRecord(spentHours: Long, spentMinutes: Long) {
        val spentTime = getSpentTimeInMillis(spentHours, spentMinutes)
        val record =
            RecordEntity(
                null, DateUtils.getCurrentDateTimeAsTimeStamp(),
                spentTime, selectedBookId, null
            )

        viewModelScope.launch(Dispatchers.IO) {
            val addedRecord = bookCollectionRepository.insertRecord(record)
        }
    }

    private fun getSpentTimeInMillis(spentHours: Long, spentMinutes: Long): Long {
        val minutesInMilis = TimeUnit.MINUTES.toMillis(spentMinutes)
        val hoursInMilis = TimeUnit.HOURS.toMillis(spentHours)
        return minutesInMilis + hoursInMilis
    }

    private fun getBooksFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val books = bookCollectionRepository.getAllBooks()
            _localDbBooks.postValue(books)
        }
    }

}