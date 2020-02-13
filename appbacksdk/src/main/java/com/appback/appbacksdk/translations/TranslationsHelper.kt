package com.appback.appbacksdk.translations

import com.appback.appbacksdk.database.TranslationDao
import com.appback.appbacksdk.network.AppbackApi
import com.appback.appbacksdk.poko.transalation.Translation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TranslationsHelper(
    val api: AppbackApi,
    private val translationDao: TranslationDao,
    var router: String
) {

    val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

    fun loadTranslations() {
        GlobalScope.launch {
            val translationsResponse = api.loadTranslations(router)
            val translations = translationsResponse.translations
            for (translation: Translation in translations) {
                translation.key += "-$router"
            }
            translationDao.insertAll(translations)
        }
    }

    suspend fun getTranslations(localRouter: String? = null): List<Translation> {
        val databaseRouter = if (localRouter.isNullOrBlank()) {
            "-$router"
        } else {
            "-$localRouter"
        }
        var translations = translationDao.getAllByRouter(databaseRouter)
        if (translations.isEmpty()) {
            val translationsResponse = api.loadTranslations(router)
            val translationsList = translationsResponse.translations
            for (translation: Translation in translationsList) {
                translation.key += "-$router"
            }
            translationDao.insertAll(translationsList)
            translations = translationsList
        }

        return translations

    }

    /**
    fun getTranslations(): List<Translation> {
    GlobalScope.launch {
    val translationsResponse = api.loadTranslations(router)
    val translations = translationsResponse.translations
    for (translation: Translation in translations) {
    translation.key += "-$router"
    }
    translationDao.insertAll(translations)
    }
    }
     **/

    suspend fun getTranslation(key: String): Translation {
        return translationDao.findTranslationAsync("$key-$router")
    }
}
