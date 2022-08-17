package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {posts ->
            adapter.submitList(posts)
        }
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        viewModel.shareEvent.observe(viewLifecycleOwner) { post ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, post)
            }
            val shareIntent = Intent.createChooser(intent, "Поделиться: ")
            startActivity(shareIntent)
        }

        viewModel.navigateToVideo.observe(viewLifecycleOwner) { video ->
            val intent = Intent()
                .apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(video)
                }
            val videoIntent =
                Intent.createChooser(intent, "Видео: ")
            startActivity(videoIntent)
        }

        val activityLauncher = registerForActivityResult(
            NewPostFragment.ResultContract
        ) { PostResult ->
            PostResult?.newContent ?: return@registerForActivityResult
            viewModel.onSaveButtonClicked(PostResult.newContent, PostResult.newVideo)
        }
        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) {
            activityLauncher.launch(it)
        }
        return binding.root
    }
}
