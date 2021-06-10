package com.github.madfoxoo.foxwallet.root

import com.nhaarman.mockitokotlin2.verify
import com.uber.rib.core.InteractorHelper
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RootInteractorTest {

    @Mock lateinit var router: RootRouter
    @Mock lateinit var presenter: RootPresenter

    private lateinit var interactor: RootInteractor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = TestRootInteractor.create(presenter)
    }

    @Test
    fun attachesNavigationRibWhenBecomesActive() {
        InteractorHelper.attach(interactor, presenter, router, null)

        verify(router).attachNavigation()
    }
}
