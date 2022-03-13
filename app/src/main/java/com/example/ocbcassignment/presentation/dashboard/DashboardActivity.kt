package com.example.ocbcassignment.presentation.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ocbcassignment.base.BaseActivity
import com.example.ocbcassignment.base.BaseResult
import com.example.ocbcassignment.databinding.ActivityDashboardBinding
import com.example.ocbcassignment.presentation.auth.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val viewModel: MainViewModel by viewModel()
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()

        viewModel.checkBalance()

        val loggedUser = authViewModel.getLoggedUserInfo()

        binding.accountHolderTv.text = loggedUser.username
        binding.accountNumberTv.text = loggedUser.accountNo

        binding.logoutBtn.setOnClickListener {
            authViewModel.clearLoginInformation()
            finish()
        }

        binding.transferBtn.setOnClickListener {
            val transferIntent = Intent(this, TransferActivity::class.java)
            startActivity(transferIntent)
        }

        binding.transactionsRv.apply {
            this.adapter = dataAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@DashboardActivity)
        }
    }

    private val dataAdapter: TransactionsAdapter by lazy {
        TransactionsAdapter()
    }

    private fun initObserver() {
        viewModel.balance.observe(this) {
            when (it) {
                is BaseResult.Loading -> {
                    binding.progressBar.root.visibility = View.VISIBLE
                }
                is BaseResult.Success -> {
                    binding.moneyAmountTv.text = it.data.balance.toString()
                    viewModel.checkTransactions()
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
        viewModel.transaction.observe(this) { it ->
            when (it) {
                is BaseResult.Loading -> {
                    binding.progressBar.root.visibility = View.VISIBLE
                }
                is BaseResult.Success -> {
                    dataAdapter.setData(it.data)
                    binding.progressBar.root.visibility = View.GONE
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
}