package com.github.madfoxoo.foxwallet.e2e

import android.Manifest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.github.madfoxoo.foxwallet.RootActivity
import com.github.madfoxoo.foxwallet.currencies.list.R
import com.github.madfoxoo.foxwallet.screens.CurrenciesScreen
import com.kaspersky.kaspresso.device.Device
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrenciesEndToEndTest : TestCase() {

    @get:Rule
    val runtimePermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun foo() {
        device
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RootActivity::class.java)

    @Test
    fun addsNewCurrencyToTheList() {
        run {
            step("Open currencies screen") {
                CurrenciesScreen {
                    root { isVisible() }
                    list {
                        firstChild<CurrenciesScreen.NoCurrenciesItem> {
                            title {
                                isVisible()
                                hasText(R.string.feature_currencies__no_currencies_title)
                            }
                            description {
                                isVisible()
                                hasText(R.string.feature_currencies__no_currencies_description)
                            }
                            add {
                                isVisible()
                                hasText(R.string.feature_currencies__action_add)
                            }
                        }
                    }
                }
            }

            step("Open create currency screen") {
                CurrenciesScreen {
                    list {
                        firstChild<CurrenciesScreen.NoCurrenciesItem> {
                            add { click() }
                        }
                    }
                }
            }
        }
    }
}
