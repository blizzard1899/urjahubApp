package com.sohail.urjahub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sohail.urjahub.R

class Register : AppCompatActivity() {
    lateinit var registerName: EditText
    lateinit var registerAge: EditText
    lateinit var registerEmail: EditText
    lateinit var registerPassword: EditText
    lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var firebaseAuth = FirebaseAuth.getInstance()
        registerName = findViewById(R.id.et_Name)
        registerAge = findViewById(R.id.et_Age)
        registerEmail = findViewById(R.id.et_Email_reg)
        registerPassword = findViewById(R.id.et_Password_reg)
        btnRegister = findViewById(R.id.btn_Register)
        btnRegister.setOnClickListener {
            val intentUser = Intent(this, Login::class.java)
            startActivity(intentUser)
            var email = registerEmail.text.toString()
            var name = registerName.text.toString()
            var age = registerAge.text.toString()
            var password = registerPassword.text.toString()
            if ((email.isEmpty())) {
                registerEmail.error = "Enter email"
            }
            if ((name.isEmpty())) {
                registerName.error = "Enter name"
            }
            if ((age.isEmpty())) {
                registerAge.error = "Enter age"
            }
            if ((password.isEmpty())) {
                registerEmail.error = "Enter password"
            }
            if (name.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "createUserwithEmail:success", Toast.LENGTH_SHORT)
                                .show()
                            val user = firebaseAuth.currentUser

                        } else {
                            Toast.makeText(
                                this,
                                "error in signing up" + (task.exception?.message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}