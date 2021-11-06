package tech.gamedev.motorolasolutions.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import tech.gamedev.motorolasolutions.data.models.User
import tech.gamedev.motorolasolutions.databinding.UserItemBinding

class UserAdapter(
    private val users: List<User>,
    private val glide: RequestManager,
    private val listener: UserClickedListener
    ): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        with(holder) {
            initialize(user)
            glide.load(user.profileImage).into(binding.ivProfileImage)
            binding.tvFullName.text = user.fullName
            binding.tvEmail.text = user.email
        }

    }

    override fun getItemCount() = users.size

    inner class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun initialize(user: User) {
            itemView.setOnClickListener {
                listener.onUserClickedListener(user)
            }

            itemView.setOnLongClickListener {
                listener.onLongUserClickedListener(user)
                return@setOnLongClickListener true
            }
        }
    }

    interface UserClickedListener {
        fun onUserClickedListener(user: User)
        fun onLongUserClickedListener(user: User)
    }
}