package app.rodrigonovoa.mybookcollection

import android.app.Application
import app.rodrigonovoa.mybookcollection.koin.networkModules
import app.rodrigonovoa.mybookcollection.koin.roomModules
import app.rodrigonovoa.mybookcollection.koin.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import timber.log.Timber

class MyBookCollectionApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin()
        Timber.plant(Timber.DebugTree())
    }

    private fun startKoin() {
        GlobalContext.startKoin {
            androidContext(applicationContext)
            modules(listOf(networkModules, viewModelModules, roomModules))
        }
    }
}