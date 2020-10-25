package com.ptrk.inverter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ptrk.inverter.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_record.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getRecord() {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("data")


        val queryPower = docRef.orderBy("power", Query.Direction.DESCENDING).limit(1).whereLessThan("power", "250")
        queryPower.get().addOnSuccessListener { snapshots ->
            val greatestPower = snapshots.documents[0]
            val b = greatestPower.getString("power")
            val c = greatestPower.getString("datetime")
            powerRecord.text = b + " W"
            if (c != null) {
                powerRecordDate.text =  c.take(10)
            }
        }

        val queryEnergy = docRef.orderBy("todayEnergy", Query.Direction.DESCENDING).limit(1).whereLessThan("todayEnergy", "2.0")
        queryEnergy.get().addOnSuccessListener { snapshots ->
            val gratestEnergy = snapshots.documents[0]
            val a = gratestEnergy.getString("todayEnergy")
            val b = gratestEnergy.getString("datetime")
            prodRecord.text = a + " kWh"
            if (b != null) {
                prodRecordDate.text =  b.take(10)
            }
        }

    }
}