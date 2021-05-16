package com.saldi.bitcoinmarketplace.presentation.testutil;

import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.test.espresso.matcher.BoundedMatcher;

import java.util.HashSet;
import java.util.Set;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Custom matchers for Espresso.
 */
public class EspressoUtils {
  /**
   * Checks that {@link BottomNavigationView} contains an item with provided id and that it is
   * checked.
   *
   * @param id of the item to find in the navigation view
   * @return a matcher that returns true if the item was found and checked
   */
  public static Matcher<View> hasCheckedItem(final int id) {
    return new BoundedMatcher<View, BottomNavigationView>(BottomNavigationView.class) {
      Set<Integer> checkedIds = new HashSet<>();
      boolean itemFound = false;
      boolean triedMatching = false;

      @Override public void describeTo(Description description) {
        if (!triedMatching) {
          description.appendText("BottomNavigationView");
          return;
        }

        description.appendText("BottomNavigationView to have a checked item with id=");
        description.appendValue(id);
        if (itemFound) {
          description.appendText(", but selection was=");
          description.appendValue(checkedIds);
        } else {
          description.appendText(", but it doesn't have an item with such id");
        }
      }

      @Override protected boolean matchesSafely(BottomNavigationView navigationView) {
        triedMatching = true;

        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
          final MenuItem item = menu.getItem(i);
          if (item.isChecked()) {
            checkedIds.add(item.getItemId());
          }
          if (item.getItemId() == id) {
            itemFound = true;
          }
        }
        return checkedIds.contains(id);
      }
    };
  }
}