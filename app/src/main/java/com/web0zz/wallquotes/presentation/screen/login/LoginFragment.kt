package com.web0zz.wallquotes.presentation.screen.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.FragmentLoginBinding
import com.web0zz.wallquotes.presentation.base.BaseFragment
import com.web0zz.wallquotes.presentation.util.FragmentUtil.getFragmentNavController

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val mViewModel: LoginViewModel by viewModels()
    private val navController by lazy {
        getFragmentNavController(R.id.nav_host_fragmentContainerView)
    }

    override fun onCreateViewInvoke() {
        Toast.makeText(context, "This is demo just put any input to fields", Toast.LENGTH_LONG).show()

        loginToHome()
    }

    private fun loginToHome() {
        fragmentBinding.loginButton.setOnClickListener {
            val username = fragmentBinding.loginUsernameTextInputLayout.editText!!.text.toString()
            val password = fragmentBinding.loginPasswordTextInputLayout.editText!!.text.toString()

            if(username.isNotBlank() && password.isNotBlank()) {
                authUser(username, password)
            } else Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
        }
    }

    private fun authUser(username: String, password: String) {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        navController?.navigate(action)
    }
}