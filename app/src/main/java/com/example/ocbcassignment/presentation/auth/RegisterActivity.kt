package com.example.ocbcassignment.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ocbcassignment.R
import com.example.ocbcassignment.base.BaseActivity
import com.example.ocbcassignment.base.BaseResult
import com.example.ocbcassignment.data.model.auth.request.AuthRequest
import com.example.ocbcassignment.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {
    private val viewModel: AuthViewModel by viewModel()

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var usernameTextField: TextInputEditText
    private lateinit var passwordTextField: TextInputEditText
    private lateinit var passwordConfirmationTextField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameTextField = binding.usernameEdt
        passwordTextField = binding.passwordEdt
        passwordConfirmationTextField = binding.passwordConfirmEdt

        initObserver()

        binding.registerBtn.setOnClickListener {
            if (validateForms()) {
                usernameTextField.error = null
                passwordTextField.error = null
                passwordConfirmationTextField.error = null
                viewModel.register(
                    AuthRequest(
                        username = usernameTextField.text.toString(),
                        password = passwordTextField.text.toString()
                    )
                )
            }
        }
    }

    private fun validateForms(): Boolean {
        if (usernameTextField.text.isNullOrBlank()) {
            usernameTextField.error = getString(R.string.invalid_username)
            return false
        }
        if (passwordTextField.text.isNullOrBlank()) {
            passwordTextField.error = getString(R.string.invalid_password)
            return false
        }
        if (passwordConfirmationTextField.text.toString() != passwordTextField.text.toString()) {
            passwordConfirmationTextField.error = getString(R.string.invalid_confirmation_password)
            return false
        }
        return true
    }

    private fun initObserver() {
        viewModel.login.observe(this) {
            when (it) {
                is BaseResult.Loading -> {
                    binding.progressBar.root.visibility = View.VISIBLE
                }
                is BaseResult.Success -> {
                    binding.progressBar.root.visibility = View.GONE
                    viewModel.saveLoginInformation(it.data)
                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    Toast.makeText(this, "Please login with registered account", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(intent)
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