package com.web0zz.wallquotes.presentation.screen.login

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.FragmentLoginBinding
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.base.BaseFragment
import com.web0zz.wallquotes.presentation.screen.home.HomeViewModel
import com.web0zz.wallquotes.presentation.util.FragmentUtil.getFragmentNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val mViewModel: LoginViewModel by viewModels()
    private val navController by lazy {
        getFragmentNavController(R.id.nav_host_fragmentContainerView)
    }

    override fun onCreateInvoke() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.loginUiState.collect { handleViewState(it) }
            }
        }
    }

    private fun handleViewState(viewState: LoginViewModel.LoginUiState) {
        when (viewState) {
            is LoginViewModel.LoginUiState.Loading -> handleLoading()
            is LoginViewModel.LoginUiState.Success -> handleLogin(viewState.isAuth)
            is LoginViewModel.LoginUiState.Error -> handleFailure(viewState.failure)
        }
    }

    private fun loginToHome() {
        fragmentBinding.loginButton.setOnClickListener {
            val username = fragmentBinding.loginUsernameTextInputLayout.editText!!.text.toString()
            val password = fragmentBinding.loginPasswordTextInputLayout.editText!!.text.toString()

            if(username.isNotBlank() && password.isNotBlank()) {
                authUser(username, password)
            }
        }
    }

    private fun authUser(username: String, password: String) {
        mViewModel.authUser(username, password)
    }

    // Handle HomeUiState

    private fun handleLoading() {
        // TODO set loading state
    }

    private fun handleLogin(isAuth: Boolean) {
        if (isAuth) {
            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            navController?.navigate(action)
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.UnknownError -> showFailureText(failure.message, failure.exceptionMessage)
        }
    }

    private fun showFailureText(message: String, exceptionMessage: String?) {
        Log.e("ERROR","Error on Login: $exceptionMessage")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}