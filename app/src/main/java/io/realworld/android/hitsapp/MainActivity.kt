package io.realworld.android.hitsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.realworld.android.hitsapp.adapter.FragmentAdapter
import io.realworld.android.hitsapp.databinding.ActivityMainBinding
import io.realworld.android.hitsapp.databinding.ActivitySignInBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.viewPager.adapter = FragmentAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater

        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.settings -> Toast.makeText(this,"In process",Toast.LENGTH_SHORT).show()
            R.id.log_out -> {
                auth.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }

        return true
    }
}