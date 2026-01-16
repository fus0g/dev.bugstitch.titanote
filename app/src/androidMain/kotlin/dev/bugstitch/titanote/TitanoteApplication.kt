package dev.bugstitch.titanote

import android.app.Application
import dev.bugstitch.titanote.di.initKoin
import org.koin.android.ext.koin.androidContext

class TitanoteApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TitanoteApplication)
        }
    }

}