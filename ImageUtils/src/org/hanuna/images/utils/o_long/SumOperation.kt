package org.hanuna.images.utils.o_long

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.utils.operations.LongRingOperation
import org.hanuna.images.utils.operations.matrixPlus
import org.hanuna.images.utils.operations.matrixMinus
import org.hanuna.images.utils.operations.matrixIntTimes
import org.hanuna.images.utils.operations.matrixTTimes

/**
 * Created by smevok on 8/9/14.
 */

fun Matrix<Long>.plus(other: Matrix<Long>): ImageMatrix<Long> = LongRingOperation.matrixPlus(this, other)
fun Matrix<Long>.minus(other: Matrix<Long>): ImageMatrix<Long> = LongRingOperation.matrixMinus(this, other)
fun Int.times(m: Matrix<Long>): ImageMatrix<Long> = LongRingOperation.matrixIntTimes(this, m)
fun Long.times(m: Matrix<Long>): ImageMatrix<Long> = LongRingOperation.matrixTTimes(this, m)
