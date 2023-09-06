/**
 * Copyright (C) 2019 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vilinesoft.domain.model

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Failure] or [Success].
 * FP Convention dictates that [Failure] is used for "failure"
 * and [Success] is used for "success".
 *
 * @see Failure
 * @see Success
 */
sealed class Either<out F, out S> {
    data class Failure<out F>(val f: F) : Either<F, Nothing>()
    data class Success<out S>(val s: S) : Either<Nothing, S>()

    val isRight get() = this is Success<S>
    val isLeft get() = this is Failure<F>

    fun either(fnL: ((F) -> Unit)? = null, fnR: (S) -> Unit): Any? = when (this) {
        is Failure -> fnL?.invoke(f)
        is Success -> fnR(s)
    }

    fun <F> failure(a: F) = Failure(a)
    fun <S> success(b: S) = Success(b)

}