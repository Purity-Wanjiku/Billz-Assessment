package com.collections.billz.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GestureDetectorCompat
import com.collections.billz.databinding.ActivityMainBinding
import com.collections.billz.models.RegisterRequest
import com.collections.billz.utils.Constants
import com.collections.billz.viewmodel.UserViewModel

class MainActivity : AppCompatActivity(){
        lateinit var binding: ActivityMainBinding
        val userViewModel : UserViewModel by viewModels()

      public override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(
                savedInstanceState)
            binding= ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            redirectUser()

        }

        override fun onResume() {
            super.onResume()
            binding.btSubmit.setOnClickListener {
                validateSignUp()
                clearErrorOnType()
            }

            userViewModel.regLiveData.observe(this) { regResponse ->
                Toast.makeText(this, regResponse.message, Toast.LENGTH_LONG).show()
                startActivity(Intent(this, HomeActivity::class.java))
                binding.pbProgressbar.visibility = View.GONE
            }

            userViewModel.errorLiveData.observe(this) { err ->
                Toast.makeText(this, err, Toast.LENGTH_LONG).show()
                binding.pbProgressbar.visibility = View.GONE
            }

            binding.tvLogin.setOnClickListener{
                startActivity(Intent(this, Signup::class.java))
            }
        }

    fun validateSignUp() {

        val firstName = binding.etFirstname.text.toString()
        val lastName = binding.etLastname.text.toString()
        val phone = binding.etPhonenumber.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val passwordConf = binding.etPasswordconf.text.toString()

        var error = false

        if (firstName.isBlank()) {
            binding.etFirstname.error = "First name is required"
            error = true
        } else {
            binding.tilFirstname.error = null
        }
        if (lastName.isBlank()) {
            binding.etLastname.error = "First name is required"
            error = true
        } else {
            binding.tilLastname.error = null
        }

        if (email.isBlank()) {
            binding.etEmail.error = "Email is required"
            error = true
        }
        else {
            binding.tilEmail.error = null
        }

        if (phone.isEmpty()) {
            binding.etPhonenumber.error = "Phone Number is required"
            error = true
        } else {
            binding.tilPhonenumber.error = null
        }
        if (password.isBlank()) {
            binding.etPassword.error = "Password is required"
            error = true
        }
        else {
            binding.tilPassword.error = null
        }
        if (!error) {
            Toast.makeText(this, "$firstName $lastName $email $phone", Toast.LENGTH_LONG).show()
        }
        if (!password.equals(passwordConf)){
            error = true
            binding.tilPasswordConf.error = "Password and confirmation does not match"
        }
        if (!error){
            var registerRequest = RegisterRequest(

                phoneNumber = phone,
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            )
            binding.pbProgressbar.visibility = View.VISIBLE
            userViewModel.registerUser(registerRequest)
        }


    }
//clear all errors once i have sign up
    fun clearErrorOnType() {
        binding.tilFirstname.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilFirstname.error = null
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

    binding.tilLastname.editText?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.tilLastname.error = null
        }
        override fun afterTextChanged(s: Editable?) {
        }
    })
        binding.tilPhonenumber.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilPhonenumber.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

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
    fun redirectUser (){
        val prefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val userId = prefs.getString(Constants.USER_ID, Constants.EMPTY_STRING)!!
//        null assertion operator, making the empty string to not be nullable but just a string

        if (userId.isNotBlank()){

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
//add no-error function