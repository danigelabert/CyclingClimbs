package com.danigelabert.cyclingclimbs.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danigelabert.cyclingclimbs.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UsersActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        userRecyclerView = findViewById(R.id.userRecyclerView)
        userAdapter = UserAdapter(emptyList())
        userRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@UsersActivity)
            adapter = userAdapter
        }

        loadUsers()
    }

    private fun loadUsers() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val users = result.toObjects(User::class.java)
                userAdapter.userList = users
                userAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener los usuarios
            }
    }
}
