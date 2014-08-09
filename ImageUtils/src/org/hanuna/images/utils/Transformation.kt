package org.hanuna.images.utils

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.common.apply

/**
 * Created by smevok on 8/2/14.
 */

public fun <T : Number> Matrix<T>.asDoubleMatrix(): ImageMatrix<Double> = this.apply{ it.toDouble() }
public fun <T : Number> Matrix<T>.asLongMatrix(): ImageMatrix<Long> = this.apply{ it.toLong() }
public fun <T : Number> Matrix<T>.asIntMatrix(): ImageMatrix<Int> = this.apply{ it.toInt() }
public fun <T : Number> Matrix<T>.asFloatMatrix(): ImageMatrix<Float> = this.apply{ it.toFloat() }
public fun <T : Number> Matrix<T>.asShortMatrix(): ImageMatrix<Short> = this.apply{ it.toShort() }