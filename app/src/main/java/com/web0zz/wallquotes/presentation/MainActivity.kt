package com.web0zz.wallquotes.presentation

import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.ActivityMainBinding
import com.web0zz.wallquotes.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navController: NavController

    // At launch, the splash screen will be display as a theme then on onCreate default theme will be set
    override fun initTheme() {
        setTheme(R.style.Theme_WallQuotes)
    }

    override fun initUi() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()

        val appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = setOf(R.id.loginFragment, R.id.homeFragment))

        activityDataBinding.mainToolbar.setupWithNavController(navController, appBarConfiguration)
    }
}