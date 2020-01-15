package com.appback.appbacksdk.logs

import com.appback.appbacksdk.database.LogEventDao
import com.appback.appbacksdk.network.AppbackApi
import com.appback.appbacksdk.poko.log.LogEvent

class LogsHelper(
    val api: AppbackApi,
    val logEventDao: LogEventDao,
    var router: String
) {

    suspend fun sendLog(
        name: String,
        description: String,
        level: Int
    ) {
        val result = api.logEvent(
            router, name, description, level
        )
        if (result.code != 200) {
            logEventDao.insert(
                LogEvent(
                    name = name,
                    description = description,
                    level = level
                )
            )
        }
    }
}