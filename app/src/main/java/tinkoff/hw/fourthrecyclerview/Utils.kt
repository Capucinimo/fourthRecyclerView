package tinkoff.hw.fourthrecyclerview


fun randomSizeString(minLength:Int = 1,maxLength:Int = 36, string: String = java.util.UUID.randomUUID().toString()): String {
    var maxLengthRight: Int
    var minLengthRight: Int
    if (minLength > maxLength) {
        minLengthRight = maxLength
        maxLengthRight = minLength
    } else {
        minLengthRight = minLength
        maxLengthRight = maxLength
    }
    maxLengthRight = if (maxLengthRight > string.length) string.length else maxLengthRight
    minLengthRight = if (minLengthRight < 1) 1 else minLengthRight

    return string.substring(0, (minLengthRight..maxLengthRight).random())
}