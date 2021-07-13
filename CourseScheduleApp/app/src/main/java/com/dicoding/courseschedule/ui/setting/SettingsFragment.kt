package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat()  {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10(DONE) : Update theme based on value in ListPreference
        val switchDarkMode: ListPreference? = findPreference(getString(R.string.pref_key_dark))
        switchDarkMode?.setOnPreferenceChangeListener{ preference, newValue ->
            val stringValue = newValue.toString()
            if(stringValue == getString(R.string.pref_dark_auto)){
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) updateTheme(NightMode.AUTO.value)
                else updateTheme(NightMode.OFF.value)
            }
            else if(stringValue == getString(R.string.pref_dark_off)) updateTheme(NightMode.OFF.value)
            else updateTheme(NightMode.ON.value)

            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}