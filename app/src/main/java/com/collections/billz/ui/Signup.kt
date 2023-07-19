package com.collections.billz.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.collections.billz.HomeActivity
import com.collections.billz.databinding.ActivitySignupBinding
import com.collections.billz.models.RegisterRequest
import com.collections.billz.viewmodel.UserViewModel

class  Signup : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    val userViewModel : UserViewModel by viewModels()

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
        userViewModel.regLiveData.observe(this) { regResponse ->
            Toast.makeText(this, regResponse.message, Toast.LENGTH_LONG).show()
            startActivity(Intent(this, HomeActivity::class.java))
//            binding.pbProgressbar.visibility = View.GONE
        }

        userViewModel.errorLiveData.observe(this) { err ->
            Toast.makeText(this, err, Toast.LENGTH_LONG).show()
//            binding.pbProgressbar2.visibility = View.GONE
        }

    }



fun validateSignUp() {

    val emailaddress = binding.etEmail.text.toString()
    val password = binding.etPassword1.text.toString()
    var error = false

    if (emailaddress.isBlank()) {
        binding.etEmail.error = "Username is required"
        error = true
    } else {
        binding.tilEmail.error = null
    }

    if (password.isBlank()) {
        binding.etPassword1.error = "Password is required"
        error = true
    }
    else {
        binding.tilPassword1.error = null
    }
    if (!error) {
        Toast.makeText(this, "$emailaddress ", Toast.LENGTH_LONG).show()
    }
    if (!error){
        var registerRequest = RegisterRequest(
            email = emailaddress,
            password = password
        )
//        binding.pbProgressbar2.visibility = View.VISIBLE
        userViewModel.registerUser(registerRequest)
    }
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




