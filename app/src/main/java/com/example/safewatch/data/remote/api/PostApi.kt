package com.example.safewatch.data.remote.api


import com.example.safewatch.data.remote.dto.PostDto
import retrofit2.http.GET

interface PostApi {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}