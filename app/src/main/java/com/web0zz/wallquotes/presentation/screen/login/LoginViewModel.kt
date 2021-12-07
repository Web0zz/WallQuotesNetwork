package com.web0zz.wallquotes.presentation.screen.login

import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class LoginViewModel : BaseViewModel() {

    private var _loginUiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Loading)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun authUser(username: String, password: String) {
        handleLogin(true)
    }

    private fun handleLogin(isAuth: Boolean) {
        _loginUiState.value = LoginUiState.Success(isAuth)
    }

    sealed class LoginUiState {
        object Loading : LoginUiState()
        data class Success(val isAuth: Boolean) : LoginUiState()
        data class Error(val failure: Failure) : LoginUiState()
    }
}