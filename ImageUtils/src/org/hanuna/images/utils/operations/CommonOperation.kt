package org.hanuna.images.utils.operations

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.common.operations.matrixElementOperation
import org.hanuna.images.common.operations.RingOperation
import org.hanuna.images.common.operations.matrixMultiplyOperation
import org.hanuna.images.common.operations.matrixResultant

/**
 * Created by smevok on 8/9/14.
 */

public fun <T> RingOperation<T>.matrixPlus(a: Matrix<T>, b: Matrix<T>): ImageMatrix<T> = matrixElementOperation(a, b, plus)
public fun <T> RingOperation<T>.matrixMinus(a: Matrix<T>, b: Matrix<T>): ImageMatrix<T> = matrixElementOperation(a, b, minus)
public fun <T> RingOperation<T>.matrixIntTimes(n: Int, a: Matrix<T>): ImageMatrix<T> = matrixElementOperation(n, a, nTimes)
public fun <T> RingOperation<T>.matrixTTimes(t: T, a: Matrix<T>): ImageMatrix<T> = matrixElementOperation(t, a, times)

public fun <T> RingOperation<T>.matrixSimpleTimes(a: Matrix<T>, b: Matrix<T>): ImageMatrix<T> = matrixElementOperation(a, b, times)

public fun <T> RingOperation<T>.matrixTimes(a: Matrix<T>, b: Matrix<T>): ImageMatrix<T> = matrixMultiplyOperation(a, b, times, plus)

public fun <T> RingOperation<T>.matrixResultant(big: ImageMatrix<T>, small: Matrix<T>): ImageMatrix<T> =
        matrixResultant(big, small, times, plus)