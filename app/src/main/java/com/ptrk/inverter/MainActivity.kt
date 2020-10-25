package com.ptrk.inverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ptrk.inverter.fragments.DateFragment
import com.ptrk.inverter.fragments.HomeFragment
import com.ptrk.inverter.fragments.RecordFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val recordFragment = RecordFragment()
        val dateFragment = DateFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnNavigationItemReselectedListener{
            when(it.itemId){
                R.id.home ->makeCurrentFragment(homeFragment)
                R.id.star ->makeCurrentFragment(recordFragment)
                R.id.date -> makeCurrentFragment(dateFragment)
            }
            true
        }


    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {

        replace(R.id.fl_wrapper, fragment)
        commit()
        }

/*    private fun getData( power: String, energy: String, date: String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("data")

*//*
        val power = findViewById<TextView>(R.id.powerText)
        val energy = findViewById<TextView>(R.id.energyText)
        val date = findViewById<TextView>(R.id.dateText)
*//*

        val query = docRef.orderBy("datetime", Query.Direction.DESCENDING).limit(2)
        query.get().addOnSuccessListener { snapshots ->
            val first = snapshots.documents[0]
            val a = first.getString("power")
            val b = first.getString("todayEnergy")
            val c = first.getString("datetime")
            power.text =  a + " W"
            energy.text = b + " kWh"
            date.text = c
        }

    }*/
}