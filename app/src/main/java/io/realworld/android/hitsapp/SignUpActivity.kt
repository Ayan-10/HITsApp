package io.realworld.android.hitsapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import io.realworld.android.hitsapp.Models.User
import io.realworld.android.hitsapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var dialog : ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        dialog = ProgressDialog(this)
        dialog.setTitle("Creating Account")
        dialog.setMessage("Loading...")

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        binding.btnSignup.setOnClickListener { v: View? ->
            dialog.show()
            auth.createUserWithEmailAndPassword(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            ).addOnCompleteListener {
                dialog.dismiss()
                if (it.isSuccessful){
                    val user = User(binding.edUsername.text.toString(),
                    binding.edEmail.text.toString(), binding.edPassword.text.toString())

                    val id = it.result?.user?.uid
                    id?.let { it1 -> database.reference.child("Users").child(it1).setValue(user) }

                    Toast.makeText(this, "User created successfully",Toast.LENGTH_SHORT)
                            .show()
                }else{
                    Toast.makeText(this, it.exception?.message ,Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
        binding.tvHaveAccount.setOnClickListener { v: View? ->
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}