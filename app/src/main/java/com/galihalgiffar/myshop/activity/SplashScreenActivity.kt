package com.galihalgiffar.myshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.galihalgiffar.myshop.R


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        @Suppress("DEPRECATED")
        Handler().postDelayed(
            {
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                    finish()
            },
            1500
        )


    }
}