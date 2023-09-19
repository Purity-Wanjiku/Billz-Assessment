package com.collections.billz.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModel
import com.collections.billz.R
import com.collections.billz.databinding.ActivityHomeBinding
import com.collections.billz.viewmodel.BillsViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    val billsViewModel : BillsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        setupBottomNav()
        binding.btAdddBill.setOnClickListener {
            val intent = Intent(this, AddBillActivity::class.java)
            startActivity(intent)
            setupBottomNav()

        }
        billsViewModel.createUpcomingBills()
    }
    fun setupBottomNav(){
        binding.bnvHome.setOnItemSelectedListener{ menuItem->
            when (menuItem.itemId){
                R.id.summary -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fcvHome, com.collections.billz.ui.SummaryFragment())
                        .commit()
                    true
                }
                R.id.upcoming -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fcvHome, com.collections.billz.ui.UpcomingBillFragment())
                        .commit()
                    true
                }
                R.id.paid -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fcvHome, com.collections.billz.ui.PaidBillFragment())
                        .commit()
                    true
                }

                R.id.settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fcvHome, com.collections.billz.ui.SettingsFragment())
                        .commit()
                    true
                }
                else -> false
            }

        }
    }
}