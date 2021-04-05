package com.github.madfoxoo.foxwallet.dagger

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ThreadConfig(val value: Type) {

    enum class Type {
        UI,
        IO,
        COMPUTATION,
        SINGLE,
        TRAMPOLINE,
        NEW_THREAD;
    }
}
