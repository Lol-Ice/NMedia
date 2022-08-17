package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.focusAndShowKeyboard

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val intent = Intent()
        val postText = intent.getStringExtra(POST_CONTENT_EXTRA_KEY)
        val url = intent.getStringExtra(POST_VIDEO_EXTRA_KEY)

        binding.contentEditText.setText(postText)
        binding.contentEditText.focusAndShowKeyboard()
        binding.videoUrl.setText(url)

        binding.ok.setOnClickListener {

            if (binding.contentEditText.text.isNullOrBlank()) {
                activity?.setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.contentEditText.text.toString()
                val videoURL = binding.videoUrl.text.toString()
                intent.putExtra(POST_CONTENT_EXTRA_KEY, content)
                intent.putExtra(POST_VIDEO_EXTRA_KEY, videoURL)
                activity?.setResult(Activity.RESULT_OK, intent)
            }
            activity?.finish()
        }

        return binding.root
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
            val intent = Intent(context, NewPostFragment::class.java)
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