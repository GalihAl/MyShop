package com.galihalgiffar.myshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.galihalgiffar.myshop.R
import com.galihalgiffar.myshop.databinding.ActivityRegisterBinding
import com.galihalgiffar.myshop.firestore.FirestoreClass
import com.galihalgiffar.myshop.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        binding.tvLogin.setOnClickListener {
            onBackPressed()
        }

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon_black_24)
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.tvFirstName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_firstname),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            TextUtils.isEmpty(binding.tvLastName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_lastname),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            TextUtils.isEmpty(binding.tvEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_emailId),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            TextUtils.isEmpty(binding.tvPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_password),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            TextUtils.isEmpty(binding.tvCofirmPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_confirmpassword),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            binding.tvPassword.text.toString()
                .trim { it <= ' ' } != binding.tvCofirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_password_and_confirmpassword_mistmatch),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            !binding.checkBox.isChecked -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_checlbox_term_and_condition),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            else -> {
                true
            }
        }
    }
    fun userRegistrationSuccess() {
        binding.progressBar.isVisible = false

        Toast.makeText(
            this,
            "Your registration is successfully",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun hideProgressBar(){
        binding.progressBar.isVisible = false
    }

    private fun registerUser() {
        if (validateRegisterDetails()) {
            val email: String = binding.tvEmail.text.toString().trim(){it <= ' '}
            val password: String = binding.tvPassword.text.toString().trim(){it <= ' '}
            binding.progressBar.isVisible = true

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                binding.tvFirstName.text.toString().trim { it <= ' ' },
                                binding.tvLastName.text.toString().trim { it <= ' ' },
                                binding.tvEmail.text.toString().trim { it <= ' ' }
                            )

                            FirestoreClass().registerUser(this, user)

                            Toast.makeText(this@RegisterActivity,"You resigtered successfully.", Toast.LENGTH_SHORT).show()
                            binding.progressBar.isVisible = false
                            
                            /*FirebaseAuth.getInstance().signOut() //signout from firebase account and back to resume screen before
                            finish()*/

                        } else {
                            Toast.makeText(this,task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                            binding.progressBar.isVisible = false
                        }
                    }
                )
        }
    }
}