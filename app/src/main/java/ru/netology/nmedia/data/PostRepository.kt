package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    val data: LiveData<List<Post>>

    fun like(postId: Long)

    fun share(postId: Long)
    abstract fun deletePost(id: Long)
    abstract fun savePost(post: Post)
}
