package com.web0zz.wallquotes.presentation.screen.login

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.FragmentLoginBinding
import com.web0zz.wallquotes.presentation.MainActivity
import com.web0zz.wallquotes.presentation.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val navController by lazy {
        activity?.let {
            Navigation.findNavController(it, R.id.nav_host_fragmentContainerView)
        }
    }

    /*
    *   Usage of Preferences in here may be wrong usage for MVVM
    *   Implemented just for demo it will be delete later // TODO
    */
    private val loginPreferences by lazy {
        context?.getSharedPreferences(
            "LOGIN",
            Context.MODE_PRIVATE
        )
    }

    override val progressBar: View by lazy {
        (requireActivity() as MainActivity).progressBar
    }

    override fun onCreateViewInvoke() {
        loginToHome()
    }

    private fun loginToHome() {
        val lastLoginState =
            loginPreferences!!.getBoolean(getString(R.string.login_state_key), false)

        if (lastLoginState) {
            authUser()
        } else {
            Toast.makeText(context, "This is demo just put any input to fields", Toast.LENGTH_LONG)
                .show()
        }

        fragmentBinding.loginButton.setOnClickListener {
            val username = fragmentBinding.loginUsernameTextInputLayout.editText!!.text.toString()
            val password = fragmentBinding.loginPasswordTextInputLayout.editText!!.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                with(loginPreferences!!.edit()) {
                    putBoolean(getString(R.string.login_state_key), true)
                    apply()
                }
                authUser()
            } else Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
        }
    }

    private fun authUser() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        navController?.navigate(action)
    }
}
