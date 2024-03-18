package com.danigelabert.cyclingclimbs.ui

data class User(val email: String,
                val provider: String,
                val nom: String,
                val edat: String,
                val pes: String,
                val img: String? = null)
