package com.web0zz.wallquotes.presentation.screen.login

import androidx.fragment.app.viewModels
import com.web0zz.wallquotes.databinding.FragmentLoginBinding
import com.web0zz.wallquotes.presentation.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val mViewModel: LoginViewModel by viewModels()
}