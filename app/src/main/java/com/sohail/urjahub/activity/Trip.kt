package com.sohail.urjahub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.sohail.urjahub.R

class Trip : AppCompatActivity() {
    lateinit var txtDistance: TextView
    lateinit var txtSpeed: TextView
    lateinit var txtCharge: TextView
    lateinit var txtTemperature: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        txtDistance = findViewById(R.id.et_Distance)
        txtSpeed = findViewById(R.id.et_Speed)
        txtCharge = findViewById(R.id.et_Charge)
        txtTemperature = findViewById(R.id.et_Temperature)
        val root: DatabaseReference = FirebaseDatabase.getInstance().reference
        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var temp: String = snapshot.child("temparature").value.toString()
                var dist: String = snapshot.child("distance").value.toString()
                var speed: String = snapshot.child("speed").value.toString()
                var battery: String = snapshot.child("battery_reading").value.toString()
                txtTemperature.text = temp
                txtCharge.text = battery
                txtDistance.text = dist
                txtSpeed.text = speed
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Trip, "Error Fetching data", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}