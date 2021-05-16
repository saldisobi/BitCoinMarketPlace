package com.saldi.bitcoinmarketplace.presentation.features.bitcoin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.saldi.bitcoinmarketplace.R
import com.saldi.bitcoinmarketplace.presentation.charts.MainActivity
import com.saldi.bitcoinmarketplace.presentation.charts.common.constants.PresentationConstants
import com.saldi.bitcoinmarketplace.presentation.charts.common.utils.EspressoIdlingResource
import com.saldi.bitcoinmarketplace.presentation.testutil.DataStatus
import com.saldi.bitcoinmarketplace.presentation.testutil.EspressoUtils
import com.saldi.bitcoinmarketplace.presentation.testutil.TestUtil.dataStatus
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, false, false)
    private var mIdlingResource: IdlingResource? = null

    private val activity by lazy { mActivityTestRule.activity }

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun verifyChartDisplayed() {
        dataStatus = DataStatus.Success
        mActivityTestRule.launchActivity(null)
        onView(withId(R.id.chart)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun verifyMoneyIsSelectedOnLaunch() {
        mActivityTestRule.launchActivity(null)
        onView(withId(R.id.bottomNavigationView)).check(
            matches(
                EspressoUtils.hasCheckedItem(
                    R.id.bottom_navigation_bar_money_icon
                )
            )
        )
    }

    @Test
    fun verifyTradeVolumeIsSelectedOnClick() {
        mActivityTestRule.launchActivity(null)
        clickView(R.id.bottom_navigation_bar_trade_volume_icon)
        onView(withId(R.id.bottomNavigationView)).check(
            matches(
                EspressoUtils.hasCheckedItem(
                    R.id.bottom_navigation_bar_trade_volume_icon
                )
            )
        )
    }


    @Test
    fun verifyCardIsSelectedOnClick() {
        mActivityTestRule.launchActivity(null)
        clickView(R.id.bottom_navigation_bar_transactions_icon)
        onView(withId(R.id.bottomNavigationView)).check(
            matches(
                EspressoUtils.hasCheckedItem(
                    R.id.bottom_navigation_bar_transactions_icon
                )
            )
        )
    }

    @Test
    fun verifyRetryDisplayed() {
        dataStatus = DataStatus.Fail
        // We launch the activity.
        mActivityTestRule.launchActivity(null)

        onView(withId(R.id.retryLayoutInflated)).check(matches(isDisplayed()))

    }


    @Test
    fun verifyFailedTitleDisplayed() {
        dataStatus = DataStatus.Fail
        // We launch the activity.
        mActivityTestRule.launchActivity(null)

        onView(withId(R.id.title)).check(matches(ViewMatchers.withText(MainActivity.SOMETHING_WENT_WRONG)))

    }

    @Test
    fun verifySuccessTitleDisplayed() {
        dataStatus = DataStatus.Success
        // We launch the activity.
        mActivityTestRule.launchActivity(null)

        onView(withId(R.id.title)).check(matches(ViewMatchers.withText(PresentationConstants.MARKET_PRICE)))

    }


    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }

    /**
     * Helper function used to click on a view.
     *
     * @param viewId The id of the view to be clicked.
     */
    private fun clickView(viewId: Int) {
        onView(withId(viewId)).perform(ViewActions.click())
    }

}
