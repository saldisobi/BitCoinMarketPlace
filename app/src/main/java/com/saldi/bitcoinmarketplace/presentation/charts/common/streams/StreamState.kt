package com.saldi.bitcoinmarketplace.presentation.charts.common.streams


/**
 * Created by Sourabh on 10/5/21
 */
sealed class StreamState {
    object Loading : StreamState()
    object Empty : StreamState()
    class Failed(val content: Any) : StreamState()
    class Retrieved(val content: Any) : StreamState()
}
