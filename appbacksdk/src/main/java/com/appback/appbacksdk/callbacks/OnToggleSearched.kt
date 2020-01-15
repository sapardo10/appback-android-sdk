package com.appback.appbacksdk.callbacks

import com.appback.appbacksdk.poko.toggle.Toggle

interface OnToggleSearched {
    fun onToggleFound(toggle: Toggle)
    fun onToggleNotFound(key: String)
}