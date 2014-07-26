package ogr.hanuna.images.common

import org.hanuna.math.toCorrectImageNumber

/**
 * Created by smevok on 7/26/14.
 */

public trait ImageVector<T> : Vector<T> {
    override fun get(index: Int): T = get_correct(index.toCorrectImageNumber(size))

    protected fun get_correct(index: Int): T
}

public trait ImageMutableVector<T> : MutableVector<T>, ImageVector<T> {
    override fun set(index: Int, value: T) = set_correct(index.toCorrectImageNumber(size), value)

    protected fun set_correct(index: Int, value: T)
}

public trait ImageMatrix<T> : Matrix<T> {
    override fun get(col: Int, row: Int): T =
            get_correct(col.toCorrectImageNumber(cols), row.toCorrectImageNumber(rows))

    protected fun get_correct(col: Int, row: Int): T
}

public trait ImageMutableMatrix<T> : MutableMatrix<T>, ImageMatrix<T> {
    override fun set(col: Int, row: Int, value: T) =
            set_correct(col.toCorrectImageNumber(cols), row.toCorrectImageNumber(rows), value)

    protected fun set_correct(col: Int, row: Int, value: T)
}

