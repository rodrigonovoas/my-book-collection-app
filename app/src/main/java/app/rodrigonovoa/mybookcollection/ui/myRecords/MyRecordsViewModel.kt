package app.rodrigonovoa.mybookcollection.ui.myRecords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.mybookcollection.data.db.RecordAndBookEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity
import app.rodrigonovoa.mybookcollection.data.model.Record
import app.rodrigonovoa.mybookcollection.db.BookCollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class MyRecordsViewModel(private val bookCollectionRepository: BookCollectionRepository) :
    ViewModel() {
    private val _storedRecords = MutableLiveData<List<Record>>().apply { postValue(listOf()) }
    val storedRecords: LiveData<List<Record>> get() = _storedRecords

    private val _totalHours = MutableLiveData<Long>().apply { postValue(0L) }
    val totalHours: LiveData<Long> get() = _totalHours

    private val _totalRecords = MutableLiveData<Int>().apply { postValue(0) }
    val totalRecords: LiveData<Int> get() = _totalRecords

    private val _reloadRecords = MutableLiveData<Boolean>().apply { postValue(false) }
    val reloadRecods: LiveData<Boolean> get() = _reloadRecords

    fun getRecordsFromInterval(calendar: Calendar) {
        val startDay = getStartOfTheDayInMillis(calendar)
        val endDay = getEndOfTheDayInMillis(calendar)

        viewModelScope.launch(Dispatchers.IO) {
            val storedRecords = bookCollectionRepository.getRecordsFromInterval(startDay, endDay)
            val mappedRecords = mapRecords(storedRecords)
            _storedRecords.postValue(mappedRecords)
        }
    }

    fun getTotalHoursFromBookId(bookId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val records = bookCollectionRepository.getRecordsByBookId(bookId)
            var totalHours = 0L
            for (record in records) {
                totalHours += (record.spentTime ?: 0L)
            }
            _totalHours.postValue(totalHours)
            _totalRecords.postValue(records.size)
        }
    }

    fun removeRecord(recordId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val deleted = bookCollectionRepository.deleteRecordById(recordId)
            if (deleted > 0) _reloadRecords.postValue(true)
        }
    }

    fun updateRecordComment(record: RecordEntity, comment: String) {
        val recordToUpdate = record
        recordToUpdate.comment = comment
        viewModelScope.launch(Dispatchers.IO) {
            bookCollectionRepository.updateRecord(recordToUpdate)
        }
    }

    private fun mapRecords(records: List<RecordAndBookEntity>): List<Record> {
        val storedRecords = mutableListOf<Record>()

        for (record in records) {
            storedRecords.add(
                Record(
                    record.id.toInt(), record.dateTime ?: 0, record.bookId?.toInt() ?: 0,record.name ?: "",
                    record.author ?: "", record.imageUrl ?: "",
                    record.spentTime ?: 0, record.comment ?: ""
                )
            )
        }

        return storedRecords.toList()
    }

    private fun getEndOfTheDayInMillis(calendar: Calendar): Long {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        val endOfTheDay = calendar.timeInMillis

        return endOfTheDay
    }

    private fun getStartOfTheDayInMillis(calendar: Calendar): Long {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        val startOfTheDay = calendar.timeInMillis

        return startOfTheDay
    }
}