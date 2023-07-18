package com.collections.billz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.collections.billz.databinding.ActivitySignupBinding
import com.collections.billz.ui.MainActivity

class Signup : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.btLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        validateSignUp()
        clearErrorOnType()
    }



fun validateSignUp() {

    val username = binding.etUsername1.text.toString()
    val password = binding.etPassword1.text.toString()
    var error = false

    if (username.isBlank()) {
        binding.etUsername1.error = "Username is required"
        error = true
    } else {
        binding.tilUsername1.error = null
    }

    if (password.isBlank()) {
        binding.etPassword1.error = "Password is required"
        error = true
    }
    else {
        binding.tilPassword1.error = null
    }
    if (!error) {
        Toast.makeText(this, "$username ", Toast.LENGTH_LONG).show()
    }
}

fun clearErrorOnType() {
    binding.tilUsername1.editText?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.tilUsername1.error = null
        }

        override fun afterTextChanged(s: Editable?) {

        }
    })

}
}




