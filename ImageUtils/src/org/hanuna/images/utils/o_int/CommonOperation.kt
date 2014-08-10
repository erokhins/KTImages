package org.hanuna.images.utils.o_int

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.utils.operations.IntRingOperation
import org.hanuna.images.utils.operations.matrixPlus
import org.hanuna.images.utils.operations.matrixMinus
import org.hanuna.images.utils.operations.matrixIntTimes
import org.hanuna.images.utils.operations.matrixTTimes
import org.hanuna.images.utils.operations.matrixResultant

/**
 * Created by smevok on 8/9/14.
 */

public fun Matrix<Int>.plus(other: Matrix<Int>): ImageMatrix<Int> = IntRingOperation.matrixPlus(this, other)
public fun Matrix<Int>.minus(other: Matrix<Int>): ImageMatrix<Int> = IntRingOperation.matrixMinus(this, other)
public fun Int.times(m: Matrix<Int>): ImageMatrix<Int> = IntRingOperation.matrixIntTimes(this, m)
public fun ImageMatrix<Int>.resultant(small: Matrix<Int>): ImageMatrix<Int> = IntRingOperation.matrixResultant(this, small)
