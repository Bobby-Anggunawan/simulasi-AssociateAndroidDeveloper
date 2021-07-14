package com.dicoding.courseschedule.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.ID_REPEATING
import com.dicoding.courseschedule.util.NightMode
import java.util.*

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
        //TODO 11(DONE) : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val switchNotification: SwitchPreference? = findPreference(getString(R.string.pref_key_notify))
        switchNotification?.setOnPreferenceChangeListener{ preference, newValue ->
            val broadcast = DailyReminder()
            if (newValue == true){
                broadcast.setDailyReminder(requireContext())
                Toast.makeText(activity,"enabled",Toast.LENGTH_LONG).show()
            }else{
                broadcast.cancelAlarm(requireContext())
                Toast.makeText(activity,"disabled",Toast.LENGTH_LONG).show()
            }

            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

    //==============================================
}