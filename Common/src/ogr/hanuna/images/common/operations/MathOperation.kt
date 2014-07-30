package ogr.hanuna.images.common.operations

/**
 * Created by smevok on 7/30/14.
 */

public trait PlusOperation<T> {
    public val plus: T.(T) -> T
    public val nTimes: Int.(T) -> T
}

public trait MinusOperation<T> {
    public val minus: T.(T) -> T
}

public trait TimesOperation<A, B, C> {
    public val times: A.(B) -> C
}

public trait SimpleTimesOperation<T> : TimesOperation<T, T, T>

public trait SumOperation<T> : PlusOperation<T>, MinusOperation<T>

public trait RingOperation<T> : SumOperation<T>, SimpleTimesOperation<T>

