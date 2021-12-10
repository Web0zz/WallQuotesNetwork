package com.web0zz.wallquotes.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseSimpleFragment<B : ViewDataBinding>(
    private val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment() {
    private var _fragmentDataBinding: B? = null
    protected val fragmentDataBinding get() = _fragmentDataBinding!!

    open fun initUi() {}
    open fun initCreate() {}
    open fun initStart() {}
    open fun initResume() {}
    open fun initPause() {}
    open fun initStop() {}

    override fun onStart() {
        super.onStart()
        initStart()
    }

    override fun onResume() {
        super.onResume()
        initResume()
    }

    override fun onPause() {
        super.onPause()
        initPause()
    }

    override fun onStop() {
        super.onStop()
        initStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _fragmentDataBinding = inflateLayout(layoutInflater, container, false)
        return fragmentDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentDataBinding = null
    }
}