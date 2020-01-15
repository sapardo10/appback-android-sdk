package com.appback.appbacksdk.exceptions

import com.appback.appbacksdk.AppbackConstants.ROUTE_NOT_DEFINED

class RouterNotDefinedException : Exception() {
    override val message: String = ROUTE_NOT_DEFINED
}