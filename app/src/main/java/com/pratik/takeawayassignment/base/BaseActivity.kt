package com.pratik.takeawayassignment.base

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {
    fun toggleSoftInputKeyboard(shouldShow: Boolean) {
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        if (shouldShow) {
            inputManager.showSoftInput(view, 0)
        } else {
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}