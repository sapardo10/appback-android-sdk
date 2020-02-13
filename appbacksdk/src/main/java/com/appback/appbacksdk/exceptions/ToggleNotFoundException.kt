package com.appback.appbacksdk.exceptions


import com.appback.appbacksdk.AppbackConstants.TOGGLE_NOT_FOUND

class ToggleNotFoundException : Exception() {
    override val message = TOGGLE_NOT_FOUND
}