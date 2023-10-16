package me.yihtseu.jishi.utils.time

fun startHour(start: Int): Int {
    return when(start) {
        1 -> 8
        2 -> 8
        3 -> 9
        4 -> 10
        5 -> 11
        6 -> 13
        7 -> 14
        8 -> 15
        9 -> 16
        10 -> 17
        11 -> 19
        12 -> 19
        13 -> 20
        else -> 0
    }
}

fun startMin(start: Int): Int {
    return when(start) {
        1 -> 0
        2 -> 50
        3 -> 55
        4 -> 45
        5 -> 35
        6 -> 30
        7 -> 20
        8 -> 25
        9 -> 15
        10 -> 5
        11 -> 0
        12 -> 50
        13 -> 40
        else -> 0
    }
}

fun endHour(end: Int): Int {
    return when(end) {
        1 -> 8
        2 -> 9
        3 -> 10
        4 -> 11
        5 -> 12
        6 -> 14
        7 -> 15
        8 -> 16
        9 -> 17
        10 -> 17
        11 -> 19
        12 -> 20
        13 -> 21
        else -> 0
    }
}

fun endMin(end: Int): Int {
    return when(end) {
        1 -> 45
        2 -> 35
        3 -> 40
        4 -> 30
        5 -> 20
        6 -> 15
        7 -> 5
        8 -> 10
        9 -> 0
        10 -> 50
        11 -> 45
        12 -> 35
        13 -> 25
        else -> 0
    }
}