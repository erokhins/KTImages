package org.hanuna.images.common

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

public fun <T> Vector<T>.reverse(): ImageVector<T> = asImageVector { this@reverse[size - 1 - it] }

public fun <T> Vector<T>.asMatrixRow(): ImageMatrix<T> = dimension(size, 1).asImageMatrix { this@asMatrixRow[col] }

public fun <T> Vector<T>.asMatrixCol(): ImageMatrix<T> = dimension(1, size).asImageMatrix { this@asMatrixCol[row] }

public fun <T> Matrix<T>.getRow(row: Int): ImageVector<T> = dimension(cols).asImageVector { this@getRow[it, row] }

public fun <T> Matrix<T>.getCol(col: Int): ImageVector<T> = dimension(rows).asImageVector { this@getCol[col, it] }

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

public fun <A, B> Vector<A>.map(f: (A) -> B): ImageVector<B> = asImageVector { f(this@map[it]) }

public fun <A, B> Matrix<A>.map(f: (A) -> B): ImageMatrix<B> = asImageMatrix {  f(this@map[col, row]) }

public fun <T> Vector<T>.writeTo(other: MutableVector<T>) {
    forAll {
        other[it] = this[it]
    }
}

public fun <T> Matrix<T>.writeTo(other: MutableMatrix<T>) {
    forAll {
        other[it] = this[it]
    }
}

fun <T> ImageMatrix<T>.getArea(area: MatrixArea): ImageMatrix<T> = object : ImageMatrix<T> {
    override val cols: Int = area.cornerRB.col - area.cornerLT.col  + 1
    override val rows: Int = area.cornerRB.row - area.cornerLT.row  + 1

    private val col_shift = area.cornerLT.col
    private val row_shift = area.cornerLT.row
    override fun get_correct(col: Int, row: Int): T = this@getArea[col + col_shift, row + row_shift]
}

fun <T> ImageVector<T>.getSubVector(range: IntRange): ImageVector<T> = object : ImageVector<T> {
    override val size: Int = range.end - range.start + 1

    private val shift = range.start
    override fun get_correct(index: Int): T = this@getSubVector[index + shift]
}