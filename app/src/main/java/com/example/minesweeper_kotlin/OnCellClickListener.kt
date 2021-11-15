package com.example.minesweeper_kotlin

interface OnCellClickListener {
    fun cellClick(cellPos: Int): Int
    fun cellClikLong(cellPos: Int)
}
