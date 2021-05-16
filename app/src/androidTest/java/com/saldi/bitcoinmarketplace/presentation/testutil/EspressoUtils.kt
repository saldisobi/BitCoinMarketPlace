package com.saldi.bitcoinmarketplace.presentation.testutil

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.hamcrest.Description
import org.hamcrest.Matcher
import java.util.*

/**
 * Custom matchers for Espresso.
 */
object EspressoUtils {
    /**
     * Checks that [BottomNavigationView] contains an item with provided id and that it is
     * checked.
     *
     * @param id of the item to find in the navigation view
     * @return a matcher that returns true if the item was found and checked
     */
    fun hasCheckedItem(id: Int): BoundedMatcher<View?, BottomNavigationView> {
        return object : BoundedMatcher<View?, BottomNavigationView>(
            BottomNavigationView::class.java
        ) {
            var checkedIds: MutableSet<Int> = HashSet()
            var itemFound = false
            var triedMatching = false
            override fun describeTo(description: Description) {
                if (!triedMatching) {
                    description.appendText("BottomNavigationView")
                    return
                }
                description.appendText("BottomNavigationView to have a checked item with id=")
                description.appendValue(id)
                if (itemFound) {
                    description.appendText(", but selection was=")
                    description.appendValue(checkedIds)
                } else {
                    description.appendText(", but it doesn't have an item with such id")
                }
            }

            override fun matchesSafely(navigationView: BottomNavigationView): Boolean {
                triedMatching = true
                val menu = navigationView.menu
                for (i in 0 until menu.size()) {
                    val item = menu.getItem(i)
                    if (item.isChecked) {
                        checkedIds.add(item.itemId)
                    }
                    if (item.itemId == id) {
                        itemFound = true
                    }
                }
                return checkedIds.contains(id)
            }
        }
    }
}