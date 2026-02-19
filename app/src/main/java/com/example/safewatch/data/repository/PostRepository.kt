package com.example.safewatch.data.repository

import com.example.safewatch.data.remote.api.RetrofitProvider
import com.example.safewatch.data.remote.dto.PostDto

class PostRepository {

    suspend fun fetchPosts(): List<PostDto> {
        return RetrofitProvider.api.getPosts()
    }
}