package com.saraschandraa.pixabayusers.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saraschandraa.pixabayusers.R
import com.saraschandraa.pixabayusers.databinding.ActivityLoginBinding
import com.saraschandraa.pixabayusers.util.PrefManagerHelper
import com.saraschandraa.pixabayusers.util.PrefManagerKeys
import com.saraschandraa.pixabayusers.viewmodel.LoginRegisterViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    private val processObserver = Observer<Boolean> { isSuccess ->

        val message = if (isSuccess) {
            getString(R.string.login_successful)
        } else {
            getString(R.string.login_failed)
        }

        MaterialAlertDialogBuilder(this@LoginActivity)
            .setMessage(
                message
            )
            .setPositiveButton(
                getString(R.string.ok)
            ) { dialog, _ ->
                if (isSuccess) {
                    PrefManagerHelper(applicationContext).putBoolean(PrefManagerKeys.IS_USER_LOGGED_IN, true)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
                dialog!!.dismiss()
            }.show()

    }

    private val emailRegexObserver = Observer<Boolean> { isError ->
        if (isError) {
            binding.tilLoginEmail.error = getString(R.string.register_email_invalid)
        } else {
            binding.tilLoginEmail.isErrorEnabled = false
        }
    }

    private val emailExistsObserver = Observer<Boolean> { emailExists ->
        if (emailExists) {
            binding.tilLoginEmail.error = getString(R.string.login_email_not_exists)
        } else {
            binding.tilLoginEmail.isErrorEnabled = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginRegisterViewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]
        loginRegisterViewModel.emailPatternError.observe(this, emailRegexObserver)
        loginRegisterViewModel.emailExistsError.observe(this, emailExistsObserver)
        loginRegisterViewModel.processSuccess.observe(this, processObserver)

        binding.tvLoginSignup.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            clearAllErrors()
            if (binding.etLoginEmail.text.toString().isEmpty()) {
                binding.tilLoginEmail.error = getString(R.string.login_email_empty)
            } else if (binding.etLoginPassword.text.toString().isEmpty()) {
                binding.tilLoginPassword.error = getString(R.string.login_password_empty)
            } else {
                loginRegisterViewModel.login(
                    binding.etLoginEmail.text.toString(),
                    binding.etLoginPassword.text.toString()
                )
            }
        }
    }

    private fun clearAllErrors() {
        binding.tilLoginEmail.isErrorEnabled = false
        binding.tilLoginPassword.isErrorEnabled = false
    }
}