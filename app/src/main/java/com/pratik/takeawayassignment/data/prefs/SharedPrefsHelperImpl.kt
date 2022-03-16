package com.pratik.takeawayassignment.data.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefsHelperImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SharedPrefsHelper {
    private val sortParam = "sort_param"
    override fun saveSortParam(param: String) {
        sharedPreferences.edit().putString(sortParam, param).apply()
    }

    override fun getSavedSortParam(): String? {
        return sharedPreferences.getString(sortParam, null)
    }
}