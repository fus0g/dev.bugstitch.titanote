package dev.bugstitch.titanote

import android.app.Application
import dev.bugstitch.titanote.container.NotesApplicationContainer
import dev.bugstitch.titanote.container.NotesApplicationContainerImpl

class TitanoteApplication : Application() {

    lateinit var container: NotesApplicationContainer

    override fun onCreate() {
        super.onCreate()

        container = NotesApplicationContainerImpl(context = this)

    }

}