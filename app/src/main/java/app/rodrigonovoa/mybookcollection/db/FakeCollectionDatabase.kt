package app.rodrigonovoa.mybookcollection.db

import app.rodrigonovoa.mybookcollection.data.Record

class FakeCollectionDatabase: BookCollectionDbInterace {
    override fun getRecords(): List<Record> {
        return listOf(
            Record(1, 100, "El Horror de Dunwich",
                "HP Lovecraft", "", 100),
            Record(2, 100, "Misery",
                "Stephen King", "", 100)
        )
    }
}