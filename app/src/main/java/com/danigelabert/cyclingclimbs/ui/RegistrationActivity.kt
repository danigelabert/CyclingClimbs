package com.danigelabert.cyclingclimbs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danigelabert.cyclingclimbs.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val nombre = binding.nombreEditText.text.toString()
            val apellido = binding.apellidoEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && nombre.isNotEmpty() && apellido.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = hashMapOf(
                                "email" to email,
                                "nombre" to nombre,
                                "apellido" to apellido
                            )

                            firestore.collection("users")
                                .document(email)
                                .set(user)
                                .addOnSuccessListener {
                                    funcionsBasiques.showDialog(5, "Usuario registrado exitosamente", this)
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    funcionsBasiques.showDialog(3, "Error al guardar la información del usuario", this)
                                }
                        } else {
                            funcionsBasiques.showDialog(3, "Error en el registro: ${task.exception?.message}", this)
                        }
                    }
            } else {
                funcionsBasiques.showDialog(2, "Completa todos los campos", this)
            }
        }
    }
}