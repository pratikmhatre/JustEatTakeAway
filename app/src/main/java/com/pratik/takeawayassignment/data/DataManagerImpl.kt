package com.pratik.takeawayassignment.data

import com.pratik.takeawayassignment.data.prefs.SharedPrefsHelperImpl
import javax.inject.Inject

class DataManagerImpl @Inject constructor(private val prefsHelperImpl: SharedPrefsHelperImpl) :
    DataManager {
    override fun saveSortParam(param: String) {
        prefsHelperImpl.saveSortParam(param)
    }

    override fun getSavedSortParam() = prefsHelperImpl.getSavedSortParam()
}