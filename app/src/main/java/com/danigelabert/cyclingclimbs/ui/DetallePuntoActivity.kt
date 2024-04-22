package com.danigelabert.cyclingclimbs.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danigelabert.cyclingclimbs.R
import com.danigelabert.cyclingclimbs.databinding.ActivityDetallePuntoBinding
import com.danigelabert.cyclingclimbs.databinding.ActivityLoginBinding
import com.danigelabert.cyclingclimbs.databinding.ActivityMapBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DetallePuntoActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityDetallePuntoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePuntoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Obtener los extras del Intent
        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")

        // Mostrar el título y la descripción en los TextView correspondientes
        binding.tvTitulo.text = titulo
        binding.tvDescripcion.text = descripcion

        // Consultar Firestore para obtener la URL de la imagen del punto
        firestore.collection("ports").document(titulo ?: "").get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val imagenUrl = document.getString("imagenUrl")
                    if (!imagenUrl.isNullOrEmpty()) {
                        // Utilizar Picasso para cargar la imagen desde la URL en el ImageView
                        Picasso.get().load(imagenUrl).into(binding.ivImagen)
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Manejar cualquier error al obtener los detalles del punto
            }
    }
}