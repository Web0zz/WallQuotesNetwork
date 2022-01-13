package com.web0zz.wallquotes.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    private val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {
    private var _fragmentBinding: VB? = null
    protected val fragmentBinding get() = _fragmentBinding!!

    protected abstract val mViewModel: VM

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    open fun onCreateInvoke() {}
    open fun onCreateViewInvoke() {}
    open fun onViewCreatedInvoke() {}
    open fun onStartInvoke() {}

    override fun onStart() {
        super.onStart()
        onStartInvoke()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateInvoke()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _fragmentBinding = inflateLayout(layoutInflater, container, false)
        onCreateViewInvoke()
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedInvoke()
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentBinding = null
    }
}
