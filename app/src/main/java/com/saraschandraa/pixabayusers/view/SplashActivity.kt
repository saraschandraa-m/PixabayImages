package com.saraschandraa.pixabayusers.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saraschandraa.pixabayusers.R
import com.saraschandraa.pixabayusers.databinding.ActivitySplashBinding
import com.saraschandraa.pixabayusers.util.PrefManagerHelper
import com.saraschandraa.pixabayusers.util.PrefManagerKeys

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (PrefManagerHelper(applicationContext).getBoolean(PrefManagerKeys.IS_USER_LOGGED_IN)) {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        } else {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}