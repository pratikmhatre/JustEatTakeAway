package com.pratik.takeawayassignment.utils

open class Event<out T>(private val content: T?) {
    private var hasBeenHandled = false

    fun getContentIfNotHanlded(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
    fun peekContent() = content
}