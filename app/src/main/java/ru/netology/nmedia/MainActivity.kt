package ru.netology.nmedia

import android.os.Bundle
import android.view.View.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }
        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }
        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.postText
                setText(content)
                if (content != null) {
                    requestFocus()
                    showKeyboard()
                    setSelection(content.length)
                    with(binding.groupEdit) { //группа отображений для редактирования поста
                        visibility = VISIBLE
                    }
                    with(binding.textEditText) { //текст редактируемого поста
                        text = content
                    }
                    with(binding.cancelEditButton) { //отмена редактирования поста
                        setOnClickListener {
                            viewModel.onSaveButtonClicked(content)
                        }
                    }
                } else {
                    with(binding.groupEdit) {
                        visibility = GONE
                    }
                    clearFocus()
                    hideKeyboard()
                }
            }
        }
    }
}