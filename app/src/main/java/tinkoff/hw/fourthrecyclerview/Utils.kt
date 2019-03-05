package tinkoff.hw.fourthrecyclerview

import kotlin.random.Random

/**
 * генерация случайного непрозрачного цвета
 */
fun randomColor(): Int {
    return Random.nextInt().or(0xff000000.toInt())
}