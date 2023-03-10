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

enum class RecordAddedStatus {
    ADDED, FAIL, NO_TIME_ADDED, NO_BOOK_ADDED, NONE
}

class AddRecordViewModel(private val bookCollectionRepository: BookCollectionRepository) :
    ViewModel() {

    private val _localDbBooks = MutableLiveData<List<BookEntity>>().apply { postValue(listOf()) }
    val localDbBooks: LiveData<List<BookEntity>> get() = _localDbBooks
    val addRecord = MutableLiveData<RecordAddedStatus>().apply { postValue(RecordAddedStatus.NONE) }
    var selectedBookId: Int = 0

    init {
        getBooksFromDatabase()
    }

    private fun getBooksFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val books = bookCollectionRepository.getAllBooks()
            _localDbBooks.postValue(books)
        }
    }

    fun insertNewRecord(date: Long, spentHours: Long, spentMinutes: Long) {
        if (!hasBookBeenSelected()) return
        val spentTime = getSpentTimeInMillis(spentHours, spentMinutes)
        if (!hasTimeBeenAdded(spentTime)) return
        val record =
            RecordEntity(
                null, date,
                spentTime, "", selectedBookId
            )

        viewModelScope.launch(Dispatchers.IO) {
            val addedRecord = bookCollectionRepository.insertRecord(record)
            if (addedRecord > 0) {
                addRecord.postValue(RecordAddedStatus.ADDED)
            } else {
                addRecord.postValue(RecordAddedStatus.FAIL)
            }
        }
    }

    private fun hasTimeBeenAdded(spentTime: Long): Boolean {
        if (spentTime.toInt() == 0) {
            addRecord.postValue(RecordAddedStatus.NO_TIME_ADDED)
            return false
        }
        return true
    }

    private fun hasBookBeenSelected(): Boolean {
        if (selectedBookId <= 0) {
            addRecord.postValue(RecordAddedStatus.NO_BOOK_ADDED)
            return false
        }
        return true
    }

    private fun getSpentTimeInMillis(spentHours: Long, spentMinutes: Long): Long {
        val minutesInMilis = TimeUnit.MINUTES.toMillis(spentMinutes)
        val hoursInMilis = TimeUnit.HOURS.toMillis(spentHours)
        return minutesInMilis + hoursInMilis
    }


}