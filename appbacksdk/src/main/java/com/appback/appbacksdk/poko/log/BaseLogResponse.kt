package com.appback.appbacksdk.poko.log

import com.google.gson.annotations.SerializedName

data class BaseLogResponse(
    @SerializedName("code") val code: Int
)