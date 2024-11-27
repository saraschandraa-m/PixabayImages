package com.saraschandraa.pixabayusers.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saraschandraa.pixabayusers.R
import com.saraschandraa.pixabayusers.databinding.ActivityRegisterBinding
import com.saraschandraa.pixabayusers.model.User
import com.saraschandraa.pixabayusers.util.PrefManagerHelper
import com.saraschandraa.pixabayusers.util.PrefManagerKeys
import com.saraschandraa.pixabayusers.viewmodel.LoginRegisterViewModel
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    private val emailRegexObserver = Observer<Boolean> { isError ->
        if (isError) {
            binding.tilRegisterEmail.error = getString(R.string.register_email_invalid)
        } else {
            binding.tilRegisterEmail.isErrorEnabled = false
        }
    }

    private val emailExistsObserver = Observer<Boolean> { emailExists ->
        if (emailExists) {
            binding.tilRegisterEmail.error = getString(R.string.register_email_exists)
        } else {
            binding.tilRegisterEmail.isErrorEnabled = false
        }
    }

    private val processObserver = Observer<Boolean> { isSuccess ->
        if (isSuccess) {
            MaterialAlertDialogBuilder(this@RegisterActivity)
                .setMessage(getString(R.string.register_successful))
                .setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, _ ->
                    PrefManagerHelper(applicationContext).putBoolean(PrefManagerKeys.IS_USER_LOGGED_IN, true)
                    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                    finish()
                    dialog!!.dismiss()
                }.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginRegisterViewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]
        loginRegisterViewModel.emailPatternError.observe(this, emailRegexObserver)
        loginRegisterViewModel.emailExistsError.observe(this, emailExistsObserver)
        loginRegisterViewModel.processSuccess.observe(this, processObserver)

        binding.ivRegisterBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.etRegisterEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty()) {
                    binding.tilRegisterEmail.isErrorEnabled = false
                }
            }
        })

        binding.etRegisterDOB.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnSignup.setOnClickListener {
            clearAllErrors()
            if (binding.etRegisterName.text.toString().isEmpty()) {
                binding.tilRegisterName.error = getString(R.string.register_name_empty)
            } else if (binding.etRegisterEmail.text.toString().isEmpty()) {
                binding.tilRegisterEmail.error = getString(R.string.register_email_empty)
            } else if (binding.etRegisterPassword.text.toString().isEmpty()) {
                binding.tilRegisterPassword.error = getString(R.string.register_password_empty)
            } else if (binding.etRegisterPassword.text.toString().length < 6) {
                binding.tilRegisterPassword.error = getString(R.string.register_password_length)
            } else if (binding.etRegisterConfirmPassword.text.toString().isEmpty()) {
                binding.tilRegisterConfirmPassword.error =
                    getString(R.string.register_confirm_password_empty)
            } else if (binding.etRegisterPassword.text.toString() != binding.etRegisterConfirmPassword.text.toString()) {
                binding.tilRegisterConfirmPassword.error =
                    getString(R.string.register_password_mismatch)
            } else if (binding.etRegisterDOB.text.toString().isEmpty()) {
                binding.tilRegisterDOB.error = getString(R.string.register_dob_empty)
            } else {
                val user = User(
                    name = binding.etRegisterName.text.toString(),
                    email = binding.etRegisterEmail.text.toString(),
                    password = binding.etRegisterPassword.text.toString(),
                    dob = binding.etRegisterDOB.text.toString()
                )
                loginRegisterViewModel.register(user)
            }
        }
    }


    private fun clearAllErrors() {
        binding.tilRegisterName.isErrorEnabled = false
        binding.tilRegisterEmail.isErrorEnabled = false
        binding.tilRegisterPassword.isErrorEnabled = false
        binding.tilRegisterConfirmPassword.isErrorEnabled = false
        binding.tilRegisterDOB.isErrorEnabled = false
    }

    private fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val months = arrayOf(
            "JAN",
            "FEB",
            "MAR",
            "APR",
            "MAY",
            "JUN",
            "JUL",
            "AUG",
            "SEP",
            "OCT",
            "NOV",
            "DEC"
        )


        val maxDateCalendar = Calendar.getInstance()
        maxDateCalendar.add(Calendar.YEAR, -18)
        maxDateCalendar.add(Calendar.DATE, -1)

        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.YEAR, -99)


        val dialog = DatePickerDialog(
            this@RegisterActivity,
            { _, selectedYear, selectedMonth, selectedDay ->
                run {
                    val selectedDate =
                        selectedDay.toString().plus("-").plus(months[selectedMonth]).plus("-")
                            .plus(selectedYear.toString())
                    binding.etRegisterDOB.setText(selectedDate)
                }
            },
            year,
            month,
            day
        )

        dialog.datePicker.maxDate = maxDateCalendar.timeInMillis
        dialog.datePicker.minDate = minDateCalendar.timeInMillis

        dialog.show()
    }
}