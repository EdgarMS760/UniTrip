package com.psm.unitrip.Manager

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREF_NAME = "uni_trip_preferences"
    private const val KEY_LAST_SYNC = "last_sync"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Guardar última fecha de sincronización
    fun setLastSync(context: Context, lastSync: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_LAST_SYNC, lastSync)
        editor.apply()
    }

    // Recuperar última fecha de sincronización
    fun getLastSync(context: Context): String? {
        return getPreferences(context).getString(KEY_LAST_SYNC, null)
    }

    // Verificar si existe una fecha de sincronización
    fun hasLastSync(context: Context): Boolean {
        return getPreferences(context).contains(KEY_LAST_SYNC)
    }
}