package com.appback.exampleapplication

import android.app.Application
import com.appback.appbacksdk.Appback

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Appback.getInstance(baseContext).configure(
            translationRouter = "appname",
            toggleRouter = "colombia-toogle",
            logRouter = "colombia-logger",
            apiKey = "j3yu2VlGvLVFJYt0AZ0B5Q95d7ZoFjecmfySHL4TED3xYJ5AZvlvR4qKoiDu"
        )
    }
}