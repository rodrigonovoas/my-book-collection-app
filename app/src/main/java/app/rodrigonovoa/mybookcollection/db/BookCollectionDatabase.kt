package app.rodrigonovoa.mybookcollection.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import app.rodrigonovoa.mybookcollection.data.db.OpinionEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(BookEntity::class, RecordEntity::class, OpinionEntity::class), version = 1, exportSchema = false)
abstract class BookCollectionDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDAO
    abstract fun recordDao(): RecordDAO
    abstract fun opinionDao(): OpinionDAO

    private class BookCollectionDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    // code after init
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: BookCollectionDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): BookCollectionDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookCollectionDatabase::class.java,
                    "book_collection.db"
                )
                    .addCallback(BookCollectionDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}