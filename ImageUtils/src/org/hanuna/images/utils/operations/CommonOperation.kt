package org.hanuna.images.utils.operations

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.common.operations.matrixElementOperation
import org.hanuna.images.common.operations.RingOperation

/**
 * Created by smevok on 8/9/14.
 */

fun <T> RingOperation<T>.matrixPlus(a: Matrix<T>, b: Matrix<T>): ImageMatrix<T> = matrixElementOperation(a, b, plus)
fun <T> RingOperation<T>.matrixMinus(a: Matrix<T>, b: Matrix<T>): ImageMatrix<T> = matrixElementOperation(a, b, minus)
fun <T> RingOperation<T>.matrixIntTimes(n: Int, a: Matrix<T>): ImageMatrix<T> = matrixElementOperation(n, a, nTimes)
fun <T> RingOperation<T>.matrixTTimes(t: T, a: Matrix<T>): ImageMatrix<T> = matrixElementOperation(t, a, times)


