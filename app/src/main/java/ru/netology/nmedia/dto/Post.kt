package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val postName: String,
    val postData: String,
    val postText: String,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var countLikeFormat: Any = countLiked(likes, likedByMe),
    var shares: Int = 0,
    var countShareFormat: Any = countShared(shares)

)