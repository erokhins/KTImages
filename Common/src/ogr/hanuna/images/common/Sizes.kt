package ogr.hanuna.images.common

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

public fun coord(col: Int, row: Int): Coordinates = SimpleCoordinates(col, row)