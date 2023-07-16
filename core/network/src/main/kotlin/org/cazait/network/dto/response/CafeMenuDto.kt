package org.cazait.network.dto.response

import com.google.gson.annotations.SerializedName

data class CafeMenuDto(
    @SerializedName("menuId")
    val menuId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("imageUrl")
    val imageUrl: String
)