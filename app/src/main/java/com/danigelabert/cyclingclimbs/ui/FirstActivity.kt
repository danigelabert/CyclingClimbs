package com.danigelabert.cyclingclimbs.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.danigelabert.cyclingclimbs.databinding.ActivityFirstBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    private lateinit var storageRef: StorageReference

    private var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore
        storageRef = FirebaseStorage.getInstance().getReference("Imatges")

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        binding.modificarButton.setOnClickListener{
            val homeIntent = Intent(this, HomeActivity::class.java).apply{
                putExtra("email", email)
                putExtra("provider", provider)
            }
            startActivity(homeIntent)
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.imageView.setImageURI(it)
            if (it != null){
                uri = it
            }
        }

        binding.findPictureButton.setOnClickListener{
            pickImage.launch("image/*")
        }

        binding.deleteButton.setOnClickListener{
            db.collection("users")
                .document(email.toString())
                .delete()
                .addOnSuccessListener {
                    funcionsBasiques.showAlert("Success", "Se ha eliminado correctamente los datos del usuario", this)
                }
                .addOnFailureListener { e ->
                    funcionsBasiques.showAlert("Error", "Se ha producido un error eliminando los datos del usuario", this)
                }
        }

        binding.insertButton.setOnClickListener{
            saveData(email.toString())
        }


    }

    private fun setup(email: String, provider: String) {
        binding.emailTV.text = email
        binding.providerTV.text = provider
    }

    private fun saveData(email: String) {
        val db = Firebase.firestore
        uri?.let {
            storageRef.putFile(it)
                .addOnSuccessListener {

                    Toast.makeText(this, " Image stored successfully",Toast.LENGTH_SHORT).show()
                    it.metadata!!.reference!!.downloadUrl
                        .addOnCompleteListener {
                            val imgUri = it.toString()
                            funcionsBasiques.addPhoto(db, email, imgUri, this)
                        }
                }
        }
    }

}