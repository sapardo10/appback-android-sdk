package com.appback.appbacksdk

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.appback.appbacksdk.AppbackConstants.BASE_URL
import com.appback.appbacksdk.AppbackConstants.DATABASE_NAME
import com.appback.appbacksdk.callbacks.OnToggleSearched
import com.appback.appbacksdk.callbacks.OnTranslationSearched
import com.appback.appbacksdk.database.AppbackDatabase
import com.appback.appbacksdk.exceptions.RouterNotDefinedException
import com.appback.appbacksdk.exceptions.TranslationNotFoundException
import com.appback.appbacksdk.logs.LogsHelper
import com.appback.appbacksdk.network.AppbackApi
import com.appback.appbacksdk.network.AuthenticationInterceptor
import com.appback.appbacksdk.poko.AccessToken
import com.appback.appbacksdk.poko.transalation.Translation
import com.appback.appbacksdk.toggles.TogglesHelper
import com.appback.appbacksdk.translations.TranslationsHelper
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class Appback private constructor(context: Context) {

    protected val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )

    private var w1: Deferred<*>? = null


    private var api: AppbackApi
    private var database: AppbackDatabase
    private var logRouter: String? = null
    private var translationsHelper: TranslationsHelper? = null
    private var togglesHelper: TogglesHelper? = null
    private var logsHelper: LogsHelper? = null
    private var apiKey = ""
    private var token: AccessToken? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create<AppbackApi>(
            AppbackApi::class.java
        )

        database = Room.databaseBuilder(
            context,
            AppbackDatabase::class.java, DATABASE_NAME
        ).build()
    }

    companion object : SingletonHolder<Appback, Context>(::Appback)

    fun configure(
        translationRouter: String? = null,
        toggleRouter: String? = null,
        logRouter: String? = null,
        apiKey: String? = null
    ) {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("error", throwable.stackTrace.toString())
        }
        w1 = scope.async {
            if (apiKey != null) {
                this@Appback.apiKey = apiKey

                val localToken = token
                try {
                    token = withContext(Dispatchers.Default) {
                        api.getToken(apiKey)
                    }

                    val httpClient = OkHttpClient.Builder()
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    val client =
                        httpClient
                            .addInterceptor(AuthenticationInterceptor(token!!.accessToken))
                            .addInterceptor(logging)
                            .build()

                    val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    api = retrofit.create<AppbackApi>(
                        AppbackApi::class.java
                    )
                } catch (e: Exception) {
                    token = localToken
                    e.printStackTrace()
                }
            }
            translationRouter?.let {
                translationsHelper =
                    TranslationsHelper(api, database.translationDao(), translationRouter)
                translationsHelper?.loadTranslations()
            }

            toggleRouter?.let {
                togglesHelper =
                    TogglesHelper(api, database.toggleDao(), it)
                togglesHelper?.loadToggles()
            }

            logRouter?.let {
                logsHelper =
                    LogsHelper(api, database.logEventDao(), it)
            }
        }


    }

    @Throws(RouterNotDefinedException::class, TranslationNotFoundException::class)
    suspend fun getTranslation(key: String): Translation {
        val translation = translationsHelper?.getTranslation(key)
        if (translation != null) {
            return translation
        } else {
            throw TranslationNotFoundException()
        }
    }

    @Throws(RouterNotDefinedException::class, TranslationNotFoundException::class)
    fun getTranslation(key: String, callback: OnTranslationSearched) {
        scope.launch {
            w1?.await()
            val translation = withContext(Dispatchers.Default) {
                translationsHelper?.getTranslation(key)
            }

            Log.d("tahg", "")
            if (translation != null) {
                callback.onTranslationFound(translation)
            } else {
                callback.onTranslationNotFount(key)
            }
        }
    }

    @Throws(RouterNotDefinedException::class)
    fun getToggle(key: String, callback: OnToggleSearched) {
        scope.launch {
            w1?.await()
            val toggle = withContext(Dispatchers.Default) {
                togglesHelper?.getToogle(key)
            }
            if (toggle != null) {
                callback.onToggleFound(toggle)
            } else {
                callback.onToggleNotFound(key)
            }
        }

    }

    @Throws(RouterNotDefinedException::class)
    fun sendLog(name: String, description: String, level: Int) {
        scope.launch {
            w1?.await()
            logsHelper?.sendLog(name, description, level)

        }
    }
}