package com.ptrk.inverter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ptrk.inverter.R
import kotlinx.android.synthetic.main.fragment_home.*


//private const var root: View? = null
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    companion object {
    }

    private fun getData() {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("data")


        val query = docRef.orderBy("datetime", Query.Direction.DESCENDING).limit(1)
        query.get().addOnSuccessListener { snapshots ->
            val first = snapshots.documents[0]
            powerText.text = first.getString("power") + "W"
            energyText.text = first.getString("todayEnergy") + "kWh"
            dateText.text = first.getString("datetime")
        }

    }

}