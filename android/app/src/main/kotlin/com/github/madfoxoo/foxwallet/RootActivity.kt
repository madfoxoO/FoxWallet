package com.github.madfoxoo.foxwallet

import android.view.ViewGroup
import com.github.madfoxoo.foxwallet.di.ComponentHolder
import com.github.madfoxoo.foxwallet.root.RootBuilder
import com.github.madfoxoo.foxwallet.root.RootRouter
import com.uber.rib.core.RibActivity

class RootActivity : RibActivity() {

    override fun createRouter(parentViewGroup: ViewGroup): RootRouter {
        return (applicationContext as ComponentHolder)
            .appComponent
            .let { RootBuilder(it) }
            .build(parentViewGroup)
    }
}
