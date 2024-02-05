package com.danigelabert.cyclingclimbs.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danigelabert.cyclingclimbs.databinding.ActivityAuthBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Analytics Event
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de FireBase completa")
        analytics.logEvent("InitScreen", bundle)

        //Setup
        setup()
    }

    private fun setup() {
        title = "Autenticación"

        binding.signUpButton.setOnClickListener{
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(binding.emailEditText.text.toString(),
                        binding.passwordEditText.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                            } else {
                                showAlert()
                            }
                    }

            }
        }

        binding.logOutButton.setOnClickListener{
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(binding.emailEditText.text.toString(),
                        binding.passwordEditText.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }

            }
        }
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticado al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply{
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}