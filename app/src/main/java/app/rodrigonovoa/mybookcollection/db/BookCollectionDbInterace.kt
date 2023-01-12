package app.rodrigonovoa.mybookcollection.db

import app.rodrigonovoa.mybookcollection.data.Record

interface BookCollectionDbInterace {
    fun getRecords(): List<Record>
}