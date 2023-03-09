package com.codenesia.githubuser.api

import com.codenesia.githubuser.GithubResponse
import com.codenesia.githubuser.data.DetailUserResponse
import com.codenesia.githubuser.data.FollowResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser (
        @Query("q") username: String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username: String
    ) : Call<ArrayList<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<FollowResponseItem>>
}