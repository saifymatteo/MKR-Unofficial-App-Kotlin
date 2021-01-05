package com.saifymatteo.mykampusradiounofficial

import android.content.Context
import android.preference.PreferenceManager

/* Big thanks to Manuel Ernesto code snippet from
https://proandroiddev.com/dark-mode-on-android-app-with-kotlin-dc759fc5f0e1 */
class UserPreferences(context: Context?) {
    companion object {
        private const val DARK_STATUS = "io.github.manuelernesto.DARK_STATUS"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_STATUS, 0)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
}