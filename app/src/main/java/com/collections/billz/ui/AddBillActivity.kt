package com.collections.billz.ui

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.collections.billz.R
import com.collections.billz.databinding.ActivityAddBillBinding
import com.collections.billz.databinding.ActivityHomeBinding
import com.collections.billz.models.Bill
import com.collections.billz.utils.Constants
import com.collections.billz.viewmodel.BillsViewModel
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import java.util.Calendar
import java.util.UUID

class AddBillActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBillBinding
    val billsViewModel: BillsViewModel by viewModels()
    lateinit var context: Context
    val bitmapArray = ArrayList<Bitmap>()
    var selectedMonth = 0
    var selectedDate = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNav()
    }

    fun setupNav() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        setupFreqSpinner()

        binding.btSave.setOnClickListener {
            validateBill()
        }
    }


     fun setupFreqSpinner() {
        val frequencies = arrayOf(Constants.WEEKLY, Constants.MONTHLY, Constants.ANNUAL)
        val freqAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequencies)
        freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spFreq.adapter = freqAdapter

        binding.spFreq.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (binding.spFreq.selectedItem.toString()) {
                    Constants.WEEKLY -> {

                        setupDueDateSpinner(Array(7) { it + 1 })
                        hideDatePicker()
                    }

                    Constants.MONTHLY -> {
                        setupDueDateSpinner(Array(31) { it + 1 })
                        hideDatePicker()
                    }

                    Constants.ANNUAL -> {
                        showDatePicker()
                        setupDueDate()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    fun setupDueDateSpinner(dates: Array<Int>) {
        val duedateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dates)
        duedateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spDate.adapter = duedateAdapter
    }

    fun showDatePicker() {
        binding.dpduedate.show()
        binding.spDate.hide()
    }

    fun hideDatePicker() {
        binding.dpduedate.hide()
        binding.spDate.show()
    }

    fun setupDueDate() {
        val calendar = Calendar.getInstance()
        binding.dpduedate.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        { view, year, month, date ->
            selectedMonth = month + 1
            selectedDate = date
        }
    }

    fun validateBill() {
        val billName = binding.etBillName.text.toString()
        val billAmount = binding.etAmount.text.toString()
        val frequency = binding.tvFreqSpinner.text.toString()
        var dueDate = Constants.EMPTY_STRING
        if (frequency == Constants.ANNUAL) {

            var finalDate = selectedDate.toString()
            var finalMonth = selectedMonth.toString()
            if (selectedDate<10){
                finalDate = "0$selectedDate"
            }

            if (selectedMonth<10){
                finalMonth = "0$selectedMonth"
            }
            dueDate = "$finalDate/$finalMonth"
        } else {
            dueDate = binding.spDate.selectedItem.toString()
        }
        var error = false
        if (billName.isBlank()) {
            error = true
            binding.etBillName.error = " Bill name required"
        }
        if (billAmount.isBlank()) {
            error = true
            binding.etAmount.error = " Amount required"
        }
        if (!error) {
            val prefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
            val userId = prefs.getString(Constants.USER_ID, Constants.EMPTY_STRING)
            val bill = Bill(
                billId = UUID.randomUUID().toString(),
                name = billName,
                amount = billAmount.toDouble(),
                frequency = frequency,
                dueDate = dueDate,
                userId = userId.toString()
            )
            billsViewModel.saveBill(bill)
            resetForm()
        }
    }

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun resetForm() {
        binding.etBillName.setText(Constants.EMPTY_STRING)
        binding.etAmount.setText(Constants.EMPTY_STRING)
        binding.spFreq.setSelection(0)
        hideDatePicker()
        binding.spDate.setSelection(0)
    }
}