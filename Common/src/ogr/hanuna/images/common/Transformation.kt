package ogr.hanuna.images.common

/**
 * Created by smevok on 7/26/14.
 */

public fun <T> Vector<T>.toI(): ImageVector<T> {
    if (this is ImageVector)
        return this
    return object : ImageVector<T> {
        override val size: Int = this@toI.size
        override fun get_correct(index: Int): T = this@toI[index]
    }
}

public fun <T> MutableVector<T>.toI(): ImageMutableVector<T> {
    if (this is ImageMutableVector)
        return this
    return object : ImageMutableVector<T> {
        override val size: Int = this@toI.size
        override fun get_correct(index: Int): T = this@toI[index]
        override fun set_correct(index: Int, value: T) {
            this@toI[index] = value
        }
    }
}

public fun <T> Matrix<T>.toI(): ImageMatrix<T> {
    if (this is ImageMatrix)
        return this
    return object : ImageMatrix<T> {
        override val cols: Int = this@toI.cols
        override val rows: Int = this@toI.rows
        override fun get_correct(col: Int, row: Int): T = this@toI[cols, rows]
    }
}

public fun <T> MutableMatrix<T>.toI(): ImageMutableMatrix<T> {
    if (this is ImageMutableMatrix)
        return this
    return object : ImageMutableMatrix<T> {
        override val cols: Int = this@toI.cols
        override val rows: Int = this@toI.rows
        override fun get_correct(col: Int, row: Int): T = this@toI[cols, rows]
        override fun set_correct(col: Int, row: Int, value: T) {
            this@toI[col, row] = value
        }
    }
}