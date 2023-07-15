package com.collections.billz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.collections.billz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding= ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }

        override fun onResume() {
            super.onResume()
//            displayContacts()

            binding.btSubmit.setOnClickListener {
                val intent = Intent(this, Signup::class.java)
                startActivity(intent)
            }

            validateSignUp()
            clearErrorOnType()
        }


    fun validateSignUp() {

        val username = binding.etUsername.text.toString()
        val phone = binding.etPhonenumber.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        var error = false

        if (username.isBlank()) {
            binding.etUsername.error = "Username is required"
            error = true
        } else {
            binding.tilUsername.error = null
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
            Toast.makeText(this, "$username $email $phone", Toast.LENGTH_LONG).show()
        }
    }

    fun clearErrorOnType() {
        binding.tilUsername.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilUsername.error = null
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
}

