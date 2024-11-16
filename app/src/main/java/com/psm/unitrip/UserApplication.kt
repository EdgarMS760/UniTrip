package com.psm.unitrip

import android.app.Application
import com.psm.unitrip.Data.DataDbHelper

class UserApplication : Application()  {

    companion object{
        lateinit var dbHelper: DataDbHelper
    }

    override fun onCreate() {
        super.onCreate()
        dbHelper =  DataDbHelper(applicationContext)
    }
}