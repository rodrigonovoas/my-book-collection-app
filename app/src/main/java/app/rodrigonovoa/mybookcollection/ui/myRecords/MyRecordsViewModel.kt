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

class MyRecordsViewModel(private val bookCollectionRepository: BookCollectionRepository) :
    ViewModel() {
    private val _storedRecords = MutableLiveData<List<Record>>().apply { postValue(listOf()) }
    val storedRecords: LiveData<List<Record>> get() = _storedRecords

    fun getRecordsFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val storedRecords = bookCollectionRepository.getAllRecords()
            val mappedRecords = mapRecords(storedRecords)
            _storedRecords.postValue(mappedRecords)
        }
    }

    private fun mapRecords(records: List<RecordAndBookEntity>): List<Record> {
        val storedRecords = mutableListOf<Record>()

        for (record in records) {
            storedRecords.add(
                Record(
                    record.id.toInt(), record.dateTime ?: 0, record.name ?: "", record.author ?: "", record.imageUrl ?: "",
                    record.spentTime ?: 0
                )
            )
        }

        return storedRecords.toList()
    }
}