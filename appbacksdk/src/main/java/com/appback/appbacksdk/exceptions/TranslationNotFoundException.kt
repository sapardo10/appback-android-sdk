package com.appback.appbacksdk.exceptions

import com.appback.appbacksdk.AppbackConstants.TRANSLATION_NOT_FOUND

class TranslationNotFoundException : Exception() {
    override val message = TRANSLATION_NOT_FOUND
}