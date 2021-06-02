package io.realworld.android.hitsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.realworld.android.hitsapp.Models.User
import io.realworld.android.hitsapp.R
import io.realworld.android.hitsapp.adapter.ChatItemAdapter
import io.realworld.android.hitsapp.databinding.FragmentChatsBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatsFragment : Fragment() {


    lateinit var binding: FragmentChatsBinding
    lateinit var list: ArrayList<User>
    lateinit var database: FirebaseDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance()
        list = ArrayList()
        val adapter = context?.let { ChatItemAdapter(list, it) }
        binding.chatRecyclerView.adapter = adapter

        val linearLayoutManager = LinearLayoutManager(context)
        binding.chatRecyclerView.layoutManager = linearLayoutManager

        database.reference.child("Users").addValueEventListener (object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) run {
                    val user = item.getValue(User::class.java)
                    user?.userID = item.key
                    if (user != null) {
                        list.add(user)
                    }
                }
                adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return binding.root
    }

}