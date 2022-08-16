package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListBinding
import ru.netology.nmedia.dto.Post

class PostAdapter(
    private val interactionListener: PostInteractionListener

) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostListBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onDeleteClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.like.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }
            binding.options.setOnClickListener {
                popupMenu.show()
            }
            binding.video.setOnClickListener {
                listener.onVideoClicked(post)
            }
            binding.buttonPlay.setOnClickListener {
                listener.onVideoClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                postName.text = post.postName
                postData.text = post.postData
                postText.text = post.postText
                like.text = post.likes.toString()
                like.isChecked = post.likedByMe
                like.setBackgroundColor(android.R.drawable.btn_default)
                share.text = post.shares.toString()
                share.isChecked = false
                videoGroup.visibility =
                    if (post.video.isNullOrBlank()) View.GONE else View.VISIBLE
            }
        }
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}