package org.hanuna.images.utils.o_short

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.utils.operations.ShortRingOperation
import org.hanuna.images.utils.operations.matrixPlus
import org.hanuna.images.utils.operations.matrixMinus
import org.hanuna.images.utils.operations.matrixIntTimes
import org.hanuna.images.utils.operations.matrixTTimes

/**
 * Created by smevok on 8/9/14.
 */

fun Matrix<Short>.plus(other: Matrix<Short>): ImageMatrix<Short> = ShortRingOperation.matrixPlus(this, other)
fun Matrix<Short>.minus(other: Matrix<Short>): ImageMatrix<Short> = ShortRingOperation.matrixMinus(this, other)
fun Int.times(m: Matrix<Short>): ImageMatrix<Short> = ShortRingOperation.matrixIntTimes(this, m)
fun Short.times(m: Matrix<Short>): ImageMatrix<Short> = ShortRingOperation.matrixTTimes(this, m)
