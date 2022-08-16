package ru.netology.nmedia.data.impr

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import kotlin.properties.Delegates

class SharedPrefsPostRepository(
    application: Application
) : PostRepository {

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )

    private var nextId: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }

    private var posts
        get() = checkNotNull(data.value) {

        }
        set(value) {
            prefs.edit {
                val serializedPost = Json.encodeToString(value)
                putString(POST_PREFS_KEY, serializedPost)
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val serializedPosts = prefs.getString(POST_PREFS_KEY, null)
        val posts: List<Post> = if (serializedPosts != null) {
            Json.decodeFromString(serializedPosts)
        } else emptyList()
        data = MutableLiveData(posts)
    }

    override fun like(postId: Long) {
        posts = posts.map {
            if (it.id == postId) it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + if (it.likedByMe) -1 else +1
                //countLikeFormat = countLiked(it.likes, !it.likedByMe) as Int
            )
            else it
        }
    }

    override fun share(postId: Long) {
        posts = posts.map {
            if (it.id == postId) it.copy(
                shares = ++it.shares
                //countShareFormat = countShared(it.shares) as Int
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
        posts = listOf(
            post.copy(
                id = ++nextId
            )
        ) + posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val POST_PREFS_KEY = "posts"
        const val NEXT_ID_PREFS_KEY = "nextId"
    }
}