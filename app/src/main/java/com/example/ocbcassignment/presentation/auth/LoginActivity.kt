package com.example.ocbcassignment.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ocbcassignment.R
import com.example.ocbcassignment.base.BaseActivity
import com.example.ocbcassignment.base.BaseResult
import com.example.ocbcassignment.data.model.auth.request.AuthRequest
import com.example.ocbcassignment.databinding.ActivityLoginBinding
import com.example.ocbcassignment.misc.Const
import com.example.ocbcassignment.presentation.dashboard.DashboardActivity
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()

    private lateinit var binding: ActivityLoginBinding

    private lateinit var usernameTextField: TextInputEditText
    private lateinit var passwordTextField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityClass: Class<*>? = try {
            val prefs = getSharedPreferences(Const.PREFERENCE_NAME, MODE_PRIVATE)
            Class.forName(
                prefs.getString(Const.LAST_ACTIVITY, LoginActivity::class.java.name).toString())
        } catch (ex: ClassNotFoundException) {
            LoginActivity::class.java
        }

        if (activityClass != this.javaClass) {
            startActivity(Intent(this, activityClass))
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameTextField = binding.usernameEdt
        passwordTextField = binding.passwordEdt

        initObserver()
        binding.loginBtn.setOnClickListener {
            if (validateForms()) {
                usernameTextField.error = null
                passwordTextField.error = null
                viewModel.doLogin(
                    AuthRequest(
                        username = usernameTextField.text.toString(),
                        password = passwordTextField.text.toString()
                    )
                )
            }
        }

        binding.registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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
        return true
    }

    private fun initObserver() {
        viewModel.login.observe(this) {
            when (it) {
                is BaseResult.Loading -> {
                    binding.progressBar.root.visibility = View.VISIBLE
                }
                is BaseResult.Success -> {
                    viewModel.saveLoginInformation(it.data)
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                    binding.progressBar.root.visibility = View.GONE
                    val intent = Intent(this, DashboardActivity::class.java)
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