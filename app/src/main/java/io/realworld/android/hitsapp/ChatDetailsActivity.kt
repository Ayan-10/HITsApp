package io.realworld.android.hitsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import io.realworld.android.hitsapp.Models.Message
import io.realworld.android.hitsapp.adapter.ChatAdapter
import io.realworld.android.hitsapp.databinding.ActivityChatDetailsBinding

class ChatDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatDetailsBinding
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val senderId = auth.uid
        val reciveId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("userName")
        val dp = intent.getStringExtra("dp")

        binding.username.text = username
        Picasso.get().load(dp).placeholder(R.drawable.baseline_face_black_24dp).into(binding.profileImage)

        binding.back.setOnClickListener {
            onBackPressed()
        }


        val messages = ArrayList<Message>()
        val chatAdapter = ChatAdapter(messages, this)
        binding.chatRecyclerView.adapter = chatAdapter

        val linearLayoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.layoutManager = linearLayoutManager
    }

}