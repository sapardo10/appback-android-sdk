package com.appback.appbacksdk.toggles

import com.appback.appbacksdk.database.ToggleDao
import com.appback.appbacksdk.network.AppbackApi
import com.appback.appbacksdk.poko.toggle.Toggle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TogglesHelper(
    val api: AppbackApi,
    private val toggleDao: ToggleDao,
    var router: String
) {

    fun loadToggles() {
        GlobalScope.launch {
            val togglesResponse = api.loadToggles(router)
            val toggles = togglesResponse.toggles
            for (toggle: Toggle in toggles) {
                toggle.key += "-$router"
            }
            toggleDao.insertAll(toggles)
        }
    }

    suspend fun getToggles(localRouter: String? = null): List<Toggle> {
        val databaseRouter = if (localRouter.isNullOrBlank()) {
            "-$router"
        } else {
            "-$localRouter"
        }
        var toggles = toggleDao.getAllByRouter(databaseRouter)
        if (toggles.isEmpty()) {
            val togglesResponse = api.loadToggles(router)
            val togglesList = togglesResponse.toggles
            for (toggle: Toggle in togglesList) {
                toggle.key += "-$router"
            }
            toggleDao.insertAll(togglesList)
            toggles = togglesList
        }

        return toggles

    }

    suspend fun getToogle(key: String): Toggle {
        return toggleDao.findToggleAsync("$key-$router")
    }
}

