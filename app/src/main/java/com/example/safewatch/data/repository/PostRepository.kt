package com.example.safewatch.data.repository

import com.example.safewatch.data.remote.api.RetrofitProvider
import com.example.safewatch.data.remote.dto.PostDto


import com.example.safewatch.data.mapper.toDomain
import com.example.safewatch.domain.model.Incident

//class PostRepository {
//
//    suspend fun fetchPosts(): List<PostDto> {
//        return RetrofitProvider.api.getPosts()
//    }
//}

class PostRepository {

    suspend fun fetchIncidents(): List<Incident> {
        return RetrofitProvider.api
            .getPosts()
            .map { it.toDomain() }
    }
}