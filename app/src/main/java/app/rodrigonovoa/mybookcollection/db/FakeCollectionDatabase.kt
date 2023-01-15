package app.rodrigonovoa.mybookcollection.db

import app.rodrigonovoa.mybookcollection.data.Record

class FakeCollectionDatabase: BookCollectionDbInterace {
    override fun getRecords(): List<Record> {
        return listOf(
            Record(1, 100, "El Horror de Dunwich",
                "HP Lovecraft", "https://m.media-amazon.com/images/I/41mIf-FO3EL.jpg", 7200000),
            Record(2, 100, "Misery",
                "Stephen King", "https://m.media-amazon.com/images/I/51SSlC2WctL.jpg", 7200000)
        )
    }
}