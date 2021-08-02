package com.dicoding.habitapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.dicoding.habitapp.utils.DarkMode

class ThemeInitApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_dark),
            getString(R.string.pref_dark_auto)
        )?.apply {
            if(this == getString(R.string.pref_dark_auto)){
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) updateTheme(
                    DarkMode.FOLLOW_SYSTEM.value)
                else updateTheme(DarkMode.OFF.value)
            }
            else if(this == getString(R.string.pref_dark_off)) updateTheme(DarkMode.OFF.value)
            else updateTheme(DarkMode.ON.value)
        }
    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        return true
    }
}