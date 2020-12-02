package com.ptrk.inverter.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ptrk.inverter.R
import kotlinx.android.synthetic.main.fragment_record.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RecordFragment : Fragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRecord()
    }
    companion object {

    }

    @SuppressLint("SetTextI18n")
    private fun getRecord() {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("data")


        val queryPower = docRef.orderBy("power", Query.Direction.DESCENDING).limit(1).whereLessThan("power", "250")
        queryPower.get().addOnSuccessListener { snapshots ->
            val greatestPower = snapshots.documents[0]
            if (snapshots.documents.size > 0) {
                val b = greatestPower.getString("power")
                val c = greatestPower.getString("datetime")
                powerRecord.text = "$b W"
                if (c != null) {
                    powerRecordDate.text = c.take(10)
                }
            }
        }

        val queryEnergy = docRef.orderBy("todayEnergy", Query.Direction.DESCENDING).limit(1).whereLessThan("todayEnergy", "2.0")
        queryEnergy.get().addOnSuccessListener { snapshots ->

            val greatestEnergy = snapshots.documents[0]
            if (snapshots.documents.size > 0) {
                val a = greatestEnergy.getString("todayEnergy")
                val b = greatestEnergy.getString("datetime")
                prodRecord.text = "$a kWh"
                if (b != null) {
                    prodRecordDate.text = b.take(10)
                }
            }

        }

    }
}