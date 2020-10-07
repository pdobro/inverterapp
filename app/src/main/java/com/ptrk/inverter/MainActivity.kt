package com.ptrk.inverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

    }

    private fun getData() {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("data")

        val power = findViewById<TextView>(R.id.powerText)
        val energy = findViewById<TextView>(R.id.energyText)
        val date = findViewById<TextView>(R.id.dateText)

        val query = docRef.orderBy("datetime", Query.Direction.DESCENDING).limit(2)
        query.get().addOnSuccessListener { snapshots ->
            val first = snapshots.documents[0]
            val a = first.getString("power")
            val b = first.getString("todayEnergy")
            val c = first.getString("datetime")
            power.text = a + " W"
            energy.text = b + " kWh"
            date.text = c
        }

    }
}