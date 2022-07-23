package ru.netology.nmedia.data

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    val data: MutableLiveData<Post>

    fun like()

    fun share()
}
