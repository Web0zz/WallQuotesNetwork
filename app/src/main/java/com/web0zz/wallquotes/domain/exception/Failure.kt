package com.web0zz.wallquotes.domain.exception

sealed class Failure {
    // In normal situation this sealed class will contain NetworkError, Api error etc.
    data class UnknownError(val message: String, val exceptionMessage: String?) : Failure()
}
