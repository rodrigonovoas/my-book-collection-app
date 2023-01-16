package app.rodrigonovoa.mybookcollection.ui.myRecords

import androidx.lifecycle.ViewModel
import app.rodrigonovoa.mybookcollection.data.model.Record
import app.rodrigonovoa.mybookcollection.db.FakeCollectionDatabase

class MyRecordsViewModel : ViewModel() {
    private val fakeDb = FakeCollectionDatabase()

    fun getRecordsFromDb(): List<Record>{
        return fakeDb.getRecords()
    }
}