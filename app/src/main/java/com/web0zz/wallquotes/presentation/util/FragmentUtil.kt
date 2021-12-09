package com.web0zz.wallquotes.presentation.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

object FragmentUtil {
    fun Fragment.getFragmentNavController(@IdRes id: Int) = activity?.let {
        return@let Navigation.findNavController(it, id)
    }
}