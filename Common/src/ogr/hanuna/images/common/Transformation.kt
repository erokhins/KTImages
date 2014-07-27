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

public fun <T> Vector<T>.reverse(): ImageVector<T> = object : ImageVector<T> {
    override val size: Int = this@reverse.size
    override fun get_correct(index: Int): T = this@reverse[size - 1 - index]
}

public fun <T> Vector<T>.asMatrixRow(): ImageMatrix<T> = object: ImageMatrix<T> {
    override fun get_correct(col: Int, row: Int): T = this@asMatrixRow[col]
    override val cols: Int = size
    override val rows: Int = 1
}

public fun <T> Vector<T>.asMatrixCol(): ImageMatrix<T> = object: ImageMatrix<T> {
    override fun get_correct(col: Int, row: Int): T = this@asMatrixCol[row]
    override val cols: Int = 1
    override val rows: Int = size
}

public fun <T> Matrix<T>.getRow(row: Int): ImageVector<T> = object : ImageVector<T> {
    override val size: Int = cols
    override fun get_correct(index: Int): T = this@getRow[index, row]
}

public fun <T> Matrix<T>.getCol(col: Int): ImageVector<T> = object : ImageVector<T> {
    override val size: Int = rows
    override fun get_correct(index: Int): T = this@getCol[col, index]
}

public enum class SimpleTransform(val ColRowR: Boolean, val ColR: Boolean, val RowR: Boolean) {
    ID : SimpleTransform(false, false, false)       // :) Hi, Anya!
    ROTATE90 : SimpleTransform(true, true, false)
    ROTATE180 : SimpleTransform(false, true, true)
    ROTATE270 : SimpleTransform(true, false, true)

    SYM_HOR : SimpleTransform(false, false, true)
    SYM_VERT : SimpleTransform(false, true, false)
    SYM_DIAG : SimpleTransform(true, false, false)
    SYM_DIAG2 : SimpleTransform(true, true, true)
}

public fun <T> Matrix<T>.simpleTransform(transform: SimpleTransform): ImageMatrix<T> =
        object : ImageMatrix<T> {
            override val cols: Int = if (transform.ColRowR) this@simpleTransform.rows else this@simpleTransform.cols
            override val rows: Int = if (transform.ColRowR) this@simpleTransform.cols else this@simpleTransform.rows
            override fun get_correct(col: Int, row: Int): T {
                val col2 = if (transform.ColR) cols - 1 - col else col
                val row2 = if (transform.RowR) rows - 1 - row else row

                val row3 = if (transform.ColRowR) col2 else row2
                val col3 = if (transform.ColRowR) row2 else col2
                return this@simpleTransform[col3, row3]
            }
        }