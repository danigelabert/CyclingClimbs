package com.danigelabert.cyclingclimbs.ui

import android.app.AlertDialog
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class funcionsBasiques {
    companion object{
        fun showDialog(alert: Int, message: String, context: Context) {
            var titol = ""

            when (alert) {
                1 -> titol = "Error"
                2 -> titol = "Falten dades"
                3 -> titol = "Error extern"
                4 -> titol = "Dades errònies"
                5 -> titol = "Registrat"
            }

            AlertDialog.Builder(context)
                .setTitle(titol)
                .setMessage(message)
                .setPositiveButton("Aceptar", null)
                .show()
        }
}}


enum class ProviderType{
    BASIC
}

