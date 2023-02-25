package app.rodrigonovoa.mybookcollection.ui.myRecords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.mybookcollection.data.db.RecordAndBookEntity
import app.rodrigonovoa.mybookcollection.data.model.Record
import app.rodrigonovoa.mybookcollection.db.BookCollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MyRecordsViewModel(private val bookCollectionRepository: BookCollectionRepository) :
    ViewModel() {
    private val _storedRecords = MutableLiveData<List<Record>>().apply { postValue(listOf()) }
    val storedRecords: LiveData<List<Record>> get() = _storedRecords

    fun getRecordsFromInterval(calendar: Calendar) {
        val startDay = getStartOfTheDayInMillis(calendar)
        val endDay = getEndOfTheDayInMillis(calendar)

        viewModelScope.launch(Dispatchers.IO) {
            val storedRecords = bookCollectionRepository.getRecordsFromInterval(startDay, endDay)
            val mappedRecords = mapRecords(storedRecords)
            _storedRecords.postValue(mappedRecords)
        }
    }

    private fun mapRecords(records: List<RecordAndBookEntity>): List<Record> {
        val storedRecords = mutableListOf<Record>()

        for (record in records) {
            storedRecords.add(
                Record(
                    record.id.toInt(), record.dateTime ?: 0, record.name ?: "",
                    record.author ?: "", record.imageUrl ?: "",
                    record.spentTime ?: 0
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