package com.appback.appbacksdk.callbacks

import com.appback.appbacksdk.poko.transalation.Translation

interface OnTranslationSearched {
    fun onTranslationFound(translation: Translation)
    fun onTranslationNotFount(key: String)
}