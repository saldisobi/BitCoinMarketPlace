package com.saldi.domain.common.state

import com.saldi.domain.common.exception.Failure

/**
 * Created by Sourabh on 10/5/21
 *
 * Result class for Success and Error
 */
sealed class DomainResult<out R> {
    data class Success<out T>(val data: T) :
        DomainResult<T>() // Status success and data of the result

    data class Error(val failure: Failure) :
        DomainResult<Nothing>() // Status Error an error message
}

val <T> DomainResult<T>.data: T?
    get() = (this as? DomainResult.Success)?.data