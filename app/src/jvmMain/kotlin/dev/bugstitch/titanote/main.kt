package dev.bugstitch.titanote

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.bugstitch.titanote.di.initKoin

fun main() = application{

    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Titanote"
    ){
        App()
    }
}
