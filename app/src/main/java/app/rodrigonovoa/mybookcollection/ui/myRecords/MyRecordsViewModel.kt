package app.rodrigonovoa.mybookcollection.ui.myRecords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity
import app.rodrigonovoa.mybookcollection.data.model.Record
import app.rodrigonovoa.mybookcollection.db.BookCollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRecordsViewModel(private val bookCollectionRepository: BookCollectionRepository) : ViewModel() {
    private val _storedRecords = MutableLiveData<List<Record>>().apply { postValue(listOf())}
    val storedRecords: LiveData<List<Record>> get() = _storedRecords

    fun getRecordsFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val storedRecords = bookCollectionRepository.getAllRecords()
            _storedRecords.postValue(mapRecords(storedRecords))
        }
    }

    private fun mapRecords(records: List<RecordEntity>): List<Record> {
        val storedRecords = mutableListOf<Record>()

        for (record in records) {
            storedRecords.add(
                Record(
                    record.id!!, record.dateTime, "test", "test 2", "",
                    record.spentTime
                )
            )
        }

        return storedRecords.toList()
    }
}