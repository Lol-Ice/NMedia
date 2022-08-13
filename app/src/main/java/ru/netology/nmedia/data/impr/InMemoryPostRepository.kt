package ru.netology.nmedia.data.impr

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.countLiked
import ru.netology.nmedia.dto.countShared

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                postName = "Нетология. Университет интернет-профессий",
                postData = "07.08.2022",
                postText = "Привет, это новая Нетология!",
                video = "https://www.youtube.com/watch?v=4IIjp2mljbs&ab_channel=KOSMO"
            )
        }
    )

    override fun like(postId: Long) {
        posts = posts.map {
            if (it.id == postId) it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes,
                countLikeFormat = countLiked(it.likes, !it.likedByMe)
            )
            else it
        }
    }

    override fun share(postId: Long) {
        posts = posts.map {
            if (it.id == postId) it.copy(
                shares = ++it.shares,
                countShareFormat = countShared(it.shares)
            )
            else it
        }
    }

    override fun deletePost(postId: Long) {
        posts = posts.filter {
            it.id != postId
        }
    }

    override fun savePost(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(
                id = ++nextId
            )
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 10
    }
}