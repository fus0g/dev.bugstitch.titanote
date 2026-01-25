package dev.bugstitch.titanote.data.repository

import androidx.compose.ui.platform.LocalUriHandler
import dev.bugstitch.titanote.domain.repository.PlatformUtils
import java.awt.Desktop
import java.net.URI

class PlatformUtilsImpl: PlatformUtils {

    override fun openUrl(url: String) {

        runCatching {
            if (!Desktop.isDesktopSupported()) {
                println("Desktop not supported")
                return
            }

            val desktop = Desktop.getDesktop()
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                println("Browse action not supported")
                return
            }

            desktop.browse(URI(url))
        }.onFailure {
            it.printStackTrace()
        }
    }

}