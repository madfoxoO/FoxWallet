package com.github.madfoxoo.foxwallet.root.nav

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object NavigationInteractorStateMatchers {

    fun hasSelectedNavigationItem(item: NavigationInteractor.NavigationItem): Matcher<NavigationInteractor.State> {
        return HasSelectedNavigationItem(item)
    }
}

private class HasSelectedNavigationItem(private val expectedValue: NavigationInteractor.NavigationItem) :
    BaseMatcher<NavigationInteractor.State>() {

    override fun describeTo(description: Description) {
        description.appendValue(expectedValue.name)
    }

    override fun describeMismatch(item: Any?, description: Description) {
        description.appendValue("was ")
        if (item is NavigationInteractor.State) {
            description.appendValue(item.selectedNavigationItem)
        } else {
            description.appendValue(item)
        }
    }

    override fun matches(item: Any?): Boolean {
        return item is NavigationInteractor.State && item.selectedNavigationItem == expectedValue
    }
}
