package org.hanuna.images.utils

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.common.map

/**
 * Created by smevok on 8/2/14.
 */

public fun <T : Number> Matrix<T>.asDoubleMatrix(): ImageMatrix<Double> = this.map { it.toDouble() }
public fun <T : Number> Matrix<T>.asLongMatrix(): ImageMatrix<Long> = this.map { it.toLong() }
public fun <T : Number> Matrix<T>.asIntMatrix(): ImageMatrix<Int> = this.map { it.toInt() }
public fun <T : Number> Matrix<T>.asFloatMatrix(): ImageMatrix<Float> = this.map { it.toFloat() }
public fun <T : Number> Matrix<T>.asShortMatrix(): ImageMatrix<Short> = this.map { it.toShort() }