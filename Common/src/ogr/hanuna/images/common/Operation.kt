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