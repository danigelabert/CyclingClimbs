package com.danigelabert.cyclingclimbs.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danigelabert.cyclingclimbs.R
import com.google.firebase.auth.FirebaseAuth
import com.danigelabert.cyclingclimbs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signInButton.setBackgroundColor(Color.rgb(137,80,11));
        binding.signInButton.setTextColor(Color.WHITE);

        binding.signInButton.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(binding.emailEditText.text.toString(),
                        binding.passwordEditText.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MapActivity::class.java)
                            intent.putExtra("user", binding.emailEditText.text.toString())
                            startActivity(intent)
                        } else {
                            funcionsBasiques.showDialog(3, "Se ha producido un error autenticando al usuario", this)
                        }
                    }
            } else {
                funcionsBasiques.showDialog(2, "Completa todos los campos", this)
            }
        }

        binding.registerLink.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}