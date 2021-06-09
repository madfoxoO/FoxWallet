package io.reactivex.observers

import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThanOrEqualTo

fun <T> TestObserver<T>.assertLastValue(reason: String = "", matcher: Matcher<T>): TestObserver<T> {
    assertThat(
        "stream values count",
        this.valueCount(),
        greaterThanOrEqualTo(1)
    )
    assertThat(reason, this.values.last(), matcher)

    return this
}
