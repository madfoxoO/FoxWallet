package com.github.madfoxoo.foxwallet.di

import android.content.Context
import com.github.madfoxoo.foxwallet.dagger.ThreadConfig
import com.github.madfoxoo.foxwallet.root.RootBuilder
import dagger.BindsInstance
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

@dagger.Component(
    modules = [SchedulersModule::class]
)
interface AppComponent : RootBuilder.ParentComponent {

    @dagger.Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}

@dagger.Module
abstract class SchedulersModule {

    @dagger.Module
    companion object {

        @Provides
        @ThreadConfig(ThreadConfig.Type.UI)
        @JvmStatic
        internal fun uiScheduler(): Scheduler {
            return AndroidSchedulers.mainThread()
        }
    }
}
