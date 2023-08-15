package com.collections.billz.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.collections.billz.databinding.ActivitySignupBinding
import com.collections.billz.models.LoginRequest
import com.collections.billz.models.LoginResponse
import com.collections.billz.utils.Constants
import com.collections.billz.viewmodel.LoginUserViewModel

class Signup : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    val loginuserViewModel: LoginUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.btLogin.setOnClickListener {
            validateSignUp()
            clearErrorOnType()

        }
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        loginuserViewModel.regLiveData.observe(this) { regResponse ->
            persistLogin(regResponse)
            binding.btLogin.visibility = View.GONE
            Toast.makeText(this, regResponse.message, Toast.LENGTH_LONG).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
//            binding.pbProgressbar.visibility = View.GONE
        }

        loginuserViewModel.errorLiveData.observe(this) { err ->
            binding.btLogin.visibility = View.GONE
            Toast.makeText(this, err, Toast.LENGTH_LONG).show()
//            binding.pbProgressbar2.visibility = View.GONE
        }

    }

    fun validateSignUp() {

        val emailaddress = binding.etEmail.text.toString()
        val password = binding.etPassword1.text.toString()
        var error = false

        if (emailaddress.isBlank()) {
            error = true
            binding.etEmail.error = "Email is required"
        } else {
            binding.tilEmail.error = null
        }

        if (password.isBlank()) {
            error = true
            binding.etPassword1.error = "Password is required"
        } else {
            binding.tilPassword1.error = null
        }
        if (!error) {
            Toast.makeText(this, "The $emailaddress matches the account", Toast.LENGTH_LONG).show()
        }
        if (!error) {
            binding.btLogin.visibility = View.GONE
            var loginRequest = LoginRequest(
                email = emailaddress,
                password = password
            )
//        binding.pbProgressbar2.visibility = View.VISIBLE
            loginuserViewModel.loginUser(loginRequest)
        }

    }

    fun persistLogin(loginResponse: LoginResponse) {
//        initialize key-value pair file that is accessible everywhere in the app: name BILLZ_PREFS
        val sharedPrefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("USER_ID", loginResponse.userId)
        editor.putString("ACCESS_TOKEN", loginResponse.accessToken)
        editor.apply()
    }

    fun clearErrorOnType() {
        binding.tilEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilEmail.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }


}




