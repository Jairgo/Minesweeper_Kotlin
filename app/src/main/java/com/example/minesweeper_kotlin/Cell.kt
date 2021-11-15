package com.example.minesweeper_kotlin

class Cell(var value: Int) {
    val bomba = -1
    val vacio = 0

    var isRevelado = false
    var isFlagged = false

    companion object {
        const val bomba = -1
        const val vacio = 0
    }
}