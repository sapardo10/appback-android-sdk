package com.appback.appbacksdk.poko.toggle

import com.google.gson.annotations.SerializedName

data class BaseToggleResponse(
    @SerializedName("toggles") val toggles: List<Toggle>
)