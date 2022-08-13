package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.util.focusAndShowKeyboard

class NewPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postText = intent.getStringExtra(POST_CONTENT_EXTRA_KEY)
        val url = intent.getStringExtra(POST_VIDEO_EXTRA_KEY)
        binding.contentEditText.setText(postText)
        binding.contentEditText.focusAndShowKeyboard()
        binding.videoUrl.setText(url)

        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.contentEditText.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.contentEditText.text.toString()
                val videoURL = binding.videoUrl.text.toString()
                intent.putExtra(POST_CONTENT_EXTRA_KEY, content)
                intent.putExtra(POST_VIDEO_EXTRA_KEY, videoURL)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }

    private companion object {
        private const val POST_CONTENT_EXTRA_KEY = "postContent"
        private const val POST_VIDEO_EXTRA_KEY = "newVideo"

    }

    class PostResult(
        val newContent: String?,
        val newVideo: String?
    )

    object ResultContract : ActivityResultContract<PostResult?, PostResult?>() {

        override fun createIntent(context: Context, input: PostResult?): Intent {
            val intent = Intent(context, NewPostActivity::class.java)
            intent.putExtra(POST_CONTENT_EXTRA_KEY, input?.newContent)
            intent.putExtra(POST_VIDEO_EXTRA_KEY, input?.newVideo)
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): PostResult? =
            if (resultCode == Activity.RESULT_OK) {
                PostResult(
                    newContent = intent?.getStringExtra(POST_CONTENT_EXTRA_KEY),
                    newVideo = intent?.getStringExtra(POST_VIDEO_EXTRA_KEY)
                )
            } else null
    }
}