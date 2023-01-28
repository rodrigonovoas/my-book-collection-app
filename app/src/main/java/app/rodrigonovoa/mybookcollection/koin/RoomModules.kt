package app.rodrigonovoa.mybookcollection.koin

import android.app.Application
import androidx.room.Room
import app.rodrigonovoa.mybookcollection.db.BookCollectionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val roomModules = module {
    single {
        BookCollectionRepository(
            get(),
            CoroutineScope(
                SupervisorJob()
            )
        )
    }
}