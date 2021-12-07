package com.web0zz.wallquotes.presentation.screen.home

import androidx.fragment.app.viewModels
import com.web0zz.wallquotes.databinding.FragmentHomeBinding
import com.web0zz.wallquotes.presentation.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val mViewModel: HomeViewModel by viewModels()
}