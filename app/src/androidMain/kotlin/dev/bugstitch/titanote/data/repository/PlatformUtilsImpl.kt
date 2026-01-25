package dev.bugstitch.titanote.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import dev.bugstitch.titanote.domain.repository.PlatformUtils
import androidx.core.net.toUri

class PlatformUtilsImpl(
    private val context: Context
): PlatformUtils {

    override fun openUrl(url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            url.toUri()
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    }

}