package com.example.ocbcassignment.presentation.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.ocbcassignment.R
import com.example.ocbcassignment.base.BaseActivity
import com.example.ocbcassignment.base.BaseResult
import com.example.ocbcassignment.data.model.feature.request.TransferRequest
import com.example.ocbcassignment.databinding.ActivityTransferBinding
import com.example.ocbcassignment.domain.model.Payees
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel


class TransferActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityTransferBinding
    private lateinit var amountTextField: TextInputEditText
    private lateinit var descriptionTextField: TextInputEditText
    private lateinit var payeesSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        amountTextField = binding.amountEdt
        descriptionTextField = binding.descriptionEdt
        payeesSpinner = binding.payeesSpinner

        initObserver()

        binding.transferBtn.setOnClickListener {
            if (validateForms()) {
                amountTextField.error = null
                descriptionTextField.error = null
                viewModel.doTransfer(
                    TransferRequest(
                        binding.amountEdt.text.toString().toInt(),
                        binding.descriptionEdt.text.toString(),
                        transferDestination.accountNo.toString()
                    )
                )
            }
        }

        binding.payeesSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                if (position == 0) {
                    (view as TextView).setTextColor(Color.GRAY)
                } else {
                    (view as TextView).setTextColor(Color.BLACK)
                    transferDestination = parent?.selectedItem as Payees
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        viewModel.checkPayees()
    }

    private fun validateForms(): Boolean {
        if (amountTextField.text.isNullOrBlank()) {
            amountTextField.error = getString(R.string.invalid_amount)
            return false
        }
        if (descriptionTextField.text.isNullOrBlank()) {
            descriptionTextField.error = getString(R.string.invalid_description)
            return false
        }
        if (payeesSpinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select recipient", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private var transferDestination: Payees = Payees()

    private fun initObserver() {
        viewModel.transfer.observe(this) {
            when (it) {
                is BaseResult.Loading -> {
                    binding.progressBar.root.visibility = View.VISIBLE
                }
                is BaseResult.Success -> {
                    binding.progressBar.root.visibility = View.GONE
                    Toast.makeText(this,
                        "Transfer to ${transferDestination.name} success",
                        Toast.LENGTH_SHORT).show()
                    binding.amountEdt.clearFocus()
                    binding.descriptionEdt.clearFocus()
                    binding.amountEdt.setText("")
                    binding.descriptionEdt.setText("")
                }
                is BaseResult.Error -> {
                    Toast.makeText(this, "Insufficient balance", Toast.LENGTH_LONG).show()
                    binding.progressBar.root.visibility = View.GONE
                }
                is BaseResult.Empty -> {
                    binding.progressBar.root.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.root.visibility = View.GONE
                }
            }
        }

        viewModel.payess.observe(this) {
            when (it) {
                is BaseResult.Loading -> {
                    binding.progressBar.root.visibility = View.VISIBLE
                }
                is BaseResult.Success -> {
                    val items = ArrayList<Payees>()
                    items.add(Payees(name = "Select recipient"))
                    items.addAll(it.data)
                    setPayeesSpinnerData(items)
                }
                is BaseResult.Error -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_LONG).show()
                    binding.progressBar.root.visibility = View.GONE
                }
                is BaseResult.Empty -> {
                    binding.progressBar.root.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.root.visibility = View.GONE
                }
            }
        }
    }

    private fun setPayeesSpinnerData(data: List<Payees>) {
        val adapter: ArrayAdapter<Payees> = object : ArrayAdapter<Payees>(this,
            android.R.layout.simple_spinner_dropdown_item,
            data) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup,
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }
        }
        binding.payeesSpinner.adapter = adapter
        binding.progressBar.root.visibility = View.GONE
    }
}