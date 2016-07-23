package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year > other.year -> 1
        year < other.year -> -1
        month > other.month -> 1
        month < other.month -> -1
        dayOfMonth > other.dayOfMonth -> 1
        dayOfMonth < other.dayOfMonth -> -1
        else -> 0
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval) : MyDate = addTimeIntervals(timeInterval, 1)
operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval) : MyDate = addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.times)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    infix operator fun times(numTimes: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, numTimes)
    }
}

data class RepeatedTimeInterval(val timeInterval: TimeInterval, val times: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        private var nextDate = start

        override fun hasNext(): Boolean = !isEmpty() && contains(nextDate)

        override fun next(): MyDate {
            val result = nextDate
            nextDate = result.nextDay()
            return result
        }
    }
}

