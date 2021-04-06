package com.github.madfoxoo.foxwallet.root

import com.nhaarman.mockitokotlin2.verify
import com.uber.rib.core.InteractorHelper
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RootInteractorTest {

    @Mock internal lateinit var router: RootRouter
    @Mock internal lateinit var presenter: RootPresenter

    private lateinit var interactor: RootInteractor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = TestRootInteractor.create(presenter)
    }

    @Test
    fun attachesCurrenciesRibWhenBecomesActive() {
        InteractorHelper.attach(interactor, presenter, router, null)

        verify(router).attachCurrencies()
    }
}
