package com.appback.appbacksdk.callbacks

import com.appback.appbacksdk.poko.transalation.Translation

interface OnTranslationsSearched {
    fun onTranslationsFound(translations: List<Translation>)
}