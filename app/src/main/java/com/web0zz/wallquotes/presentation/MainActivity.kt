package com.web0zz.wallquotes.presentation

import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.ActivityMainBinding
import com.web0zz.wallquotes.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    // At launch, the splash screen will be display as a theme then on onCreate default theme will be set
    override fun initTheme() {
        setTheme(R.style.Theme_WallQuotes)
    }
}