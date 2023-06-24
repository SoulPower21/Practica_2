package com.example.practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var auth = FirebaseAuth.getInstance()

        val txtDNI = findViewById<EditText>(R.id.txtDNI)
        val txtCLAVE = findViewById<EditText>(R.id.txtCLAVE)
        val btnLogin = findViewById<Button>(R.id.btnIngreso)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("usuarios")

        btnCrearCuenta.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val dni = txtDNI.text.toString()
            val clave = txtCLAVE.text.toString()

            auth
            collectionRef
                .whereEqualTo("dni", dni)
                .whereEqualTo("clave", clave)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val querySnapshot = task.result
                        if (querySnapshot != null && !querySnapshot.isEmpty) {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Inicio de sesión exitoso",
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Credenciales inválidas",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Error al iniciar sesión",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}