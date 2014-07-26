package ogr.hanuna.images.common

/**
 * Created by smevok on 7/26/14.
 */

public trait Vector<T> : OneDimension {
    fun get(index: Int): T
}

public trait MutableVector<T> : Vector<T> {
    fun set(index: Int, value: T)
}

public trait Matrix<T> : TwoDimension {
    public fun get(col: Int, row: Int): T
    public fun get(coord: Coordinates): T = get(coord.col, coord.row)
}

public trait MutableMatrix<T> : Matrix<T> {
    public fun set(col: Int, row: Int, value: T)
    public fun set(coord: Coordinates, value: T): Unit = set(coord.col, coord.row, value)
}

