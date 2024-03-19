package com.danigelabert.cyclingclimbs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danigelabert.cyclingclimbs.R

class UserAdapter(var userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userNameTextView)
        val userEmail: TextView = itemView.findViewById(R.id.userEmailTextView)
        // Agrega más vistas si es necesario para mostrar más datos del usuario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.userName.text = currentUser.nom
        holder.userEmail.text = currentUser.email
        // Configura más datos del usuario si es necesario
    }

    override fun getItemCount() = userList.size
}
