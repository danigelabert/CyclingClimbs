package com.danigelabert.cyclingclimbs.ui

import com.google.firebase.firestore.PropertyName

data class User(
    @get:PropertyName("edat") val edat: String = "",
    @get:PropertyName("email") val email: String = "",
    @get:PropertyName("image") val image: String? = null,
    @get:PropertyName("nom") val nom: String = "",
    @get:PropertyName("pes") val pes: String = "",
    @get:PropertyName("provider") val provider: String = ""
)
