package com.codenesia.githubuser.data

import com.google.gson.annotations.SerializedName

data class FollowResponseItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

)
