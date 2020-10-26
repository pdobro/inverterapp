package com.ptrk.inverter.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ptrk.inverter.R
import kotlinx.android.synthetic.main.fragment_date.*
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DateFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_date, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var monthStr: String
        var dayStr: String

        buttonPickDate.setOnClickListener {
            val dateDialog = DatePickerDialog(
                context!!,
                DatePickerDialog.OnDateSetListener { view, myear: Int, mmonth: Int, mday: Int ->
                    if (mmonth < 9) {
                        monthStr = "0" + (mmonth + 1)
                    } else {
                        monthStr = "" + (mmonth + 1)
                    }
                    if (mday < 10) {
                        dayStr = "0$mday"
                    } else {
                        dayStr = "" + mday
                    }

                    val dateString = "$monthStr.$dayStr.$myear"
                    selectedDate.text = dateString
                    getDailyData(dateString)
                },
                year,
                month,
                day
            )
            dateDialog.datePicker.maxDate = Date().time
            dateDialog.show()
        }
    }

    companion object {

    }

    @SuppressLint("SetTextI18n")
    private fun getDailyData(date: String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("data")


        val query = docRef.whereGreaterThanOrEqualTo("datetime", date).whereLessThanOrEqualTo(
            "datetime",
            date + '\uf8ff'
        ).orderBy("datetime", Query.Direction.DESCENDING).limit(1)
        query.get().addOnSuccessListener { snapshots ->
            if (snapshots.documents.size > 0) {
                val first = snapshots.documents[0]
                dateEnergy.text = first.getString("todayEnergy") + "kWh"
            } else
                dateEnergy.text = "brak danych"

        }

    }
}