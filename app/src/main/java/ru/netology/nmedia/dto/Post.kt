package ru.netology.nmedia.dto

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val postName: String,
    val postData: String,
    val postText: String,
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    var shares: Int = 0,
    val video: String? = null
)