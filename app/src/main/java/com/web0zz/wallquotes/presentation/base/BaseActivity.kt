package com.web0zz.wallquotes.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(
    private val inflateLayout: (LayoutInflater) -> B
) : AppCompatActivity() {
    protected lateinit var activityDataBinding: B

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