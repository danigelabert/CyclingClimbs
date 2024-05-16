package com.danigelabert.cyclingclimbs.ui

import CommentAdapter
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danigelabert.cyclingclimbs.R
import com.danigelabert.cyclingclimbs.databinding.ActivityDetallePuntoBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DetallePuntoActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityDetallePuntoBinding
    private lateinit var CommentAdapter: CommentAdapter
    private lateinit var userRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePuntoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")
        val username = intent.getStringExtra("user")
        val user = username?.substringBefore('@')

        binding.tvTitulo.text = titulo
        binding.tvDescripcion.text = descripcion


        binding.btnEnviarComentario.setBackgroundColor(Color.rgb(137,80,11));
        binding.btnEnviarComentario.setTextColor(Color.WHITE);



        userRecyclerView = findViewById(R.id.rvSecondList)
        CommentAdapter = CommentAdapter(emptyList())
        userRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DetallePuntoActivity)
            adapter = CommentAdapter
        }

        loadComments(firestore, titulo ?: "")

        cargarImagen(titulo ?: "")

        binding.btnEnviarComentario.setOnClickListener {
            val comentario = binding.etComentario.text.toString()
            if (comentario.isNotEmpty()) {
                agregarComentario(titulo ?: "", comentario, user.toString())
                binding.etComentario.text.clear()
            } else {
                Toast.makeText(this, "Por favor, ingresa un comentario", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun loadComments(db: FirebaseFirestore, titulo: String) {
        db.collection(titulo)
            .get()
            .addOnSuccessListener { result ->
                CommentAdapter.list = emptyList()
                val users = result.toObjects(Comentario::class.java)
                CommentAdapter.list = users
                CommentAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                funcionsBasiques.showDialog(1, exception.message.toString(), this)
            }
    }


    private fun cargarImagen(titulo: String) {
        firestore.collection("ports").document(titulo).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val imagenUrl = document.getString("imagenUrl")
                    if (!imagenUrl.isNullOrEmpty()) {
                        Picasso.get().load(imagenUrl).into(binding.ivImagen)
                    } else {
                        Toast.makeText(
                            this,
                            "No se encontró la URL de la imagen",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    private fun agregarComentario(titulo: String, comentario: String, nombreUsuario: String) {
        val comentariosRef =
            firestore.collection(titulo)
        val nuevoComentario = Comentario(
            usuario = nombreUsuario,
            comentario = comentario,
            fecha = System.currentTimeMillis().toString()
        )

        comentariosRef.add(nuevoComentario)
            .addOnSuccessListener {

                loadComments(firestore, titulo)
            }
            .addOnFailureListener { exception ->
                funcionsBasiques.showDialog(1, exception.message.toString(), this)
            }
    }
    }

