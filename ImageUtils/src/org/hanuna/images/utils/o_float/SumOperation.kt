package org.hanuna.images.utils.o_float

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.utils.operations.IntRingOperation
import org.hanuna.images.utils.operations.matrixPlus
import org.hanuna.images.utils.operations.matrixMinus
import org.hanuna.images.utils.operations.matrixIntTimes
import org.hanuna.images.utils.operations.matrixTTimes
import org.hanuna.images.utils.operations.FloatRingOperation

/**
 * Created by smevok on 8/9/14.
 */

fun Matrix<Float>.plus(other: Matrix<Float>): ImageMatrix<Float> = FloatRingOperation.matrixPlus(this, other)
fun Matrix<Float>.minus(other: Matrix<Float>): ImageMatrix<Float> = FloatRingOperation.matrixMinus(this, other)
fun Int.times(m: Matrix<Float>): ImageMatrix<Float> = FloatRingOperation.matrixIntTimes(this, m)
fun Float.times(m: Matrix<Float>): ImageMatrix<Float> = FloatRingOperation.matrixTTimes(this, m)
