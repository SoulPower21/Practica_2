package com.example.practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.practica2.model.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val txtDni = findViewById<EditText>(R.id.txtdni)
        val txtFullName=findViewById<EditText>(R.id.txtfullname)
        val txtClave=findViewById<EditText>(R.id.txtclave)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("usuarios")

        btnRegistrar.setOnClickListener {
            val dni = txtDni.text.toString()
            val fullname = txtFullName.text.toString()
            val clave = txtClave.text.toString()
            val nuevoUsuario = Usuario(dni,fullname,clave)

            collectionRef.add(nuevoUsuario)
                .addOnSuccessListener { documentReference ->
                }.addOnFailureListener{error ->
                    Snackbar
                        .make(
                            findViewById(android.R.id.content),
                            "Error al registrar: $error",
                            Snackbar.LENGTH_LONG
                        ).show()
                }
            Snackbar
                .make(
                    findViewById(android.R.id.content),
                    "Registro exitoso",
                    Snackbar.LENGTH_LONG
                ).show()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}