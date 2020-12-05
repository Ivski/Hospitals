package rocks.ivski.hospitals

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import rocks.ivski.hospitals.di.appModule
import rocks.ivski.hospitals.di.repoModule
import rocks.ivski.hospitals.di.vmModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, vmModule))
        }
    }
}