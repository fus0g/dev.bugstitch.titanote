package dev.bugstitch.titanote.utils

import android.util.Log

actual fun CustomLog(error: String, message: String) {

    Log.e(error,message)
}