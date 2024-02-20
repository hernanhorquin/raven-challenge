package com.raven.home.data.remote.service

import com.raven.home.data.remote.model.HomeArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {

    @GET("svc/mostpopular/v2/viewed/{period}.json")
    suspend fun getNews(@Path("period") period: Int): HomeArticlesResponse
}
