package com.galihalgiffar.myshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.galihalgiffar.myshop.R
import com.galihalgiffar.myshop.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener(this)
        binding.tvForgetpassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)


    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.tv_register -> {
                    val mIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(mIntent)
                }
                R.id.tv_forgetpassword -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)

                }
                R.id.btn_login -> {
                    loginRegisteredUser()

                }
            }
        }

    }

    private fun validateLoginDetail(): Boolean {
        return when {
            TextUtils.isEmpty(binding.tvEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_emailId),
                    Toast.LENGTH_SHORT
                    ).show()
                false
            }
            TextUtils.isEmpty(binding.tvPassword.text . toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_msg_password),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            else -> {
                true
            }
        }
    }

    private fun loginRegisteredUser() {
        if (validateLoginDetail()) {
            binding.progressBar.isVisible = true

            val email = binding.tvEmail.text.toString().trim{it <= ' '}
            val password = binding.tvPassword.text.toString().trim{it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    binding.progressBar.isVisible = false

                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "you are loggin is successfull.",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

        }
    }


}