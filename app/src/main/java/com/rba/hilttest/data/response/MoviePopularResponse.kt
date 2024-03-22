package com.rba.hilttest.data.response

import com.google.gson.annotations.SerializedName

data class MoviePopularResponse(
    val page: Int?,
    @SerializedName("total_results") val totalResults: Int?,
    @SerializedName("total_pages") val totalPages: Int?,
    val results: List<MoviePopularItemResponse>?
)