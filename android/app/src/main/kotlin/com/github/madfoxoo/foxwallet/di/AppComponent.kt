package com.github.madfoxoo.foxwallet.di

import android.content.Context
import com.github.madfoxoo.foxwallet.root.RootBuilder
import dagger.BindsInstance

@dagger.Component
interface AppComponent : RootBuilder.ParentComponent {

    @dagger.Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
