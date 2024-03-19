package com.danigelabert.cyclingclimbs.ui

import android.app.AlertDialog
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class funcionsBasiques {

    companion object{
        fun showAlert(type: String, msg: String, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(type)
            builder.setMessage(msg)
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        fun modifyUser(db: FirebaseFirestore, user: User, context: Context) {

            val usuari = hashMapOf(
                "email" to user.email,
                "provider" to user.provider,
                "nom" to user.nom,
                "edat" to user.edat,
                "pes" to user.pes,
                "image" to user.image
            )

            db.collection("users")
                .document(user.email)
                .set(usuari)
                .addOnSuccessListener {

                    funcionsBasiques.showAlert("Success", "Les dades s'han modificat correctament.", context)
                }
                .addOnFailureListener { e ->
                    funcionsBasiques.showAlert("Error", "S'ha produit un error al modificar les dades.", context)
                }
        }

        fun addPhoto(db: FirebaseFirestore, email: String, img: String, context: Context) {

            val doc = db.collection("users").document(email)

            doc.update("image", img)
                .addOnSuccessListener {

                }
                .addOnFailureListener{

                }
        }

        fun readUser(db: FirebaseFirestore, email: String, context: Context): User{
            var user = User("","","","","")
            db.collection("users")
                .document(email)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                         user = document.toObject(User::class.java)!!
                    }
                }
                .addOnFailureListener { e ->
                    showAlert("Error", "S'ha produit un error al llegir les dades.", context)
                }
            return user
        }
    }



}