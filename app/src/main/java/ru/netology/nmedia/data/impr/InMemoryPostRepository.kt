package ru.netology.nmedia.data.impr

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.countLiked
import ru.netology.nmedia.dto.countShared

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        Post(
            id = 0L,
            postName = "Нетология. Университет интернет-профессий",
            postData = "12.07.2022",
            postText = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен → https://netolo.gy/fyb"
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
        }
        val likedPost = currentPost.copy(
            likes = currentPost.likes,
            likedByMe = !currentPost.likedByMe,
            countLikeFormat = countLiked(currentPost.likes, !currentPost.likedByMe)
        )
        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
        }
        val sharedPost = currentPost.copy(
            shares = currentPost.shares,
            countShareFormat = countShared(currentPost.shares)
        )
        ++sharedPost.shares
        data.value = sharedPost
    }

}