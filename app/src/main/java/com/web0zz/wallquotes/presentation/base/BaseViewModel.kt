package com.web0zz.wallquotes.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    protected var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}