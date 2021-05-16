package com.saldi.bitcoinmarketplace.presentation.charts

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.saldi.bitcoinmarketplace.R
import com.saldi.bitcoinmarketplace.databinding.ActivityBitcoinMarketPriceBinding
import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.ui.configureChartEmptyView
import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.ui.findNavigationPositionById
import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.ui.findNavigationText
import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.ui.populateChart
import com.saldi.bitcoinmarketplace.presentation.charts.common.streams.StreamState
import com.saldi.bitcoinmarketplace.presentation.charts.entities.BitcoinPresentationViewEntity
import com.saldi.bitcoinmarketplace.presentation.charts.viewmodel.BitcoinChartViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Sourabh on 15/5/21
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBitcoinMarketPriceBinding

    private val bitcoinChartViewModel: BitcoinChartViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        configureBottomNavigationBar()
        observeViewModelLiveData()
    }

    /**
     * Set up Activity UI
     */
    private fun initView() {
        binding = ActivityBitcoinMarketPriceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.chart.configureChartEmptyView(this@MainActivity)
    }

    /**
     * Function used to set up Bottom Navigation View.
     */
    private fun configureBottomNavigationBar() {
        binding.bottomNavigationView.apply {
            setOnNavigationItemSelectedListener { item ->
                binding.title.text = item.title
                bitcoinChartViewModel.fetchBitcoinInformation(
                    findNavigationPositionById(
                        item.itemId
                    ).tag
                )
                true
            }
        }
    }

    /**
     * Function that will set up the [bitcoinChartViewModel]'s [@LiveData] observation.
     */
    private fun observeViewModelLiveData() {
        bitcoinChartViewModel.bitcoinMarketPriceInformationLiveData.observe(
            this, { handleViewModelLiveDataEvents(it) })
    }

    /**
     * Function that is in charge of handling the events from the ViewModel's Livedata.
     *
     * @param streamState The current state of the stream.
     */
    private fun handleViewModelLiveDataEvents(streamState: StreamState?) {
        when (streamState) {
            is StreamState.Loading -> {
                binding.title.text = LOADING_TEXT
                binding.progressBar.visibility = View.VISIBLE
            }
            is StreamState.Retrieved -> {
                handleSuccess(streamState.content as? BitcoinPresentationViewEntity)
            }
            is StreamState.Failed -> {
                binding.title.text = SOMETHING_WENT_WRONG
                handleFailState()
            }
            is StreamState.Empty -> {
                binding.chart.configureChartEmptyView(this@MainActivity)
                binding.retryLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun handleFailState() {
        if (binding.retryLayout.parent != null) {
            val retryLayoutInflated =
                binding.retryLayout.inflate() as ViewGroup
            retryLayoutInflated.findViewById<View>(R.id.buttonRetry).setOnClickListener {
                bitcoinChartViewModel.fetchBitcoinInformation(
                    findNavigationPositionById(
                        binding.bottomNavigationView.selectedItemId
                    ).tag
                )
            }

        } else {
            binding.retryLayout.visibility = View.VISIBLE
        }
        binding.chart.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    /**
     * Function in charge of painting the screen according to [BitcoinPresentationViewEntity].
     */
    private fun handleSuccess(viewEntity: BitcoinPresentationViewEntity?) {
        binding.title.text = findNavigationText(
            binding.bottomNavigationView.selectedItemId
        )
        binding.progressBar.visibility = View.GONE
        binding.chart.populateChart(this@MainActivity, viewEntity)
    }


    companion object {
        const val CHART_ANIMATION_DURATION_MS = 1500
        const val LOADING_TEXT = "Fetching chart data"
        const val SOMETHING_WENT_WRONG = "Something went wrong"
    }
}