package com.danigelabert.cyclingclimbs.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.danigelabert.cyclingclimbs.databinding.ActivityFirstBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var storageRef: StorageReference

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        storageRef = Firebase.storage.reference

        val bundle = intent.extras
        val email = bundle?.getString("email") ?: ""
        val provider = bundle?.getString("provider") ?: ""
        setup(email, provider)

        binding.modificarButton.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java).apply {
                putExtra("email", email)
                putExtra("provider", provider)
            }
            startActivity(homeIntent)
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.imageView.setImageURI(uri)
            this.uri = uri
        }

        binding.findPictureButton.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.deleteButton.setOnClickListener {
            deleteUser(email)
        }

        binding.insertButton.setOnClickListener {
            saveData(email)
        }

        binding.recycleButton.setOnClickListener{
            val homeIntent = Intent(this, UsersActivity::class.java)
            startActivity(homeIntent)
        }
    }

    private fun setup(email: String, provider: String) {
        binding.emailTV.text = email
        binding.providerTV.text = provider
    }

    private fun deleteUser(email: String) {
        db.collection("users")
            .document(email)
            .delete()
            .addOnSuccessListener {
                showAlert("Success", "Se ha eliminado correctamente los datos del usuario")
            }
            .addOnFailureListener { e ->
                showAlert("Error", "Se ha producido un error eliminando los datos del usuario: ${e.message}")
            }
    }

    private fun saveData(email: String) {
        uri?.let { uri ->
            val imageRef = storageRef.child("images/${UUID.randomUUID()}")
            val uploadTask = imageRef.putFile(uri)

            uploadTask.addOnSuccessListener { _ ->
                imageRef.downloadUrl.addOnCompleteListener { downloadUrlTask ->
                    if (downloadUrlTask.isSuccessful) {
                        val imgUri = downloadUrlTask.result.toString()
                        addPhoto(email, imgUri)
                        showAlert("Success", "Se ha subido correctamente la imagen")
                    } else {
                        showAlert("Error", "Error al obtener la URL de descarga: ${downloadUrlTask.exception?.message}")
                    }
                }
            }.addOnFailureListener { e ->
                showAlert("Error", "Error al subir la imagen: ${e.message}")
            }
        } ?: showAlert("Error", "La URI del archivo es nula")
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun addPhoto(email: String, imgUri: String) {
        val userRef = db.collection("users").document(email)

        userRef.update("photoUrl", imgUri)
            .addOnSuccessListener {
                showAlert("Success", "La imatge s'ha afegit correctement")
            }
            .addOnFailureListener { e ->
                showAlert("Error", "Error al añadir la URL de la imagen a Firestore: ${e.message}")
            }
    }
}