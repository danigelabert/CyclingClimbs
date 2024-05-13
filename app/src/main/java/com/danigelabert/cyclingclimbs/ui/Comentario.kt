package com.danigelabert.cyclingclimbs.ui

import com.google.firebase.firestore.PropertyName

data class Comentario(
    @get:PropertyName("usuario") val usuario: String = "",
    @get:PropertyName("comentario") val comentario: String = "",
    @get:PropertyName("fecha") val fecha: String? = ""
)