package com.web0zz.wallquotes.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.web0zz.wallquotes.presentation.screen.dialog.ErrorDialog

abstract class BaseFragment<VB : ViewDataBinding>(
    private val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {
    private var _fragmentBinding: VB? = null
    protected val fragmentBinding get() = _fragmentBinding!!

    protected abstract val progressBar: View

    private var errorDialog: ErrorDialog? = null

    protected fun setProgressStatus(isLoading: Boolean) {
        progressBar.isVisible = isLoading
    }

    fun showErrorDialog(title: String, message: String) {
        if (errorDialog == null) {
            errorDialog = ErrorDialog()
        }
        errorDialog?.apply {
            this.title = title
            this.message = message
        }
        errorDialog?.let {
            if (!it.isVisible) {
                it.show(requireActivity().supportFragmentManager, TAG_ERROR_DIALOG)
            }
        }
    }

    open fun onCreateViewInvoke() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _fragmentBinding = inflateLayout(layoutInflater, container, false)
        onCreateViewInvoke()
        return fragmentBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentBinding = null
    }

    companion object {
        private const val TAG_ERROR_DIALOG = "error_dialog"
    }
}
