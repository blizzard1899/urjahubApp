package com.sohail.urjahub.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.sohail.urjahub.R
import com.sohail.urjahub.activity.MainActivity


class Settings : Fragment() {
    private lateinit var mGoogleSignOutClient: GoogleSignInClient
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var logOut: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignOutClient = GoogleSignIn.getClient(activity as Context, gso)
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        logOut = view.findViewById(R.id.btnLog_out)
        logOut.setOnClickListener {
            mGoogleSignOutClient.signOut().addOnCompleteListener {
                val intent = Intent(activity as Context, MainActivity::class.java)
                startActivity(intent)
                ActivityCompat.finishAffinity(activity as Activity)
            }
        }
        return view
    }

}