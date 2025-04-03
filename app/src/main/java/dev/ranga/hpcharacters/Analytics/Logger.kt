package dev.ranga.hpcharacters.Analytics

import android.util.Log
import javax.inject.Inject

interface Logger {
    fun debug(tag: String, message: String)
    fun error(tag: String, message: String, throwable: Throwable? = null)
    fun info(tag: String, message: String)
}

class LoggerImpl @Inject constructor() : Logger {

    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun info(tag: String, message: String) {
        Log.i(tag, message)
    }
}