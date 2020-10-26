package com.ptrk.inverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ptrk.inverter.fragments.DateFragment
import com.ptrk.inverter.fragments.HomeFragment
import com.ptrk.inverter.fragments.RecordFragment
import kotlinx.android.synthetic.main.activity_main.*

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
        }



    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {

        replace(R.id.fl_wrapper, fragment)
        commit()
        }


}