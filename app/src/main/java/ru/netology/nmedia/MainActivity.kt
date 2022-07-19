package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.countLiked
import ru.netology.nmedia.dto.countShared

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val post = Post(
            id = 0L,
            postName = "Нетология. Университет интернет-профессий",
            postData = "12.07.2022",
            postText = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен → https://netolo.gy/fyb"
        )

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.render(post)

        binding.like.setOnClickListener {
            post.likedByMe = !post.likedByMe
            val imageResId = if (post.likedByMe)R.drawable.ic_red_like else R.drawable.ic_like //замена картинки кнопки лайка
            val countLikeFormats = countLiked(post.likes, post.likedByMe) //счетчик и форматирование лайков
            binding.like.setImageResource(imageResId)
            binding.countLike.text = countLikeFormats.toString()
        }

        binding.share.setOnClickListener {
            val countShareFormats = countShared(post.shares)
            ++post.shares
            binding.countShare.text = countShareFormats.toString()
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        postName.text = post.postName
        postData.text = post.postData
        postText.text = post.postText
        countLike.text = post.countLikeFormat.toString()
        countShare.text = post.countShareFormat.toString()
    }
}