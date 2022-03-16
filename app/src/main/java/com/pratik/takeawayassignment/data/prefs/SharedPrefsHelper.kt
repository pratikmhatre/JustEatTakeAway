package com.pratik.takeawayassignment.data.prefs

interface SharedPrefsHelper {
    fun saveSortParam(param: String)
    fun getSavedSortParam(): String?
}