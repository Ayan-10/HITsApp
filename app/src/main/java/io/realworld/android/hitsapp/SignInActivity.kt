package io.realworld.android.hitsapp

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import io.realworld.android.hitsapp.Models.User
import io.realworld.android.hitsapp.databinding.ActivitySignInBinding
import io.realworld.android.hitsapp.databinding.ActivitySignUpBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var dialog : ProgressDialog
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        dialog = ProgressDialog(this)
        dialog.setTitle("Logging in Account")
        dialog.setMessage("Loading...")

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        binding.btnSignin.setOnClickListener {
            dialog.show()
            auth.signInWithEmailAndPassword(
                    binding.edEmail.text.toString(),
                    binding.edPassword.text.toString()
            ).addOnCompleteListener {
                dialog.dismiss()
                if (it.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, it.exception?.message , Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
        binding.btnGoogle.setOnClickListener { signIn() }

        binding.tvNoAccount.setOnClickListener { v: View? ->
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        if(auth.currentUser!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private val RC_SIGN_IN = 65
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        val users : User? = null
                        users?.userID = user?.uid
                        users?.userName = user?.displayName
                        users?.profilePic = user?.photoUrl.toString()
                        user?.let { database.reference.child("Users").child(it.uid).setValue(users) }


                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
//                        updateUI(null)
                    }
                }
    }
}