package com.saldi.bitcoinmarketplace.presentation.charts.common.arch

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.flow.collect

/**
 * Only proceed with the given action if the coroutine has not been cancelled.
 * Necessary because Flow.collect receives items even after coroutine was cancelled
 * https://github.com/Kotlin/kotlinx.coroutines/issues/1265
 * https://stackoverflow.com/questions/59680533/how-to-cancel-unsubscribe-from-coroutines-flow?answertab=votes#tab-top
 */
suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
    collect {
        coroutineContext.ensureActive()
        action(it)
    }
}