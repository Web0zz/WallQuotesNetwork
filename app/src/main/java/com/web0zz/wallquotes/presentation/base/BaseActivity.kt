package com.web0zz.wallquotes.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding>(
    private val inflateLayout: (LayoutInflater) -> VB,
) : AppCompatActivity() {
    protected lateinit var activityDataBinding: VB

    open fun initTheme() {}
    open fun initUi() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataBinding = inflateLayout(layoutInflater)
        initTheme()
        setContentView(activityDataBinding.root)

        initUi()
    }
}
