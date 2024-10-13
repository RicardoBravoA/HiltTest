package com.rba.hilttest.domain.mapper

import com.rba.hilttest.data.response.MoviePopularItemResponse
import com.rba.hilttest.domain.model.MovieModel

fun MoviePopularItemResponse.toDomain() = MovieModel(id, title)