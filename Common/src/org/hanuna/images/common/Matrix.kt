package org.hanuna.images.common

import org.hanuna.images.common.toCorrectImageNumber

/**
 * Created by smevok on 7/26/14.
 */

public trait Vector<out T> : OneDimension {
    fun get(index: Int): T
}

public trait MutableVector<T> : Vector<T> {
    fun set(index: Int, value: T)
}

public trait Matrix<out T> : TwoDimension {
    public fun get(col: Int, row: Int): T
    public fun get(coord: Coordinates): T = get(coord.col, coord.row)
}

public trait MutableMatrix<T> : Matrix<T> {
    public fun set(col: Int, row: Int, value: T)
    public fun set(coord: Coordinates, value: T): Unit = set(coord.col, coord.row, value)
}

public trait ImageVector<out T> : Vector<T> {
    override fun get(index: Int): T = get_correct(index.toCorrectImageNumber(size))

    fun get_correct(index: Int): T
}

public trait ImageMutableVector<T> : MutableVector<T>, ImageVector<T> {
    override fun set(index: Int, value: T) = set_correct(index.toCorrectImageNumber(size), value)

    fun set_correct(index: Int, value: T)
}

public trait ImageMatrix<out T> : Matrix<T> {
    override fun get(col: Int, row: Int): T =
            get_correct(col.toCorrectImageNumber(cols), row.toCorrectImageNumber(rows))

    fun get_correct(col: Int, row: Int): T
}

public trait ImageMutableMatrix<T> : MutableMatrix<T>, ImageMatrix<T> {
    override fun set(col: Int, row: Int, value: T) =
            set_correct(col.toCorrectImageNumber(cols), row.toCorrectImageNumber(rows), value)

    fun set_correct(col: Int, row: Int, value: T)
}

