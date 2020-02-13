package com.appback.appbacksdk.callbacks

import com.appback.appbacksdk.poko.toggle.Toggle

interface OnTogglesSearched {
    fun onTogglesFound(toggles: List<Toggle>)
}