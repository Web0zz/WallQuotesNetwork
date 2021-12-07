package com.web0zz.wallquotes.domain.exception

sealed class Failure {
    data class UnknownError(val message: String, val exceptionMessage: String?) : Failure()
}
