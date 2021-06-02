package io.realworld.android.hitsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.realworld.android.hitsapp.ChatDetailsActivity
import io.realworld.android.hitsapp.Models.User
import io.realworld.android.hitsapp.R

class ChatItemAdapter(private val list: ArrayList<User>, private val context: Context):
        RecyclerView.Adapter<ChatItemAdapter.ViewHolder>() {




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var imageView : ImageView
        lateinit var textView: TextView
        lateinit var lastSms: TextView

        init {
            imageView = itemView.findViewById(R.id.profile_image)
            textView = itemView.findViewById(R.id.username)
            lastSms = itemView.findViewById(R.id.last_sms)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.chats_item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = list.get(position)
        Picasso.get().load(user.profilePic)
                .placeholder(R.drawable.baseline_face_black_24dp).into(holder.imageView)
        holder.textView.text = user.userName
        holder.lastSms.text = user.lastMessage

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatDetailsActivity::class.java)
            intent.putExtra("userId", user.userID)
            intent.putExtra("dp", user.profilePic)
            intent.putExtra("userName", user.userName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}