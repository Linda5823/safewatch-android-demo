package com.example.safewatch.data.remote.api

import com.example.safewatch.data.remote.dto.GenericDto
import retrofit2.http.GET

interface GenericApi {

    @GET("posts")
    suspend fun getItems(): List<GenericDto>
}
