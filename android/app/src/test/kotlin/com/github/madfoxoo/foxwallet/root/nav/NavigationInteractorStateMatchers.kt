package com.github.madfoxoo.foxwallet.root.nav

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object NavigationInteractorStateMatchers {

    fun hasSelectedNavigationItem(item: NavigationInteractor.NavigationItem): Matcher<NavigationInteractor.State> {
        return HasSelectedNavigationItem(item)
    }
}

private abstract class NavigationInteractorStateBaseMatcher : BaseMatcher<NavigationInteractor.State>() {

    final override fun describeMismatch(item: Any?, description: Description) {
        description.appendText("was ")
        if (item is NavigationInteractor.State) {
            describeMismatch(item, description)
        } else {
            description.appendValue(item)
        }
    }

    abstract fun describeMismatch(item: NavigationInteractor.State, description: Description)
}

private class HasSelectedNavigationItem(private val expectedValue: NavigationInteractor.NavigationItem) :
    NavigationInteractorStateBaseMatcher() {

    override fun describeTo(description: Description) {
        description.appendValue(expectedValue.name)
    }

    override fun describeMismatch(item: NavigationInteractor.State, description: Description) {
        description.appendValue(item.selectedNavigationItem.name)
    }

    override fun matches(item: Any?): Boolean {
        return item is NavigationInteractor.State && item.selectedNavigationItem == expectedValue
    }
}
