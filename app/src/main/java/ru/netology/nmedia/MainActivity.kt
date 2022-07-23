package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    private fun <T> viewModel(): T {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this, { post ->
            binding.render(post)

            binding.like.setOnClickListener {
                viewModel.onLikeClicked()
            }
            binding.share.setOnClickListener {
                viewModel.onShareClicked()
            }
        })
    }

    private fun ActivityMainBinding.render(post: Post) {
        postName.text = post.postName
        postData.text = post.postData
        postText.text = post.postText
        countLike.text = post.countLikeFormat.toString()
        countShare.text = post.countShareFormat.toString()
        like.setImageResource(getLikeIconRes(post.likedByMe))
    }
}

@DrawableRes
private fun getLikeIconRes(liked: Boolean) =
    if (liked) R.drawable.ic_red_like else R.drawable.ic_like