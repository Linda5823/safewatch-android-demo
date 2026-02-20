package com.example.safewatch.data.repository

import android.content.Context
import com.example.safewatch.data.local.db.DatabaseProvider
import com.example.safewatch.data.mapper.toDomain
import com.example.safewatch.data.mapper.toEntity
import com.example.safewatch.data.mapper.toDomain as postToDomain
import com.example.safewatch.data.remote.api.RetrofitProvider
import com.example.safewatch.domain.model.Incident

class IncidentRepository(
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
            .map { it.postToDomain() } // previous PostDto.toDomain(): Incident
    }

    suspend fun getIncidentsPageCacheFirst(page: Int, pageSize: Int): List<Incident> {
        val offset = (page - 1) * pageSize

        // 1) Read the DB page first
        val cachedPage = dao.getPage(limit = pageSize, offset = offset).map { it.toDomain() }
        if (cachedPage.isNotEmpty()) return cachedPage

        // 2) This page is not in the database → Try fetching from the network (currently, refreshIncidents is a full fetch, which is fine)

        // For simplicity: here we directly call refresh or fetchFromNetwork to write the entire database once, and then read the page

        // (We can change this to real server-side pagination when  use a real backend later)
        val freshAll = fetchFromNetwork()
        dao.clearAll()
        dao.upsertAll(freshAll.map { it.toEntity() })

        return dao.getPage(limit = pageSize, offset = offset).map { it.toDomain() }
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