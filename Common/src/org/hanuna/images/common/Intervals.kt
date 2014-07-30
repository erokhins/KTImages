package org.hanuna.images.common

/**
 * Created by smevok on 7/27/14.
 */

public trait MatrixArea {
    val cornerLT: Coordinates
    val cornerRB: Coordinates
}

public fun Coordinates.rangeTo(other: Coordinates): MatrixArea {
    assert(this.col <= other.col && this.row <= other.row)
    return object : MatrixArea {
        override val cornerLT: Coordinates = this@rangeTo
        override val cornerRB: Coordinates = other
    }
}
