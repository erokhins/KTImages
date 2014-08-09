package org.hanuna.images.common

/**
 * Created by smevok on 7/25/14.
 */

public trait OneDimension {
    val size: Int
}

public trait TwoDimension {
    val cols: Int // width
    val rows: Int // height
}

public trait Coordinates {
    val col: Int
    val row: Int

    public fun equals(other : Coordinates) : Boolean = col == other.col && row == other.row
}

public class SimpleCoordinates(override val col: Int, override val row: Int) : Coordinates

public fun OneDimension.equalSize(other: OneDimension): Boolean = this.size == other.size
public fun TwoDimension.equalSize(other: TwoDimension): Boolean = this.cols == other.cols && this.rows == other.rows

public fun OneDimension.isEmpty(): Boolean = size == 0
public fun OneDimension.isNotEmpty(): Boolean = size != 0

public fun TwoDimension.isEmpty(): Boolean = cols == 0 || rows == 0
public fun TwoDimension.isNotEmpty(): Boolean = !isEmpty()

public fun coord(col: Int, row: Int): Coordinates = SimpleCoordinates(col, row)
public val FIRST: Coordinates = coord(0, 0)

public val Int.col: Coordinates
    get() = coord(this, 0)
public val Int.row: Coordinates
    get() = coord(0, this)

public fun Coordinates.plus(other: Coordinates): Coordinates = coord(this.col + other.col, this.row + other.row)
public fun Coordinates.minus(other: Coordinates): Coordinates = coord(this.col - other.col, this.row - other.row)

public fun IntRange.plus(i: Int): IntRange = IntRange(start + i, end + i)
public fun Int.plus(range: IntRange): IntRange = range + this