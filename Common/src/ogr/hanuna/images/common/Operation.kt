package ogr.hanuna.images.common

/**
 * Created by smevok on 7/26/14.
 */

public fun <A, B, C> matrixElementOperation(m1: Matrix<A>, m2: Matrix<B>, operation: (A, B) -> C): ImageMatrix<C> {
    assert(m1.equalSize(m2), "Size of matrix non equal: (${m1.cols}, ${m1.rows}) <> (${m2.cols}, ${m2.rows})")

    return object : ImageMatrix<C> {
        override val cols: Int = m1.cols
        override val rows: Int = m1.rows
        override fun get_correct(col: Int, row: Int): C = operation(m1[col, row], m2[col, row])
    }
}

public fun <A, B, C> matrixElementOperation(m: Matrix<A>, b: B, operation: (A, B) -> C): ImageMatrix<C> {
    return object : ImageMatrix<C> {
        override val cols: Int = m.cols
        override val rows: Int = m.rows
        override fun get_correct(col: Int, row: Int): C = operation(m[col, row], b)
    }
}

public fun <A, B, C> vectorElementOperation(v1: Vector<A>, v2: Vector<B>, operation: (A, B) -> C): ImageVector<C> {
    assert(v1.equalSize(v2), "Size of vectors non equal: ${v1.size} <> ${v2.size}")

    return object : ImageVector<C> {
        override val size: Int = v1.size

        override fun get_correct(index: Int): C = operation(v1[index], v2[index])
    }
}

public fun <A, B, C> vectorElementOperation(v: Vector<A>, b: B, operation: (A, B) -> C): ImageVector<C> {
    return object : ImageVector<C> {
        override val size: Int = v.size

        override fun get_correct(index: Int): C = operation(v[index], b)
    }
}

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

public fun <A, B, C> vectorMultiplyOperation(v1: Vector<A>, v2: Vector<B>, times: (A, B) -> C, plus: (C, C) -> C): C {
    assert(v1.equalSize(v2), "For this operation size of vectors(v1.szie: ${v1.size}; v2.size: ${v2.size})  must be equal.")
    assert(v1.size != 0, "Empty vectors not supported")
    var c: C = times(v1[0], v2[0])
    v1.forAll {
        if (it != 0) {
            c = plus(c, times(v1[it], v2[it]))
        }
    }
    return c
}

public fun <A, B, C> matrixMultiplyOperation(m1: Matrix<A>, m2: Matrix<B>, times: (A, B) -> C, plus: (C, C) -> C):
        ImageMatrix<C> {
    assert(m1.cols == m2.rows, "For this operation m1.cols(${m1.cols}) must be equal m2.rows(${m2.rows}).")
    assert(m1.cols != 0, "Are you kidding? It is matrix 0xN!")

    return object : ImageMatrix<C> {
        override val cols: Int = m2.cols
        override val rows: Int = m1.rows
        override fun get_correct(col: Int, row: Int): C {
            val colVector = m2.getCol(col)
            val rowVector = m1.getRow(row)
            return vectorMultiplyOperation(rowVector, colVector, times, plus)
        }
    }
}