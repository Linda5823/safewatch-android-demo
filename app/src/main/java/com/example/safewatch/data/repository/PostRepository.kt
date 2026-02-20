package com.example.safewatch.data.repository

import android.content.Context
import com.example.safewatch.data.local.db.DatabaseProvider
import com.example.safewatch.data.mapper.toDomain
import com.example.safewatch.data.mapper.toEntity
import com.example.safewatch.data.mapper.toDomain as postToDomain
import com.example.safewatch.data.remote.api.RetrofitProvider
import com.example.safewatch.domain.model.Incident

class PostRepository(
    context: Context
) {
    private val dao = DatabaseProvider.get(context).incidentDao()

    /**
     * Cache-first:
     * 1) Try DB
     * 2) If empty, fetch network and cache
     */
    suspend fun getIncidentsCacheFirst(): List<Incident> {
        val cached = dao.getAll().map { it.toDomain() }
        if (cached.isNotEmpty()) return cached

        val fresh = fetchFromNetwork()
        dao.upsertAll(fresh.map { it.toEntity() })
        return fresh
    }

    /**
     * Force refresh from network, update DB, return latest.
     */
    suspend fun refreshIncidents(): List<Incident> {
        val fresh = fetchFromNetwork()
        dao.clearAll()
        dao.upsertAll(fresh.map { it.toEntity() })
        return fresh
    }

    private suspend fun fetchFromNetwork(): List<Incident> {
        return RetrofitProvider.api
            .getPosts()
            .map { it.postToDomain() } // 你之前的 PostDto.toDomain(): Incident
    }
}




//  ------------------3st version:----------------------


//class PostRepository {
//
//    suspend fun fetchIncidents(): List<Incident> {
//        return RetrofitProvider.api
//            .getPosts()
//            .map { it.toDomain() }
//    }
//}

//  ------------------2nd version:----------------------

//class PostRepository {
//
//    suspend fun fetchPosts(): List<PostDto> {
//        return RetrofitProvider.api.getPosts()
//    }
//}