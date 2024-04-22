package com.danigelabert.cyclingclimbs.ui

import com.google.firebase.firestore.PropertyName

data class User(
    @get:PropertyName("email") val email: String = "",
    @get:PropertyName("image") val image: String? = null,
    @get:PropertyName("nom") val nom: String = "",
    @get:PropertyName("cognom") val cognom: String = "",
    @get:PropertyName("provider") val provider: String = ""
)


