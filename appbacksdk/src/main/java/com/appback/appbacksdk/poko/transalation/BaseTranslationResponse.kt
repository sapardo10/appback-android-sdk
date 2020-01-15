package com.appback.appbacksdk.poko.transalation

import com.google.gson.annotations.SerializedName

data class BaseTranslationResponse(
    @SerializedName("translations") val translations: List<Translation>
)