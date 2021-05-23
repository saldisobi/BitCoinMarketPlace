package com.saldi.bitcoinmarketplace.presentation.charts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saldi.bitcoinmarketplace.presentation.charts.common.arch.safeCollect
import com.saldi.bitcoinmarketplace.presentation.charts.common.streams.StreamState
import com.saldi.bitcoinmarketplace.presentation.charts.common.utils.wrapEspressoIdlingResource
import com.saldi.bitcoinmarketplace.presentation.charts.entities.BitcoinPresentationViewEntity
import com.saldi.bitcoinmarketplace.presentation.charts.mapper.PresentationMapperImpl
import com.saldi.domain.common.exception.Failure
import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import com.saldi.domain.features.bitcoin.usecases.RetrieveBitcoinMarketPriceUseCase
import com.saldi.domain.features.bitcoin.usecases.RetrieveBitcoinTradeVolumeUseCase
import com.saldi.domain.features.bitcoin.usecases.RetrieveTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Sourabh on 10/5/21
 */
@HiltViewModel
class BitcoinChartViewModel @ExperimentalCoroutinesApi
@Inject constructor(
    private val retrieveUseCase: RetrieveBitcoinMarketPriceUseCase,
    private val retrieveBitcoinTradeVolumeUseCase: RetrieveBitcoinTradeVolumeUseCase,
    private val retrieveTransactionsUseCase: RetrieveTransactionsUseCase,
    private val presentationMapperImpl: PresentationMapperImpl
) : ViewModel() {

    /**
     * [LiveData] that will notify the UI when any change occurs in the data set.
     */
    private val _bitcoinMarketPriceInformationLiveData = MutableLiveData<StreamState>()
    val bitcoinMarketPriceInformationLiveData: LiveData<StreamState>
        get() = _bitcoinMarketPriceInformationLiveData


    /**
     * When this ViewModel is instantiated, we fetch BitcoinMarketPriceInformation.
     */
    init {
        fetchBitcoinInformation(BitcoinMetrics.MarketPrice)
    }

    fun fetchBitcoinInformation(bitcoinMetrics: BitcoinMetrics) {
        val useCase = getUseCase(bitcoinMetrics)
        viewModelScope.launch {
            _bitcoinMarketPriceInformationLiveData.postValue(StreamState.Loading)
            wrapEspressoIdlingResource {
                useCase(Unit).safeCollect {
                    when (it) {
                        is DomainResult.Success -> {
                            handleSuccess(it.data)
                        }
                        is DomainResult.Error -> {
                            handleError(it.failure)
                        }
                        else -> throw IllegalArgumentException("Unknown Stream  State $it}")
                    }
                }
            }
        }
    }

    private fun getUseCase(bitcoinMetrics: BitcoinMetrics) =
        when (bitcoinMetrics) {
            is BitcoinMetrics.TradeVolume -> retrieveTransactionsUseCase
            is BitcoinMetrics.MarketPrice -> retrieveUseCase
            is BitcoinMetrics.Transactions -> retrieveBitcoinTradeVolumeUseCase
        }

    private fun handleError(failure: Failure) {
        when (failure) {
            is Failure.ServerError -> {
                _bitcoinMarketPriceInformationLiveData.postValue(StreamState.Failed(SERVER_ERROR))
            }
            is Failure.DataError -> {
                _bitcoinMarketPriceInformationLiveData.postValue(StreamState.Empty)
            }
            is Failure.NetworkConnection -> {
                _bitcoinMarketPriceInformationLiveData.postValue(StreamState.Failed(NETWORK_ERROR))
            }
            else -> {
                _bitcoinMarketPriceInformationLiveData.postValue(StreamState.Failed(SERVER_ERROR))
            }
        }
    }

    private fun handleSuccess(bitcoinValue: BitcoinValue) {
        if (bitcoinValue.values.isEmpty()) {
            _bitcoinMarketPriceInformationLiveData.postValue(StreamState.Empty)
        } else {
            val bitcoinPresentationViewEntity: BitcoinPresentationViewEntity =
                presentationMapperImpl.invoke(bitcoinValue)

            _bitcoinMarketPriceInformationLiveData.postValue(
                StreamState.Retrieved(
                    bitcoinPresentationViewEntity
                )
            )
        }
    }

    companion object {

        const val NETWORK_ERROR = "Please check your internet connection"

        const val SERVER_ERROR = "Unable to reach our servers, please retry after sometime"

    }
}

