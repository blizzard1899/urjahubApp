package com.sohail.urjahub.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sohail.urjahub.R


class Login : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var emailLogIn: EditText
    lateinit var passwordLogIn: EditText
    lateinit var btnLogIn: Button
    lateinit var btnEmailRegister: Button
    lateinit var txtForgotPassword: TextView
    lateinit var lLGoogleRegister : LinearLayout
    private val RC_SIGN_IN = 9001
    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailLogIn = findViewById(R.id.et_Email)
        passwordLogIn = findViewById(R.id.et_Password)
        btnLogIn = findViewById(R.id.btn_Log_In)
        btnEmailRegister = findViewById(R.id.btn_Email)
        lLGoogleRegister = findViewById(R.id.lL_btn_Google)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtForgotPassword.setOnClickListener {
            val intentForgotPassword = Intent(this, ForgotPassword::class.java)
            startActivity(intentForgotPassword)
            finish()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        btnEmailRegister.setOnClickListener {
            val intentRegister = Intent(this, Register::class.java)
            startActivity(intentRegister)
        }
        lLGoogleRegister.setOnClickListener {
            googleSignIn()
        }
        btnLogIn.setOnClickListener {

            val email_logIn = emailLogIn.text.toString()
            val password_LogIn = passwordLogIn.text.toString()
            if (email_logIn.isEmpty()) {
                emailLogIn.error = "Enter email"
            }
            if (password_LogIn.isEmpty()) {
                passwordLogIn.error = "Enter password"
            }
            if (email_logIn.isNotEmpty() && password_LogIn.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email_logIn, password_LogIn)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Log In successful", Toast.LENGTH_SHORT).show()
                            val intentLogIn = Intent(this, MainActivity::class.java)
                            startActivity(intentLogIn)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Error :" + (task.exception?.message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
        }

    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>?) {
        try {
            val account = completedTask?.getResult(
                ApiException::class.java
            )
            val googleId = account?.id ?: ""
            Log.i("Google ID", googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

        } catch (e: ApiException) {
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intentGoogleSignIn = Intent(this@Login, MainActivity::class.java)
                startActivity(intentGoogleSignIn)
                finish()
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val intentCurrentUser = Intent(this@Login, MainActivity::class.java)
            startActivity(intentCurrentUser)
            finish()
        }
    }

}