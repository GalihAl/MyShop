package com.galihalgiffar.myshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.galihalgiffar.myshop.R
import com.galihalgiffar.myshop.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        binding.btnSubmit.setOnClickListener (this
        )


    }
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon_white_24)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_submit -> {
                    val email: String = binding.tiEmail.text.toString().trim { it <= ' ' }
                    if (email.isEmpty()) {
                        Toast.makeText(
                            this,
                            "please enter your email",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.fpProgressbar.isVisible = true
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                binding.fpProgressbar.isVisible = false
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "email send successfully to reset your password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
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
        }
    }
}