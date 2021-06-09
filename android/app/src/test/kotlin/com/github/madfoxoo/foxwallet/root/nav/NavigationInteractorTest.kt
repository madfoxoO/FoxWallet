package com.github.madfoxoo.foxwallet.root.nav

import com.nhaarman.mockitokotlin2.verify
import com.uber.rib.core.InteractorHelper
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NavigationInteractorTest {

    @Mock lateinit var router: NavigationRouter
    @Mock lateinit var presenter: NavigationPresenter

    private lateinit var interactor: NavigationInteractor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = TestNavigationInteractor.create(presenter)
    }

    @Test
    fun attachesHomeRibWhenBecomesActive() {
         InteractorHelper.attach(interactor, presenter, router, null)

        verify(router).attachHome()
    }
}