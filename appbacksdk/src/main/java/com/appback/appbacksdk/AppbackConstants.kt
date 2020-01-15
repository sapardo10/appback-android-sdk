package com.appback.appbacksdk

object AppbackConstants {
    const val BASE_URL = "https://appback.io/api/"
    const val DATABASE_NAME = "appback-database"

    //ERRORS
    const val ROUTE_NOT_DEFINED =
        "The route that you are trying to access has not been defined yet, make sure you called the call the configure method before trying to do this operation"
    const val TRANSLATION_NOT_FOUND = "We couldn't find the translation you are trying to find, review your router and key"
}