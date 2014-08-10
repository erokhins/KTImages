package org.hanuna.images.utils.o_double

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.utils.operations.IntRingOperation
import org.hanuna.images.utils.operations.matrixPlus
import org.hanuna.images.utils.operations.matrixMinus
import org.hanuna.images.utils.operations.matrixIntTimes
import org.hanuna.images.utils.operations.matrixTTimes
import org.hanuna.images.utils.operations.matrixResultant
import org.hanuna.images.utils.operations.DoubleRingOperation

/**
 * Created by smevok on 8/9/14.
 */

public fun Matrix<Double>.plus(other: Matrix<Double>): ImageMatrix<Double> = DoubleRingOperation.matrixPlus(this, other)
public fun Matrix<Double>.minus(other: Matrix<Double>): ImageMatrix<Double> = DoubleRingOperation.matrixMinus(this, other)
public fun Int.times(m: Matrix<Double>): ImageMatrix<Double> = DoubleRingOperation.matrixIntTimes(this, m)
public fun Double.times(m: Matrix<Double>): ImageMatrix<Double> = DoubleRingOperation.matrixTTimes(this, m)
public fun ImageMatrix<Double>.resultant(small: Matrix<Double>): ImageMatrix<Double> = DoubleRingOperation.matrixResultant(this, small)