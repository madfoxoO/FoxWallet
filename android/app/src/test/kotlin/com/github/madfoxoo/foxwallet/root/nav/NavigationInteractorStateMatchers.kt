package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.R
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object NavigationInteractorStateMatchers {

    fun hasSelectedNavigationItemId(id: Int): Matcher<NavigationInteractor.State> {
        return HasSelectedNavigationItemId(id)
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

private class HasSelectedNavigationItemId(private val expectedValue: Int) : NavigationInteractorStateBaseMatcher() {

    override fun describeTo(description: Description) {
        description.appendValue(findNameById(expectedValue))
    }

    override fun describeMismatch(item: NavigationInteractor.State, description: Description) {
        description.appendValue(findNameById(item.selectedNavigationItemId))
    }

    private fun findNameById(id: Int): String {
        return when (id) {
            R.id.action_home -> "action_home"
            R.id.action_payments -> "action_payments"
            R.id.action_statistics -> "action_statistics"
            R.id.action_menu -> "action_menu"
            else -> "unknown"
        }
    }

    override fun matches(item: Any?): Boolean {
        return item is NavigationInteractor.State && item.selectedNavigationItemId == expectedValue
    }
}
