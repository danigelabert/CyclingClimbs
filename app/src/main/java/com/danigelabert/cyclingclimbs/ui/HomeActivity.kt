package com.danigelabert.cyclingclimbs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danigelabert.cyclingclimbs.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        //Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        binding.emailTextView.text=email
        binding.providerTextView.text=provider

        binding.modifyButton.setOnClickListener(){
            funcionsBasiques.modifyUser(db, User(
                binding.edatET.text.toString(),
                email.toString(),
                "",
                binding.nomET.text.toString(),
                binding.pesET.text.toString(),
                provider.toString()),
                this
            )
        }

    }




}