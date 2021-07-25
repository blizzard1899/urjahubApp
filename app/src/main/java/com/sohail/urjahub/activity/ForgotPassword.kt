package com.sohail.urjahub.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.sohail.urjahub.R


class ForgotPassword : AppCompatActivity() {
    lateinit var etResetEmail: EditText
    lateinit var btnResetPassword: Button
    private lateinit var resetAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        etResetEmail = findViewById(R.id.etResetEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        resetAuth = FirebaseAuth.getInstance()
        btnResetPassword.setOnClickListener {
            val email = etResetEmail.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT).show()
            } else {
                resetAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPassword,
                                "Check email to reset your password!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@ForgotPassword,
                                "Fail to send reset password email!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}
