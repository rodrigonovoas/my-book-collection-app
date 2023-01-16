package app.rodrigonovoa.mybookcollection.db

import app.rodrigonovoa.mybookcollection.data.model.Record

interface BookCollectionDbImpl {
    fun getRecords(): List<Record>
}